package bf.company.safety.dto;

import bf.company.safety.model.AlertLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateAlertRequest(
        @NotBlank String title,
        @NotBlank String message,
        @NotNull AlertLevel level,
        @NotBlank String targetZone,
        @NotBlank String sender
) {
}
