package de.kaiserpfalzEdv.commons.db.test;

import de.kaiserpfalzEdv.commons.db.ZonedDateTimeJPAConverter;
import de.kaiserpfalzEdv.commons.test.CommonTestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.testng.Assert.assertEquals;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @version 0.1.0
 * @since 0.1.0
 */
@Test
public class ZonedDatetimeJPAConverterTest extends CommonTestBase {
    private static final Logger LOG = LoggerFactory.getLogger(ZonedDatetimeJPAConverterTest.class);


    private ZonedDateTimeJPAConverter service;


    public ZonedDatetimeJPAConverterTest() {
        super(ZonedDatetimeJPAConverterTest.class, LOG);
    }

    public void checkTime() {
        logMethod("datetime-zoned-local", "Check converter with local ZonedDateTime ...");


        ZonedDateTime orig = ZonedDateTime.now();

        Timestamp converted = service.convertToDatabaseColumn(orig);
        LOG.debug("Date/Time converted: {} => {}", orig, converted);

        ZonedDateTime result = service.convertToEntityAttribute(converted);
        LOG.debug("Date/Time reconverted: {} => {}", converted, result);

        assertEquals(result, orig.withZoneSameInstant(result.getZone()));
    }


    public void checkTimeUsCentral() {
        logMethod("datetime-zoned-uscentral", "Check converter with fixed offset to US/Central");

        ZonedDateTime orig = ZonedDateTime.now(ZoneId.of("US/Central"));

        Timestamp converted = service.convertToDatabaseColumn(orig);
        LOG.debug("Date/Time converted: {} => {}", orig, converted);

        ZonedDateTime result = service.convertToEntityAttribute(converted);
        LOG.debug("Date/Time reconverted: {} => {}", converted, result);

        assertEquals(result, orig.withZoneSameInstant(result.getZone()));
    }


    public void checkTimeAfricaNairobi() {
        logMethod("datetime-zoned-uscentral", "Check converter with fixed offset to Africa/Nairobi");

        ZonedDateTime orig = ZonedDateTime.now(ZoneId.of("Africa/Nairobi"));

        Timestamp converted = service.convertToDatabaseColumn(orig);
        LOG.debug("Date/Time converted: {} => {}", orig, converted);

        ZonedDateTime result = service.convertToEntityAttribute(converted);
        LOG.debug("Date/Time reconverted: {} => {}", converted, result);

        assertEquals(result, orig.withZoneSameInstant(result.getZone()));
    }


    @BeforeMethod
    protected void setUpService() {
        service = new ZonedDateTimeJPAConverter();
    }
}
