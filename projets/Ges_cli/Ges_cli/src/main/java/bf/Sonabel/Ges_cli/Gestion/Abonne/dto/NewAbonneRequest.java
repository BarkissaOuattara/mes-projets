package bf.Sonabel.Ges_cli.Gestion.Abonne.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class NewAbonneRequest {

    @NotBlank
    private String nom;

    @NotBlank
    private String prenom;

    private String numPol;

    @NotNull
    private Integer exploitationCode;


    private Date dateNaissance;

    private LocalDate dateCreation;

    @Email
    private String email;

    private String genre;

    private String numReccm;

    private String numIfu;

    private Date dateCNIB;

    private String numCNIB;

    private String postbp;


    private String raisonSocial;


    private String telSer;

    private String telWhatsapp;

    private String ville;
}
