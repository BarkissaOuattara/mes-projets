package bf.Sonabel.Ges_cli.Gestion.Statistique;

import java.time.YearMonth;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StatistiqueMensuelleRepository extends JpaRepository<StatistiqueMensuelle, Long> {
    Optional<StatistiqueMensuelle> findByMois(YearMonth mois);
}
