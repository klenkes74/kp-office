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
import de.kaiserpfalzEdv.commons.correlation.ResponseCorrelation;
import de.kaiserpfalzEdv.commons.test.CommonTestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.UUID;

import static org.testng.Assert.assertEquals;

/**
 * @author klenkes
 * @since 2014Q
 */
@Test
public class ResponseCorrelationTest extends CommonTestBase {
    private static final Logger LOG = LoggerFactory.getLogger(ResponseCorrelationTest.class);

    private static final UUID CORRELATION_ID = UUID.randomUUID();
    private static final UUID REQUEST_ID = UUID.randomUUID();
    private static final UUID RESPONSE_ID = UUID.randomUUID();
    private static final long SEQUENCE_NO = 1L;
    private static final boolean HAS_NEXT = true;
    private static final boolean IS_IN_SEQUENCE = false || HAS_NEXT;


    private ResponseCorrelation service;


    public ResponseCorrelationTest() {
        super(ResponseCorrelation.class, LOG);
    }


    public void checkBasicRequestCorrelation() {
        logMethod("basic-response", "Checking a basic response correlation.");

        assertEquals(service.getId(), CORRELATION_ID, "Wrong correlation id");
        assertEquals(service.getResponseId(), RESPONSE_ID, "Wrong response id");
        assertEquals(service.getInResponseTo(), REQUEST_ID, "Wrong request id");
        assertEquals(service.getSequence(), SEQUENCE_NO, "Wrong sequence number");
        assertEquals(service.hasMultipleResponses(), HAS_NEXT, "Response should be a single response");
        assertEquals(service.hasNextResponse(), IS_IN_SEQUENCE, "Response should be the last response");
    }


    @BeforeMethod
    protected void generateService() {
        LOG.debug("Building service with correlationId={}, requestId={}, repsonseId={} and sequenceNo={}",
                CORRELATION_ID, REQUEST_ID, RESPONSE_ID, SEQUENCE_NO);

        CorrelationBuilder<ResponseCorrelation> builder = new CorrelationBuilder<ResponseCorrelation>()
                .withCorrelationId(CORRELATION_ID)
                .withRequestId(REQUEST_ID)
                .withResponseId(RESPONSE_ID)
                .withSequence(SEQUENCE_NO);

        if (HAS_NEXT) {
            builder.hasMultipleResponses();
            builder.hasNextResponse();
        }

        if (IS_IN_SEQUENCE)
            builder.hasMultipleResponses();

        service = builder.build();
    }
}
