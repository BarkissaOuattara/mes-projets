package bf.Sonabel.Ges_cli.Gestion.Abonne;

import bf.Sonabel.Ges_cli.Gestion.Abonne.dto.NewAbonneRequest;
import bf.Sonabel.Ges_cli.Gestion.Exception.ItemNotFoundException;
import bf.Sonabel.Ges_cli.Gestion.Exploitation.Exploitation;
import bf.Sonabel.Ges_cli.Gestion.Exploitation.ExploitationService;
import bf.Sonabel.Ges_cli.Gestion.Statistique.StatistiqueUpdateEvent;
import bf.Sonabel.Ges_cli.Gestion.TypeClient.TypeClientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbonneServiceImpl implements AbonneService {

    private final AbonneRepository abonneRepository;
    private final ExploitationService exploitationService;
    @Autowired
    private ApplicationEventPublisher publisher;

    public AbonneServiceImpl(
            AbonneRepository abonneRepository,
            ExploitationService exploitationService,
            TypeClientService typeClientService
    ) {
        this.abonneRepository = abonneRepository;
        this.exploitationService = exploitationService;
    }

    @Override
    public List<Abonne> getAll() {
        return abonneRepository.findAll();
    }

    @Override
    public Abonne createAbonne(NewAbonneRequest request) {

        Exploitation exploitation = exploitationService.getByCode(request.getExploitationCode());

        Abonne abonne = Abonne.builder()
                .numPol(abonneRepository.findAll().size() + "")
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .dateNaissance(request.getDateNaissance())
                .email(request.getEmail())
                .genre(request.getGenre())
                .dateCNIB(request.getDateCNIB())
                .numReccm(request.getNumReccm())
                .numIfu(request.getNumIfu())
                .numCNIB(request.getNumCNIB())
                .postbp(request.getPostbp())
                .raisonSocial(request.getRaisonSocial())
                .telSer(request.getTelSer())
                .telWhatsapp(request.getTelWhatsapp())
                .ville(request.getVille())
                .exploitation(exploitation)
                .dateCreation(request.getDateCreation())
                .build();
        Abonne saved = abonneRepository.save(abonne);
        publisher.publishEvent(new StatistiqueUpdateEvent(saved));
        return saved;

        
    }

    @Override
public void updateAbonne(NewAbonneRequest abonne) {
    Abonne existing = abonneRepository.findByNumPol(abonne.getNumPol())
            .orElseThrow(ItemNotFoundException::new);

    Exploitation exploitation = exploitationService.getByCode(abonne.getExploitationCode());

    Abonne updatedData = Abonne.builder()
            .nom(abonne.getNom())
            .prenom(abonne.getPrenom())
            .dateNaissance(abonne.getDateNaissance())
            .email(abonne.getEmail())
            .genre(abonne.getGenre())
            .numCNIB(abonne.getNumCNIB())
            .dateCNIB(abonne.getDateCNIB())
            .numIfu(abonne.getNumIfu())
            .numReccm(abonne.getNumReccm())
            .postbp(abonne.getPostbp())
            .raisonSocial(abonne.getRaisonSocial())
            .telSer(abonne.getTelSer())
            .telWhatsapp(abonne.getTelWhatsapp())
            .ville(abonne.getVille())
            .exploitation(exploitation)
            .build();

    existing.update(updatedData);
    abonneRepository.save(existing);
}


    @Override
    public void deleteAbonne(String numPol) {
        Abonne abonne = abonneRepository.findByNumPol(numPol)
                .orElseThrow(ItemNotFoundException::new);
        abonneRepository.delete(abonne);
    }

    @Override
    public Abonne findByNumPolice(String numPolice) {
        return abonneRepository.findByNumPol(numPolice)
                .orElseThrow(ItemNotFoundException::new);
    }
}
