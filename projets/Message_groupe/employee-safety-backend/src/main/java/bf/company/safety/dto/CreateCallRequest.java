package bf.company.safety.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateCallRequest(
        @NotNull Long employeeId,
        @NotBlank String phone,
        @NotBlank String zone,
        boolean urgent
) {
}
