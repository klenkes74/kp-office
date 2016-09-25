/*
 * Copyright 2016 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzedv.office.tenant;

import java.util.Enumeration;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-25
 */
@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "connectionFactoryLookup", propertyValue = "jms/tenantCF"),
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/tenant"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class TenantMDB implements MessageListener {
    private static final Logger LOG = LoggerFactory.getLogger(TenantMDB.class);

    @Inject
    @JMSConnectionFactory("jms/tenantCF")
    private JMSContext context;

    @Override
    public void onMessage(Message message) {
        try {
            MDC.put("action-id", message.getStringProperty("action-id"));
            MDC.put("message-id", message.getJMSMessageID());
            MDC.put("correlation-id", message.getJMSCorrelationID());

            String text = "reply";

            sendReply(message, text);
        } catch (JMSException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);
        } finally {
            MDC.remove("message-id");
            MDC.remove("correlation-id");
            MDC.remove("action-id");
        }
    }

    private void sendReply(Message message, String text) throws JMSException {
        Destination replyTo = message.getJMSReplyTo();

        if (replyTo == null) {
            LOG.info("No reply-to set. Don't need to answer!");

        }

        JMSProducer producer = context.createProducer();

        @SuppressWarnings("unchecked")
        Enumeration<String> props = (Enumeration<String>) message.getPropertyNames();
        while (props.hasMoreElements()) {
            String key = props.nextElement();

            producer.setProperty(key, message.getStringProperty(key));
        }

        producer
                .setJMSCorrelationID(message.getJMSCorrelationID())
                .send(replyTo, text);
    }
}
