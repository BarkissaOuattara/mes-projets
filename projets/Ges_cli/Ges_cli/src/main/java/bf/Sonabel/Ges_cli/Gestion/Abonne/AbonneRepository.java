package bf.Sonabel.Ges_cli.Gestion.Abonne;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AbonneRepository extends JpaRepository<Abonne, Integer> {

    // Recherche des abonnés par code exploitation
    List<Abonne> findByExploitation_Code(Integer code);


    Optional<Abonne> findByNumPol(String numPol);

     Long countByDateCreation(LocalDate date);

    // Nombre d'abonnés créés entre deux dates (pour les stats mensuelles)
    long countByDateCreationBetween(LocalDate debut, LocalDate fin);
}
