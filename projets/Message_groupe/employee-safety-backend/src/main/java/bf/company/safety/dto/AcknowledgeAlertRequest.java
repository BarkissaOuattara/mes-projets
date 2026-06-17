package bf.company.safety.dto;

import jakarta.validation.constraints.NotNull;

public record AcknowledgeAlertRequest(@NotNull Long employeeId) {
}
