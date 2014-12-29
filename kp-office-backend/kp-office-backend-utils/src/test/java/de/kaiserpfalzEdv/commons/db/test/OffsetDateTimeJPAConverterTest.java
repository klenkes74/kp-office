package de.kaiserpfalzEdv.commons.db.test;

import de.kaiserpfalzEdv.commons.db.OffsetDateTimeJPAConverter;
import de.kaiserpfalzEdv.commons.test.CommonTestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.OffsetDateTime;
import java.time.ZoneId;

import static org.testng.Assert.assertEquals;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @version 0.1.0
 * @since 0.1.0
 */
@Test
public class OffsetDateTimeJPAConverterTest extends CommonTestBase {
    private static final Logger LOG = LoggerFactory.getLogger(OffsetDateTimeJPAConverterTest.class);


    private OffsetDateTimeJPAConverter service;


    public OffsetDateTimeJPAConverterTest() {
        super(OffsetDateTimeJPAConverterTest.class, LOG);
    }

    public void checkTime() {
        logMethod("datetime-offset-local", "Check converter with local OffsetTime ...");


        OffsetDateTime orig = OffsetDateTime.now();

        String converted = service.convertToDatabaseColumn(orig);
        LOG.debug("Date/Time converted: {} => {}", orig, converted);

        OffsetDateTime result = service.convertToEntityAttribute(converted);
        LOG.debug("Date/Time reconverted: {} => {}", converted, result);

        assertEquals(orig, result);
    }


    public void checkTimeUsCentral() {
        logMethod("datetime-offset-uscentral", "Check converter with fixed offset to US/Central");

        OffsetDateTime orig = OffsetDateTime.now(ZoneId.of("US/Central"));

        String converted = service.convertToDatabaseColumn(orig);
        LOG.debug("Date/Time converted: {} => {}", orig, converted);

        OffsetDateTime result = service.convertToEntityAttribute(converted);
        LOG.debug("Date/Time reconverted: {} => {}", converted, result);

        assertEquals(orig, result);
    }


    public void checkTimeAfricaNairobi() {
        logMethod("datetime-offset-uscentral", "Check converter with fixed offset to Africa/Nairobi");

        OffsetDateTime orig = OffsetDateTime.now(ZoneId.of("Africa/Nairobi"));

        String converted = service.convertToDatabaseColumn(orig);
        LOG.debug("Date/Time converted: {} => {}", orig, converted);

        OffsetDateTime result = service.convertToEntityAttribute(converted);
        LOG.debug("Date/Time reconverted: {} => {}", converted, result);

        assertEquals(orig, result);
    }


    @BeforeMethod
    protected void setUpService() {
        service = new OffsetDateTimeJPAConverter();
    }
}
