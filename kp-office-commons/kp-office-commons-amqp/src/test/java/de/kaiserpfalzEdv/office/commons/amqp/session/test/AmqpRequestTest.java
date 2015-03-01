/*
 * Copyright 2015 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzEdv.office.commons.amqp.session.test;

import de.kaiserpfalzEdv.commons.test.CommonTestBase;
import de.kaiserpfalzEdv.office.commons.amqp.session.AmqpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 01.03.15 15:11
 */
@Test
public class AmqpRequestTest extends CommonTestBase {
    private static final Logger LOG = LoggerFactory.getLogger(AmqpRequestTest.class);


    private AmqpRequest service = new AmqpRequest();


    public AmqpRequestTest() {
        super(AmqpRequestTest.class, LOG);
    }


    public void storeData() {
        logMethod("store", "Stores a string into the request ...");

        service.startRequest(null);
        service.set("key", "data");
        String result = (String) service.get("key");

        assertEquals(result, "data");
    }


    public void endRequest() {
        logMethod("end-request", "Checks the removing of all data when a request is ended ...");

        service.startRequest(null);
        service.set("key", "data");

        service.endRequest();

        String result = (String) service.get("key");

        assertNull(result, "There should be no data left!");
    }
}
