package bf.olive.application_test.Classe.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NewClasseRequest(
    @NotBlank
    @NotNull
    String nom

){}
