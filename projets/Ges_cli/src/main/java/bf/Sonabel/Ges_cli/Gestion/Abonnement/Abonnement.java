package bf.Sonabel.Ges_cli.Gestion.Abonnement;

import bf.Sonabel.Ges_cli.Gestion.Abonne.Abonne;
import bf.Sonabel.Ges_cli.Gestion.Branchement.Branchement;
import bf.Sonabel.Ges_cli.Gestion.Statistique.StatistiqueEntityListener;
import bf.Sonabel.Ges_cli.Gestion.Tarif.Tarif;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(StatistiqueEntityListener.class)
public class Abonnement {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String code;

    private LocalDate dateDebut;
    private LocalDate dateFin;
    private LocalDate dateResiliation;
    @Enumerated(EnumType.STRING)
    private StatutAbonnement statut;

    private String numPol;
    

    @ManyToOne
    @JoinColumn(name = "code_abonne", referencedColumnName = "code", nullable = false)
    private Abonne abonne;

    @ManyToOne(optional = false)
    @JoinColumn(name = "codeBranch", nullable = false)
    private Branchement branchement;


   @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tarif_code")
    private Tarif tarif;



}
