package bf.company.safety.repository;

import bf.company.safety.model.DeviceToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeviceTokenRepository extends JpaRepository<DeviceToken, Long> {
    Optional<DeviceToken> findByToken(String token);

    List<DeviceToken> findByEmployeeIdIn(List<Long> employeeIds);
}
