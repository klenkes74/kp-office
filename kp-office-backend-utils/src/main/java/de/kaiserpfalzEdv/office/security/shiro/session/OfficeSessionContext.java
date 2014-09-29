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

package de.kaiserpfalzEdv.office.security.shiro.session;

import de.kaiserpfalzEdv.office.security.OfficeTicket;
import org.apache.shiro.session.mgt.DefaultSessionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;


/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public class OfficeSessionContext extends DefaultSessionContext {
    private static final Logger LOG = LoggerFactory.getLogger(OfficeSessionContext.class);

    private static final String TICKET = DefaultSessionContext.class.getName() + ".TICKET";

    public OfficeTicket getTicket() {
        return (OfficeTicket) get(TICKET);
    }

    public void setTicket(OfficeTicket ticket) {
        nullSafePut(TICKET, ticket);
    }

    public Serializable getSessionId() {
        Serializable result = getTypedValue(TICKET, OfficeTicket.class).getTicket().toString();

        LOG.debug("Returning session id: {}", result);

        return result;
    }

    public void setSessionId(Serializable sessionId) {
        // do nothing.
    }

}
