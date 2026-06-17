package bf.Sonabel.Ges_cli.Gestion.Abonne;

import bf.Sonabel.Ges_cli.Gestion.Exploitation.Exploitation;
import bf.Sonabel.Ges_cli.Gestion.TypeClient.TypeClient;
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
    private Integer numPolice;

    @ManyToOne
    @JoinColumn(name = "exploitation", nullable = false)
    private Exploitation exploitation;

    @ManyToOne
    @JoinColumn(name = "typeclient", nullable = false)
    private TypeClient typeClient;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    public void update(Abonne newData) {
        this.nom = newData.getNom();
        this.prenom = newData.getPrenom();
        this.exploitation = newData.getExploitation();
        this.typeClient = newData.getTypeClient();
    }
}
