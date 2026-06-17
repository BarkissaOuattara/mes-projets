package bf.Sonabel.Ges_cli.Gestion.Statistique;

import lombok.Data;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Data
public class StatistiqueJour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private Long totalAbonnes;
    private Long totalAbonnements;
    private Long totalResiliations;
}

