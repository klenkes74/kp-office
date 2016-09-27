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

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;

import de.kaiserpfalzedv.office.common.MessageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-27
 */
@Dependent
public class ResponseSender {
    private static final Logger LOG = LoggerFactory.getLogger(ResponseSender.class);

    private JMSContext context;

    @Inject
    public ResponseSender(
            @JMSConnectionFactory("jms/tenantCF") final JMSContext context
    ) {
        this.context = context;
    }

    public void sendReply(MessageInfo info, final String text) throws JMSException {
        Destination replyTo = info.getReplyTo();

        if (replyTo == null) {
            LOG.info("No reply-to set. Don't need to answer!");

        }

        JMSProducer producer = context.createProducer();

        info.forEach(producer::setProperty);

        producer
                .setJMSCorrelationID(info.get("correlation-id"))
                .send(replyTo, text);
    }

}
