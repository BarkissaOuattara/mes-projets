package bf.company.safety.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateIncidentRequest(
        @NotNull Long employeeId,
        @NotBlank String zone,
        @NotBlank String description
) {
}
