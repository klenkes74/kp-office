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
import de.kaiserpfalzEdv.commons.test.CommonTestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.security.Principal;
import java.util.UUID;

import static org.testng.Assert.assertEquals;

/**
 * @author klenkes
 * @since 2014Q
 */
@Test
public class RequestCorrelationTest extends CommonTestBase {
    private static final Logger LOG = LoggerFactory.getLogger(RequestCorrelationTest.class);

    private static final UUID CORRELATION_ID = UUID.randomUUID();
    private static final UUID REQUEST_ID = UUID.randomUUID();
    private static final long SEQUENCE_NO = 1L;

    private static final Principal REQUESTER = new Principal() {
        @Override
        public String getName() {
            return "requesting user";
        }
    };

    private static final Principal SYSTEM = new Principal() {
        @Override
        public String getName() {
            return "requesting system";
        }
    };


    private RequestCorrelation service;


    public RequestCorrelationTest() {
        super(RequestCorrelation.class, LOG);
    }


    public void checkBasicRequestCorrelation() {
        logMethod("basic-request", "Checking a basic request correlation.");

        assertEquals(service.getId(), CORRELATION_ID, "Wrong correlation id");
        assertEquals(service.getRequestId(), REQUEST_ID, "Wrong request id");
        assertEquals(service.getSequence(), SEQUENCE_NO, "Wrong sequence number");
        assertEquals(service.getRequester(), REQUESTER, "Wrong requesting user");
        assertEquals(service.getSystem(), SYSTEM, "Wrong requesting system");
    }


    @BeforeMethod
    protected void generateService() {
        LOG.trace("Building service with correlationId={}, requestId={} and sequenceNo={}", CORRELATION_ID, REQUEST_ID, SEQUENCE_NO);

        service = new CorrelationBuilder<RequestCorrelation>()
                .withCorrelationId(CORRELATION_ID)
                .withRequestId(REQUEST_ID)
                .withSequence(SEQUENCE_NO)
                .withRequester(REQUESTER)
                .withSystem(SYSTEM)
                .build();
    }
}
