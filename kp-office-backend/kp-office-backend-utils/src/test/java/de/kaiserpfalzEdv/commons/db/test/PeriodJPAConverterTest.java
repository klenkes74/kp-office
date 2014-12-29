package de.kaiserpfalzEdv.commons.db.test;

import de.kaiserpfalzEdv.commons.db.PeriodJPAConverter;
import de.kaiserpfalzEdv.commons.test.CommonTestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Period;

import static org.testng.Assert.assertEquals;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @version 0.1.0
 * @since 0.1.0
 */
@Test
public class PeriodJPAConverterTest extends CommonTestBase {
    private static final Logger LOG = LoggerFactory.getLogger(PeriodJPAConverterTest.class);


    private PeriodJPAConverter service;


    public PeriodJPAConverterTest() {
        super(PeriodJPAConverterTest.class, LOG);
    }

    public void checkPeriod() {
        logMethod("period-single", "Check converter with local ZonedDateTime ...");


        Period orig = Period.of(1, 1, 0);

        String converted = service.convertToDatabaseColumn(orig);
        LOG.debug("Period converted: {} => {}", orig, converted);

        Period result = service.convertToEntityAttribute(converted);
        LOG.debug("Period reconverted: {} => {}", converted, result);

        assertEquals(result, orig);
    }


    @BeforeMethod
    protected void setUpService() {
        service = new PeriodJPAConverter();
    }
}
