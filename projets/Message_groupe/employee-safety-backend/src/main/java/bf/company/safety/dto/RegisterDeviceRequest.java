package bf.company.safety.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterDeviceRequest(
        @NotNull Long employeeId,
        @NotBlank String token,
        @NotBlank String platform
) {
}
