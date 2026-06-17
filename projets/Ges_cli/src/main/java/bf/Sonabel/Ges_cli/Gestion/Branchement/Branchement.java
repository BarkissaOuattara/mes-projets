package bf.Sonabel.Ges_cli.Gestion.Branchement;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import bf.Sonabel.Ges_cli.Gestion.Abonnement.Abonnement;
import bf.Sonabel.Ges_cli.Gestion.Exploitation.Exploitation;
import bf.Sonabel.Ges_cli.Gestion.TypeBranchement.TypeBranch;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Branchement {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String code;

    @ManyToOne
    @JoinColumn(name = "CodeExpl", nullable = false)
    private Exploitation exploitation;

    @Column(nullable = false)
    private String section;

    @Column(nullable = false)
    private Integer lot;

    @Column(nullable = false)
    private Integer parcelle;

    @Column(nullable = false)
    private Integer rang;

    @Column(nullable = false)
    private String etat;

    @ManyToOne(optional = false)
    @JoinColumn(name = "type_branchement_code",nullable = false)
    private TypeBranch typeBranch;

    @Column(nullable = false)
    private Integer avoirs;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    private String tel;

    private String rue;

@OneToMany(mappedBy = "branchement", cascade = CascadeType.ALL)
@JsonIgnore
private List<Abonnement> abonnements;



    public void update(Branchement newData) {
        this.section = newData.getSection();
        this.lot = newData.getLot();
        this.parcelle = newData.getParcelle();
        this.rang = newData.getRang();
        this.etat = newData.getEtat();
        this.avoirs = newData.getAvoirs();
        this.nom = newData.getNom();
        this.prenom = newData.getPrenom();
        this.tel = newData.getTel();
        this.rue = newData.getRue();
    }
}
