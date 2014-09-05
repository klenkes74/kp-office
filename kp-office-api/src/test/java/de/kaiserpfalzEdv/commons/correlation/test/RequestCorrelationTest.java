/*
 * Copyright (c) 2014 Kaiserpfalz EDV-Service, Roland T. Lichti
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.kaiserpfalzEdv.commons.correlation.test;

import de.kaiserpfalzEdv.commons.correlation.CorrelationBuilder;
import de.kaiserpfalzEdv.commons.correlation.RequestCorrelation;
import de.kaiserpfalzEdv.commons.security.ActingSystem;
import de.kaiserpfalzEdv.commons.test.CommonTestBase;
import de.kaiserpfalzEdv.office.security.OfficeSubjectDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.UUID;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

/**
 * @author klenkes
 * @since 2014Q
 */
@Test
public class RequestCorrelationTest extends CommonTestBase {
    private static final Logger LOG = LoggerFactory.getLogger(RequestCorrelationTest.class);

    private static final UUID SESSION_ID = UUID.randomUUID();
    private static final UUID REQUEST_ID = UUID.randomUUID();
    private static final long SEQUENCE_NO = 1L;

    private static final OfficeSubjectDTO REQUESTER = new OfficeSubjectDTO(null, null, null);
    private static final ActingSystem SYSTEM = new OfficeSubjectDTO(null, null, null);


    private RequestCorrelation service;


    public RequestCorrelationTest() {
        super(RequestCorrelation.class, LOG);
    }


    public void checkData() {
        logMethod("data-check", "Checking full data set ...");

        assertEquals(service.getId(), REQUEST_ID, "Wrong request id");
        assertEquals(service.getSequence(), SEQUENCE_NO, "Wrong sequence number");
        assertEquals(service.getRequester(), REQUESTER, "Wrong requesting user");
        assertEquals(service.getSystem(), SYSTEM, "Wrong requesting system");
    }


    public void checkCorrelationType() {
        logMethod("correlation-type", "Checking correlation type ...");

        assertTrue(service.isRequest(), "Wrong correlation type. This should be a request.");
        assertFalse(service.isResponse(), "Wrong correlation type. This should be no response.");
    }


    public void createRequestWithSession() {
        logMethod("request-w/-session", "Checking a basic request correlation with session.");

        assertEquals(service.getSessionId(), SESSION_ID, "Wrong session id");
    }

    public void createRequestWithoutSession() {
        logMethod("request-w/o-session", "Checking a basic request correlation without session.");
        service = new CorrelationBuilder<RequestCorrelation>()
                .withRequestId(REQUEST_ID)
                .build();

        assertNull(service.getSessionId());
    }


    @BeforeMethod
    protected void generateService() {
        LOG.trace("Building service with correlationId={}, requestId={} and sequenceNo={}", SESSION_ID, REQUEST_ID, SEQUENCE_NO);

        service = new CorrelationBuilder<RequestCorrelation>()
                .withSessionId(SESSION_ID)
                .withRequestId(REQUEST_ID)
                .withSequence(SEQUENCE_NO)
                .withRequester(REQUESTER)
                .withSystem(SYSTEM)
                .build();
    }
}
