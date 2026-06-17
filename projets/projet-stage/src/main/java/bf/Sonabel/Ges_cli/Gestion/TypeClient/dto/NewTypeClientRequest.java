package bf.Sonabel.Ges_cli.Gestion.TypeClient.dto;


import jakarta.validation.constraints.NotBlank;

public record NewTypeClientRequest(
    @NotBlank String code,
    @NotBlank String libelle
) {}
