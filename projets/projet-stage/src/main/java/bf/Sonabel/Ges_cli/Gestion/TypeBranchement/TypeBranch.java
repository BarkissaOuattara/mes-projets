package bf.Sonabel.Ges_cli.Gestion.TypeBranchement;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import bf.Sonabel.Ges_cli.Gestion.Branchement.Branchement;
import bf.Sonabel.Ges_cli.Gestion.Tarif.Tarif;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class TypeBranch {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String calibrage;

    @Column(nullable = false)
    private String libelle;

    @Column(nullable = false)
    private Integer fraisrcvrt;

    @Column(nullable = false)
    private Integer fraisremise;

    private Integer fraisrepose;

    @Column(nullable = false)
    private Integer fraisetal;

    @Column(nullable = false)
    private Integer forfTypebr;

    @Column(nullable = false)
    private Integer forfMntBran;

    @Column(nullable = true)
    private Integer forfGle;

    @OneToMany(mappedBy = "typeBranch", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Branchement> branchement;

    @OneToMany(mappedBy = "typeBranch", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Tarif> tarif;

}
