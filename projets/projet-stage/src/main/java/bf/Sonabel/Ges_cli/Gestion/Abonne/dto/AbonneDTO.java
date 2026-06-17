package bf.Sonabel.Ges_cli.Gestion.Abonne.dto;

import bf.Sonabel.Ges_cli.Gestion.Abonne.Abonne;
import bf.Sonabel.Ges_cli.Gestion.Exploitation.dto.ExploitationDTO;
import bf.Sonabel.Ges_cli.Gestion.TypeClient.dto.TypeClientDTO;


@Data
@Builder
public class AbonneDTO{
    Integer numPol;
    String nom;
    String prenom;
    ExploitationDTO exploitation;
    TypeClientDTO typeClient;

    public static AbonneDTO tDto(Abonne abonne) 
    {
        if (abonne == null){
            return null;
        }

        return AbonneDTO.builder()

           .numPol(abonne.getNumPol())
           .nom(abonne.getNom())
           .prenom(abonne.getPrenom())
           .exploitation(abonne.getExploitation())
            .typeClient(abonne.getTypeClient())
            .build();
    
    }
}
