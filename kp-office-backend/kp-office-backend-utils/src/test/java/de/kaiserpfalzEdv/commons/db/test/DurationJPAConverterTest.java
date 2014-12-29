package de.kaiserpfalzEdv.commons.db.test;

import de.kaiserpfalzEdv.commons.db.DurationJPAConverter;
import de.kaiserpfalzEdv.commons.test.CommonTestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.time.LocalTime;

import static org.testng.Assert.assertEquals;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @version 0.1.0
 * @since 0.1.0
 */
@Test
public class DurationJPAConverterTest extends CommonTestBase {
    private static final Logger LOG = LoggerFactory.getLogger(DurationJPAConverterTest.class);


    private DurationJPAConverter service;


    public DurationJPAConverterTest() {
        super(DurationJPAConverterTest.class, LOG);
    }

    public void checkDuration() {
        logMethod("duration-single", "Check converter with local ZonedDateTime ...");


        Duration orig = Duration.between(LocalTime.now().minusHours(2), LocalTime.now());

        Long converted = service.convertToDatabaseColumn(orig);
        LOG.debug("Period converted: {} => {}", orig, converted);

        Duration result = service.convertToEntityAttribute(converted);
        LOG.debug("Period reconverted: {} => {}", converted, result);

        assertEquals(result, orig);
    }


    @BeforeMethod
    protected void setUpService() {
        service = new DurationJPAConverter();
    }
}
