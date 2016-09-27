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

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import de.kaiserpfalzedv.office.common.BaseSystemException;
import de.kaiserpfalzedv.office.common.MessageInfo;
import de.kaiserpfalzedv.office.commons.shared.converter.NoMatchingConverterFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-25
 */
@MessageDriven(
        activationConfig = {
                @ActivationConfigProperty(propertyName = "connectionFactoryLookup", propertyValue = "jms/tenantCF"),
                @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/tenant"),
                @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
        }
)
public class TenantMDB implements MessageListener {
    private static final Logger LOG = LoggerFactory.getLogger(TenantMDB.class);

    private TenantWorker worker;

    @Inject
    public TenantMDB(final TenantWorker worker) {
        this.worker = worker;
    }

    @Override
    public void onMessage(Message message) {
        MessageInfo info = new MessageInfo(message);
        info.setMDC();

        try {
            worker.workOn(info, message.getBody(String.class));
        } catch (JMSException | NoMatchingConverterFoundException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);

            throw new BaseSystemException(e);
        } finally {
            info.removeMDC();
        }
    }
}
