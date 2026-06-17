package bf.Sonabel.Ges_cli.Gestion.Abonnement;

import bf.Sonabel.Ges_cli.Gestion.Abonnement.dto.ResiliationRequest;
import bf.Sonabel.Ges_cli.Gestion.Branchement.Branchement;
import bf.Sonabel.Ges_cli.Gestion.Exploitation.Exploitation;
import bf.Sonabel.Ges_cli.Gestion.Abonne.Abonne;
import bf.Sonabel.Ges_cli.Gestion.Abonnement.dto.AbonnementDTO;

import java.util.Collection;
import java.util.List;

public interface AbonnementService {
    AbonnementDTO createAbonnement(AbonnementDTO request);
    AbonnementDTO getAbonnementActifPourResiliation(
            Integer codeExpl,
            Integer lot,
            Integer parcelle,
            String section,
            Integer rang,
            String numPolice);
    Collection<Abonnement> getAll();
     Branchement findBranchement(Integer lot, Integer parcelle, Integer rang, String section, Exploitation exploitation);
     Abonne findByNumPol(String numPol);
     AbonnementDTO resilierAbonnement(ResiliationRequest request);
     List<AbonnementDTO> getDerniersAbonnements();
     AbonnementDTO findAbonnementByNumPol(String numPol);
     


}
