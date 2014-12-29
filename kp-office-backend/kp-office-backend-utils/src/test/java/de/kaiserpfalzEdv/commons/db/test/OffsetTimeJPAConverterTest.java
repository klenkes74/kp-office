package de.kaiserpfalzEdv.commons.db.test;

import de.kaiserpfalzEdv.commons.db.OffsetTimeJPAConverter;
import de.kaiserpfalzEdv.commons.test.CommonTestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.OffsetTime;
import java.time.ZoneId;

import static org.testng.Assert.assertEquals;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @version 0.1.0
 * @since 0.1.0
 */
@Test
public class OffsetTimeJPAConverterTest extends CommonTestBase {
    private static final Logger LOG = LoggerFactory.getLogger(OffsetTimeJPAConverterTest.class);


    private OffsetTimeJPAConverter service;


    public OffsetTimeJPAConverterTest() {
        super(OffsetTimeJPAConverterTest.class, LOG);
    }

    public void checkTime() {
        logMethod("time-offset-local", "Check converter with local OffsetTime ...");


        OffsetTime orig = OffsetTime.now();

        String converted = service.convertToDatabaseColumn(orig);
        LOG.debug("Time converted: {} => {}", orig, converted);

        OffsetTime result = service.convertToEntityAttribute(converted);
        LOG.debug("Time reconverted: {} => {}", converted, result);

        assertEquals(orig, result);
    }


    public void checkTimeUsCentral() {
        logMethod("time-offset-uscentral", "Check converter with fixed offset to US/Central");

        OffsetTime orig = OffsetTime.now(ZoneId.of("US/Central"));

        String converted = service.convertToDatabaseColumn(orig);
        LOG.debug("Time converted: {} => {}", orig, converted);

        OffsetTime result = service.convertToEntityAttribute(converted);
        LOG.debug("Time reconverted: {} => {}", converted, result);

        assertEquals(orig, result);
    }


    public void checkTimeAfricaNairobi() {
        logMethod("time-offset-uscentral", "Check converter with fixed offset to Africa/Nairobi");

        OffsetTime orig = OffsetTime.now(ZoneId.of("Africa/Nairobi"));

        String converted = service.convertToDatabaseColumn(orig);
        LOG.debug("Time converted: {} => {}", orig, converted);

        OffsetTime result = service.convertToEntityAttribute(converted);
        LOG.debug("Time reconverted: {} => {}", converted, result);

        assertEquals(orig, result);
    }


    @BeforeMethod
    protected void setUpService() {
        service = new OffsetTimeJPAConverter();
    }
}
