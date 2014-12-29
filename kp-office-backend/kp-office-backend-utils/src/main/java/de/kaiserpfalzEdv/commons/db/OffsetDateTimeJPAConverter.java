package de.kaiserpfalzEdv.commons.db;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.OffsetDateTime;

/**
 * Converts a {@link java.time.LocalDate} into a database friendly data field.
 *
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @version 0.1.0
 * @since 0.1.0
 */
@Converter(autoApply = true)
public class OffsetDateTimeJPAConverter implements AttributeConverter<OffsetDateTime, String> {
    @Override
    public String convertToDatabaseColumn(OffsetDateTime attribute) {
        return attribute.toString();
    }

    @Override
    public OffsetDateTime convertToEntityAttribute(String dbData) {
        return OffsetDateTime.parse(dbData);
    }
}
