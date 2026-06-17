package bf.Sonabel.Ges_cli.Gestion.Abonnement;

import bf.Sonabel.Ges_cli.Gestion.Abonnement.dto.ResiliationRequest;
import bf.Sonabel.Ges_cli.Gestion.Abonnement.dto.AbonnementDTO;
import bf.Sonabel.Ges_cli.Gestion.Branchement.Branchement;
import bf.Sonabel.Ges_cli.Gestion.Branchement.BranchementRepository;
import bf.Sonabel.Ges_cli.Gestion.Exploitation.Exploitation;
import bf.Sonabel.Ges_cli.Gestion.Exploitation.ExploitationService;
import bf.Sonabel.Ges_cli.Gestion.Statistique.StatistiqueUpdateEvent;
// import bf.Sonabel.Ges_cli.Gestion.Tarif.Tarif;
// import bf.Sonabel.Ges_cli.Gestion.Tarif.TarifRepository;
import bf.Sonabel.Ges_cli.Gestion.Exception.ItemNotFoundException;
import bf.Sonabel.Ges_cli.Gestion.Abonne.Abonne;
import bf.Sonabel.Ges_cli.Gestion.Abonne.AbonneRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AbonnementServiceImpl implements AbonnementService {

    private final AbonnementRepository abonnementRepository;
    private final BranchementRepository branchementRepository;
    private final ExploitationService exploitationService;
    private final AbonneRepository abonneRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Override
public AbonnementDTO createAbonnement(AbonnementDTO request) {
    Branchement branchement = findBranchement(
        request.getBranchement().getLot(),
        request.getBranchement().getParcelle(),
        request.getBranchement().getRang(),
        request.getBranchement().getSection(),
        request.getBranchement().getExploitation()
    );

    // Vérifier que le branchement est ACTIF
    if (!"ACTIF".equalsIgnoreCase(branchement.getEtat())) {
        throw new IllegalStateException("Ce branchement n'est pas à l'état ACTIF !");
    }

    // Vérifier s'il existe déjà un abonnement actif pour ce branchement
    boolean existeActif = abonnementRepository.existsByBranchementAndStatut(branchement, StatutAbonnement.ACTIF);
    if (existeActif) {
        throw new IllegalStateException("Ce branchement possède déjà un abonnement actif !");
    }

    // Vérifier s'il existe déjà un abonnement actif avec ce numéro de police
    boolean policeActif = abonnementRepository.existsByNumPolAndStatut(request.getNumPol(), StatutAbonnement.ACTIF);
    if (policeActif) {
        throw new IllegalStateException("Un abonnement actif existe déjà pour ce numéro de police !");
    }

    // Créer le nouvel abonnement
    request.setDateDebut(LocalDate.now());
    request.setStatut(StatutAbonnement.ACTIF);

    Abonnement abonnement = AbonnementDTO.toEntity(request);
    Abonnement saved = abonnementRepository.save(abonnement);
    publisher.publishEvent(new StatistiqueUpdateEvent(saved));
    return AbonnementDTO.toDTO(saved);
}


    @Override
    public List<AbonnementDTO> getDerniersAbonnements() {
        return abonnementRepository.findTop10ByOrderByDateDebutDesc()
                .stream()
                .map(AbonnementDTO::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AbonnementDTO getAbonnementActifPourResiliation(Integer codeExpl, Integer lot, Integer parcelle,
            String section, Integer rang, String numPolice) {

        Exploitation exploitation = exploitationService.getByCode(codeExpl);
        Branchement branchement = findBranchement(lot, parcelle, rang, section, exploitation);

        Abonnement abonnement = abonnementRepository
    .findByBranchementAndStatutAndDateFinIsNull(branchement, StatutAbonnement.ACTIF)
    .orElseThrow(() -> new IllegalStateException("Aucun abonnement actif valide trouvé pour ce branchement !"));


        abonneRepository.findByNumPol(numPolice)
                .orElseThrow(() -> new ItemNotFoundException("Abonné introuvable."));

        return AbonnementDTO.toDTO(abonnement);
    }

    @Override
    public AbonnementDTO findAbonnementByNumPol(String numPol) {
        return abonnementRepository.findByNumPolAndStatut(numPol, StatutAbonnement.ACTIF)
                .map(AbonnementDTO::toDTO)
                .orElseThrow(() -> new ItemNotFoundException(
                        "Abonnement actif non trouvé pour le numéro de police " + numPol));
    }

    @Override
    public AbonnementDTO resilierAbonnement(ResiliationRequest request) {
        if (request == null || request.getCode() == null || request.getCode().isEmpty()) {
            throw new IllegalArgumentException("Le code de l'abonnement est requis.");
        }

        Abonnement abonnement = abonnementRepository.findById(request.getCode())
                .orElseThrow(
                        () -> new ItemNotFoundException("Aucun abonnement trouvé avec le code : " + request.getCode()));

        if (abonnement.getStatut() != StatutAbonnement.ACTIF) {
            throw new IllegalStateException("Seuls les abonnements actifs peuvent être résiliés.");
        }

        abonnement.setStatut(StatutAbonnement.RESILIE);
        abonnement.setDateResiliation(LocalDate.now());

        Abonnement saved = abonnementRepository.save(abonnement);
        publisher.publishEvent(new StatistiqueUpdateEvent(saved));
        return AbonnementDTO.toDTO(saved);
    }

    @Override
    public Collection<Abonnement> getAll() {
        return abonnementRepository.findAll();
    }

    public Branchement findBranchement(Integer lot, Integer parcelle, Integer rang, String section,
            Exploitation exploitation) {
        return branchementRepository.findByLotAndParcelleAndRangAndSectionAndExploitation(
                lot, parcelle, rang, section, exploitation).stream().findFirst()
                .orElseThrow(() -> new ItemNotFoundException("Branchement introuvable !"));
    }

    public Abonne findByNumPol(String numPol) {
        return abonneRepository.findByNumPol(numPol)
                .orElseThrow(() -> new ItemNotFoundException("Abonné introuvable"));
    }
}
