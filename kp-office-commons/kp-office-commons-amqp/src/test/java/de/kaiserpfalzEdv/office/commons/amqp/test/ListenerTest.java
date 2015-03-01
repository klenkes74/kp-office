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

package de.kaiserpfalzEdv.office.commons.amqp.test;

import de.kaiserpfalzEdv.commons.test.SpringTestNGBase;
import de.kaiserpfalzEdv.office.commons.amqp.session.AmqpRequest;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.UUID;

import static org.testng.Assert.assertEquals;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 01.03.15 11:39
 */
@Test
@ContextConfiguration("/beans-test.xml")
public class ListenerTest extends SpringTestNGBase {
    private static final Logger LOG = LoggerFactory.getLogger(ListenerTest.class);

    @Value("${amqp.host} ${amqp.port}")
    private String host;

    @Inject
    private AmqpTemplate service;

    @Inject
    private AmqpRequest request;


    public ListenerTest() {
        super(ListenerTest.class, LOG);
    }


    public void testListener() {
        logMethod("listener-loaded", "Checks if the listener is working for host '" + host + "' ...");

        request.setUserId(UUID.randomUUID());
        BinaryMessage message = new BinaryMessage(5, "testnachricht");

        Object result = service.convertSendAndReceive("kpo.core", "core.i18n", message);
        LOG.info("Received: {}", result);

        assertEquals(result, message);
    }

}


class BinaryMessage implements Serializable {
    private static final long serialVersionUID = 673393669598091562L;


    private UUID   id;
    private int    number;
    private String message;


    public BinaryMessage(int number, String message) {
        this.id = UUID.randomUUID();
        this.number = number;
        this.message = message;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        BinaryMessage rhs = (BinaryMessage) obj;
        return new EqualsBuilder()
                .append(this.id, rhs.id)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .toHashCode();
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append(id)
                .append("number", number)
                .append("message", message)
                .toString();
    }
}
