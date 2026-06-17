package bf.Sonabel.Ges_cli.Gestion.Abonne;

import bf.Sonabel.Ges_cli.Gestion.Abonne.dto.NewAbonneRequest;
import bf.Sonabel.Ges_cli.Gestion.Exception.ItemNotFoundException;
import bf.Sonabel.Ges_cli.Gestion.Exploitation.Exploitation;
import bf.Sonabel.Ges_cli.Gestion.Exploitation.ExploitationService;
import bf.Sonabel.Ges_cli.Gestion.TypeClient.TypeClient;
import bf.Sonabel.Ges_cli.Gestion.TypeClient.TypeClientService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbonneServiceImpl implements AbonneService {

    private final AbonneRepository abonneRepository;
    private final ExploitationService exploitationService;
    private final TypeClientService typeClientService;

    public AbonneServiceImpl(
            AbonneRepository abonneRepository,
            ExploitationService exploitationService,
            TypeClientService typeClientService
    ) {
        this.abonneRepository = abonneRepository;
        this.exploitationService = exploitationService;
        this.typeClientService = typeClientService;
    }

    @Override
    public List<Abonne> getAll() {
        return abonneRepository.findAll();
    }

    @Override
    public Abonne createAbonne(NewAbonneRequest request) {

        // Récupération de l'exploitation et du typeClient via leurs codes respectifs.
        Exploitation exploitation = exploitationService.getByCode(request.getExploitationCode());
        TypeClient typeClient = typeClientService.getByCode(request.getTypeClientCode());

        // Création de l'abonné sans inclure numPolice, il sera auto-généré par la base de données.
        Abonne abonne = Abonne.builder()
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .exploitation(exploitation)
                .typeClient(typeClient)
                .build();
        return abonneRepository.save(abonne);
    }

    @Override
    public void updateAbonne(Abonne abonne) {
        Abonne existing = abonneRepository.findById(abonne.getNumPolice())
                .orElseThrow(ItemNotFoundException::new);
        existing.update(abonne);
        abonneRepository.save(existing);
    }

    @Override
    public void deleteAbonne(Integer numPolice) {
        Abonne abonne = abonneRepository.findByNumPolice(numPolice)
                .orElseThrow(ItemNotFoundException::new);
        abonneRepository.delete(abonne);
    }

    @Override
    public Abonne findByNumPolice(Integer numPolice) {
        return abonneRepository.findByNumPolice(numPolice)
                .orElseThrow(ItemNotFoundException::new);
    }
}
