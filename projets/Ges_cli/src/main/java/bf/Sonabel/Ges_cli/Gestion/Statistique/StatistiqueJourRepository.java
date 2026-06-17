package bf.Sonabel.Ges_cli.Gestion.Statistique;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StatistiqueJourRepository extends JpaRepository<StatistiqueJour, Long> {
    Optional<StatistiqueJour> findByDate(LocalDate aujourdHui);
    List<StatistiqueJour> findAllByDateBetween(LocalDate start, LocalDate aujourdHui);
}


