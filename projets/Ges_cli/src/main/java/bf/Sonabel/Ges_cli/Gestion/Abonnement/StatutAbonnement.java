package bf.Sonabel.Ges_cli.Gestion.Abonnement;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum StatutAbonnement {
    ACTIF("A"),
    RESILIE("W");

    private final String code;

    StatutAbonnement(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    // Lors de la sérialisation vers le frontend → "A" ou "W"
    @JsonValue
    public String toJson() {
        return this.code;
    }

    // Lors de la désérialisation depuis le frontend → A ou W
    @JsonCreator
    public static StatutAbonnement fromJson(String code) {
        for (StatutAbonnement statut : values()) {
            if (statut.code.equalsIgnoreCase(code)) {
                return statut;
            }
        }
        throw new IllegalArgumentException("Code de statut inconnu : " + code);
    }
}
