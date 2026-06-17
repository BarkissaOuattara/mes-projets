package bf.Sonabel.Ges_cli.Gestion.Statistique;

import java.time.YearMonth;

import jakarta.persistence.*;

@Entity
@Table(name = "statistique_mensuelle")
public class StatistiqueMensuelle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mois", nullable = false)
    @Convert(converter = YearMonthAttributeConverter.class)
    private YearMonth mois;

    @Column(name = "total_abonnes")
    private Long totalAbonnes;

    @Column(name = "total_abonnements")
    private Long totalAbonnements;

    @Column(name = "total_resiliations")
    private Long totalResiliations;

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public YearMonth getMois() {
        return mois;
    }

    public void setMois(YearMonth mois) {
        this.mois = mois;
    }

    public Long getTotalAbonnes() {
        return totalAbonnes;
    }

    public void setTotalAbonnes(Long totalAbonnes) {
        this.totalAbonnes = totalAbonnes;
    }

    public Long getTotalAbonnements() {
        return totalAbonnements;
    }

    public void setTotalAbonnements(Long totalAbonnements) {
        this.totalAbonnements = totalAbonnements;
    }

    public Long getTotalResiliations() {
        return totalResiliations;
    }

    public void setTotalResiliations(Long totalResiliations) {
        this.totalResiliations = totalResiliations;
    }
}
