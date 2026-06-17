package bf.Sonabel.Ges_cli.Gestion.Statistique;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.YearMonth;

/**
 * Convertisseur JPA pour mapper un champ YearMonth en base sous forme de String (ex: "2025-06").
 */
@Converter(autoApply = true)
public class YearMonthAttributeConverter implements AttributeConverter<YearMonth, String> {

    /**
     * Convertit un YearMonth en String pour stockage en base de données.
     */
    @Override
    public String convertToDatabaseColumn(YearMonth attribute) {
        return (attribute == null) ? null : attribute.toString(); // Format "yyyy-MM"
    }

    /**
     * Convertit une chaîne stockée en base en YearMonth.
     */
    @Override
    public YearMonth convertToEntityAttribute(String dbData) {
        return (dbData == null || dbData.isEmpty()) ? null : YearMonth.parse(dbData);
    }
}
