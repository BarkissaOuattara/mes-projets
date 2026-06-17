package bf.company.safety.repository;

import bf.company.safety.model.CallRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CallRequestRepository extends JpaRepository<CallRequest, Long> {
    List<CallRequest> findAllByOrderByCreatedAtDesc();
}
