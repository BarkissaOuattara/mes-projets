package bf.Sonabel.Ges_cli.Gestion.Abonne.dto;

import bf.Sonabel.Ges_cli.Gestion.Abonne.Abonne;
import bf.Sonabel.Ges_cli.Gestion.Exploitation.Exploitation;

import java.time.LocalDate;
import java.util.Date;

public record AbonneDTO(
    Integer code,
    String numPol,
    String nom,
    String prenom,
    Date dateNaissance,
    String email,
    String genre,
    Date dateCNIB,
    String numReccm,
    String numIfu,
    String numCNIB,
    String postbp,
    String raisonSocial,
    String telSer,
    String telWhatsapp,
    String ville,
    Exploitation exploitation,
    LocalDate dateCreation
) {
    public AbonneDTO(Abonne abonne) {
        this(
            abonne.getCode(),
            abonne.getNumPol(),
            abonne.getNom(),
            abonne.getPrenom(),
            abonne.getDateNaissance(),
            abonne.getEmail(),
            abonne.getGenre(),
            abonne.getDateCNIB(),
            abonne.getNumReccm(),
            abonne.getNumIfu(),
            abonne.getNumCNIB(),
            abonne.getPostbp(),
            abonne.getRaisonSocial(),
            abonne.getTelSer(),
            abonne.getTelWhatsapp(),
            abonne.getVille(),
            abonne.getExploitation(), 
            abonne.getDateCreation()

        );
    }
}
