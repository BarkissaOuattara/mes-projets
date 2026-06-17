package bf.Sonabel.Ges_cli.Gestion.Abonne;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import bf.Sonabel.Ges_cli.Gestion.Abonnement.Abonnement;
import bf.Sonabel.Ges_cli.Gestion.Exploitation.Exploitation;
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
@Table
public class Abonne {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer code;

    private String numPol;

    @ManyToOne
    @JoinColumn(name = "exploitation", nullable = false)
    private Exploitation exploitation;

    
    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @CreationTimestamp
    @Column(name = "date_creation", updatable = false)
    private LocalDate dateCreation;

    private Date dateNaissance;

    private String email;
    private String genre;
    private String numReccm;
    private String numIfu;

    private String numCNIB;
    private Date dateCNIB;
    private String postbp;
    
    private String raisonSocial;
    private String telSer;
    private String telWhatsapp;
    private String ville;

    @OneToMany(mappedBy = "abonne")
    @JsonIgnore
    private List<Abonnement> abonnements;

    public void update(Abonne newData) {
    this.nom = newData.getNom();
    this.prenom = newData.getPrenom();
    this.dateNaissance = newData.getDateNaissance();
    this.email = newData.getEmail();
    this.genre = newData.getGenre();
    this.numCNIB = newData.getNumCNIB();
    this.dateCNIB = newData.getDateCNIB();
    this.numIfu = newData.getNumIfu();
    this.numReccm = newData.getNumReccm();
    this.postbp = newData.getPostbp();
    this.raisonSocial = newData.getRaisonSocial();
    this.telSer = newData.getTelSer();
    this.telWhatsapp = newData.getTelWhatsapp();
    this.ville = newData.getVille();
    this.exploitation = newData.getExploitation();
}

}
