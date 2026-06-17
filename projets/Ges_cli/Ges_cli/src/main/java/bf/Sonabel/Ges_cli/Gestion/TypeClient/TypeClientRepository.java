package bf.Sonabel.Ges_cli.Gestion.TypeClient;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeClientRepository extends JpaRepository<TypeClient, String> {
        Optional<TypeClient> findByLibelleIgnoreCase(String libelle);

}
