package bf.olive.application_test.etudiant;

import com.fasterxml.jackson.annotation.JsonIgnore;

import bf.olive.application_test.Classe.Classe;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Etudiant {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, unique = true)
    private String matricule;

    private String nom;

    private String prenom;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "classe_id", nullable = false)
    private Classe classe;

    public void update(Etudiant etudiant){
        this.matricule = etudiant.getMatricule();
        this.nom = etudiant.getNom();
        this.prenom = etudiant.getPrenom();
        
        
    }
}
