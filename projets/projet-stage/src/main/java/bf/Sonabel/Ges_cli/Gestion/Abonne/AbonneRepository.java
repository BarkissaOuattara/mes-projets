package bf.Sonabel.Ges_cli.Gestion.Abonne;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AbonneRepository extends JpaRepository<Abonne, Integer> {

    // Recherche des abonnés par code exploitation
    List<Abonne> findByExploitation_Code(Integer code);

    // Recherche des abonnés par code type client
    List<Abonne> findByTypeClient_Code(String codeTypeClient);

    Optional<Abonne> findByNumPolice(Integer numPolice);
}
