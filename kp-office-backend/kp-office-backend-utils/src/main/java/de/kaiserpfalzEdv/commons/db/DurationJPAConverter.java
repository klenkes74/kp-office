package de.kaiserpfalzEdv.commons.db;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.Duration;

/**
 * Converts a {@link java.time.LocalDate} into a database friendly data field.
 *
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @version 0.1.0
 * @since 0.1.0
 */
@Converter(autoApply = true)
public class DurationJPAConverter implements AttributeConverter<Duration, Long> {
    @Override
    public Long convertToDatabaseColumn(Duration attribute) {
        return attribute.toMillis();
    }

    @Override
    public Duration convertToEntityAttribute(Long dbData) {
        return Duration.ofMillis(dbData);
    }
}
