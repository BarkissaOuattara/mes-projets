package bf.Sonabel.Ges_cli.Gestion.Tarif;

import bf.Sonabel.Ges_cli.Gestion.Exception.ItemNotFoundException;
import bf.Sonabel.Ges_cli.Gestion.Exception.TarifAlreadyExisteException;
import bf.Sonabel.Ges_cli.Gestion.Tarif.dto.NewTarifRequest;
import bf.Sonabel.Ges_cli.Gestion.TypeBranchement.TypeBranch;
import bf.Sonabel.Ges_cli.Gestion.TypeBranchement.TypeBranchService;
import bf.Sonabel.Ges_cli.Gestion.TypeClient.TypeClient;
import bf.Sonabel.Ges_cli.Gestion.TypeClient.TypeClientService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TarifServiceImpl implements TarifService {

    private final TarifRepository tarifRepository;
    private final TypeClientService typeClientService;
    private final TypeBranchService typeBranchService;

    public TarifServiceImpl(
        TarifRepository tarifRepository,
        TypeClientService typeClientService,
        TypeBranchService typeBranchService
    ) {
        this.tarifRepository = tarifRepository;
        this.typeClientService = typeClientService;
        this.typeBranchService = typeBranchService;
    }

    @Override
    public List<Tarif> getAll() {
        return tarifRepository.findAll();
    }

    @Override
    public Tarif createTarif(NewTarifRequest request) {

        // Récupération des entités liées
        TypeBranch typeBranch = typeBranchService.getById(request.getTypeBranchCode());
        TypeClient typeClient = typeClientService.getByCode(request.getTypeClientCode());

        // Vérification d'unicité
        boolean tarifExists = !tarifRepository.findByTypeBranchAndTypeClient(typeBranch, typeClient).isEmpty();
        if (tarifExists) {
            throw new TarifAlreadyExisteException("Un tarif avec ce type de branchement et ce type de client existe déjà.");
        }

        // Création de l'entité Tarif
        Tarif newTarif = Tarif.builder()
                .typeBranch(typeBranch)
                .typeClient(typeClient)
                .libelle(request.getLibelle())
                .reglDisjonct(request.getReglDisjonct())
                .puissance(request.getPuissance())
                .tarifHp(request.getTarifHp())
                .tarifHpt(request.getTarifHpt())
                .tarifHc(request.getTarifHc())
                .loccpt(request.getLoccpt())
                .locposte(request.getLocposte())
                .loctransf(request.getLoctransf())
                .mntAvCons(request.getMntAvCons())
                .fraisPol(request.getFraisPol())
                .fraisTimb(request.getFraisTimb())
                .mntPrimFix(request.getMntPrimFix())
                .mntRedev(request.getMntRedev())
                .build();

        return tarifRepository.save(newTarif);
    }

    @Override
    public void updateTarif(Tarif tarif) {
        Tarif existingTarif = tarifRepository.findById(tarif.getCode())
                .orElseThrow(() -> new ItemNotFoundException("Tarif avec le code " + tarif.getCode() + " introuvable"));
        existingTarif.update(tarif);
        tarifRepository.save(existingTarif);
    }

    @Override
    public void deleteTarif(String code) {
        Tarif tarif = tarifRepository.findById(code)
                .orElseThrow(() -> new ItemNotFoundException("Tarif avec le code " + code + " introuvable"));
        tarifRepository.delete(tarif);
    }

    @Override
    public Tarif findById(String code) {
        return tarifRepository.findById(code)
                .orElseThrow(() -> new ItemNotFoundException("Tarif avec le code " + code + " introuvable"));
    }

    @Override
    public boolean existsByCode(String code) {
        return tarifRepository.existsById(code);
    }

    public Tarif findByLibelle(String libelle) {
    return tarifRepository.findByLibelleIgnoreCase(libelle)
            .orElseThrow(() -> new RuntimeException("Tarif non trouvé"));
}


}
