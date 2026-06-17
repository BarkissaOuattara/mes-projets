package bf.company.safety.repository;

import bf.company.safety.model.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertRepository extends JpaRepository<Alert, Long> {
    List<Alert> findByTargetZoneIgnoreCaseOrTargetZoneIgnoreCaseOrderByCreatedAtDesc(String zone, String allZone);

    List<Alert> findAllByOrderByCreatedAtDesc();
}
