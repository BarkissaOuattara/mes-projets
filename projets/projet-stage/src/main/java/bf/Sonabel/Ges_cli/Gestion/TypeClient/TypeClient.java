package bf.Sonabel.Ges_cli.Gestion.TypeClient;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import bf.Sonabel.Ges_cli.Gestion.Abonne.Abonne;
import bf.Sonabel.Ges_cli.Gestion.Tarif.Tarif;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class TypeClient {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String code;

    @Column(nullable = false)
    private String libelle;

    @OneToMany(mappedBy = "typeClient", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Tarif> tarif;


    @OneToMany(mappedBy = "typeClient", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Abonne> abonnes;

}
