package bf.Sonabel.Ges_cli.Gestion.Abonnement.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

import bf.Sonabel.Ges_cli.Gestion.Abonne.Abonne;
import bf.Sonabel.Ges_cli.Gestion.Abonnement.Abonnement;
import bf.Sonabel.Ges_cli.Gestion.Abonnement.StatutAbonnement;
import bf.Sonabel.Ges_cli.Gestion.Branchement.Branchement;
import bf.Sonabel.Ges_cli.Gestion.Tarif.Tarif;

@Data
@Builder
public class AbonnementDTO {

    private String code;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private LocalDate dateResiliation;

    private StatutAbonnement statut;
    private String numPol;
    private Abonne abonne;

    // Branchement
    private Branchement branchement;

    private Tarif tarif;

    public static AbonnementDTO toDTO(Abonnement abonnement) {
        if (abonnement == null) {
            return null;
        }

        Tarif tarif = abonnement.getTarif();
        if (tarif == null) {
        }

        return AbonnementDTO.builder()
                .code(abonnement.getCode())
                .dateDebut(abonnement.getDateDebut())
                .dateFin(abonnement.getDateFin())
                .dateResiliation(abonnement.getDateResiliation())
                .statut(abonnement.getStatut())
                .numPol(abonnement.getNumPol())
                .abonne(abonnement.getAbonne())
                .branchement(abonnement.getBranchement())
                .tarif(tarif)
                .build();
    }

    public static Abonnement toEntity(AbonnementDTO abonnementDTO) {
        if (abonnementDTO == null) {
            return null;
        }

        Abonnement abonnement = new Abonnement();
        abonnement.setCode(abonnementDTO.getCode());
        abonnement.setDateDebut(abonnementDTO.getDateDebut());
        abonnement.setDateFin(abonnementDTO.getDateFin());
        abonnement.setDateResiliation(abonnementDTO.getDateResiliation());
        abonnement.setStatut(abonnementDTO.getStatut());
        abonnement.setNumPol(abonnementDTO.getNumPol());
        abonnement.setAbonne(abonnementDTO.getAbonne());
        abonnement.setBranchement(abonnementDTO.getBranchement());
        abonnement.setTarif(abonnementDTO.getTarif());
        

        return abonnement;
    }
}
