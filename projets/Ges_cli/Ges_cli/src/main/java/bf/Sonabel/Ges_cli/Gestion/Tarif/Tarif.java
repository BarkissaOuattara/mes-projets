package bf.Sonabel.Ges_cli.Gestion.Tarif;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import bf.Sonabel.Ges_cli.Gestion.Abonnement.Abonnement;
import bf.Sonabel.Ges_cli.Gestion.TypeBranchement.TypeBranch;
import bf.Sonabel.Ges_cli.Gestion.TypeClient.TypeClient;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Tarif {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String code;

    @ManyToOne(optional = false)
    @JoinColumn(name = "type_branchement_code",nullable = false)
    @JsonIgnore
    private TypeBranch typeBranch;

    @ManyToOne(optional = false)
    @JoinColumn(name = "type_client_code", nullable = false)
    private TypeClient typeClient;


   @OneToMany(mappedBy = "tarif")
   @JsonIgnore
    private List<Abonnement> abonnements;



    @Column(nullable = false)
    private String libelle;

    @Column(nullable = false)
    private Integer reglDisjonct;

    @Column(nullable = false)
    private Integer puissance;

    @Column(nullable = false)
    private Integer tarifHp;

    @Column(nullable = false)
    private Integer tarifHpt;

    @Column(nullable = false)
    private Integer tarifHc;

    @Column(nullable = false)
    private Integer loccpt;

    @Column(nullable = false)
    private Integer locposte;

    @Column(nullable = false)
    private Integer loctransf;

    @Column(nullable = false)
    private Integer mntAvCons;

    @Column(nullable = false)
    private Integer fraisPol;

    @Column(nullable = false)
    private Integer fraisTimb;

    @Column(nullable = false)
    private Integer mntPrimFix;

    @Column(nullable = false)
    private Integer mntRedev;

    public void update(Tarif newData) {
        this.libelle = newData.getLibelle();
        this.reglDisjonct = newData.getReglDisjonct();
        this.puissance = newData.getPuissance();
        this.tarifHp = newData.getTarifHp();
        this.tarifHpt = newData.getTarifHpt();
        this.tarifHc = newData.getTarifHc();
        this.loccpt = newData.getLoccpt();
        this.locposte = newData.getLocposte();
        this.loctransf = newData.getLoctransf();
        this.mntAvCons = newData.getMntAvCons();
        this.fraisPol = newData.getFraisPol();
        this.fraisTimb = newData.getFraisTimb();
        this.mntPrimFix = newData.getMntPrimFix();
        this.mntRedev = newData.getMntRedev();
    }
}
