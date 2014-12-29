package de.kaiserpfalzEdv.commons.db;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.OffsetTime;

/**
 * Converts a {@link java.time.LocalDate} into a database friendly data field.
 *
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @version 0.1.0
 * @since 0.1.0
 */
@Converter(autoApply = true)
public class OffsetTimeJPAConverter implements AttributeConverter<OffsetTime, String> {
    public String convertToDatabaseColumn(OffsetTime attribute) {
        return attribute.toString();
    }

    @Override
    public OffsetTime convertToEntityAttribute(String dbData) {
        return OffsetTime.parse(dbData);
    }
}
