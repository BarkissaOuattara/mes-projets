package bf.Sonabel.Ges_cli.Gestion.TypeClient.dto;


import bf.Sonabel.Ges_cli.Gestion.TypeClient.TypeClient;

public record TypeClientDTO(
    String code,
    String libelle
) {
    public TypeClientDTO(TypeClient typeClient) {
        this(
            typeClient.getCode(),
            typeClient.getLibelle()
        );
    }
}
