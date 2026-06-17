package bf.company.safety.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateEmployeeRequest(
        @NotBlank String fullName,
        @NotBlank String phone,
        @NotBlank String zone
) {
}
