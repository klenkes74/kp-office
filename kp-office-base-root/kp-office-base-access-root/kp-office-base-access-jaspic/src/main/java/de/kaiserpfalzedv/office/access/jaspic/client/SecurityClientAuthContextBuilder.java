/*
 * Copyright 2017 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzedv.office.access.jaspic.client;

import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.message.AuthException;

import de.kaiserpfalzedv.office.access.jaspic.WrappedAuthException;
import de.kaiserpfalzedv.office.common.api.BuilderException;
import org.apache.commons.lang3.builder.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-04-01
 */
public class SecurityClientAuthContextBuilder implements Builder<SecurityClientAuthContext> {
    private static final Logger LOG = LoggerFactory.getLogger(SecurityClientAuthContextBuilder.class);

    private String authContextId;
    private Subject clientSubject;
    private CallbackHandler handler;
    private Map properties;


    @SuppressWarnings("unchecked")
    @Override
    public SecurityClientAuthContext build() throws BuilderException {
        LOG.trace("Building new client authentication context: authContextId={}, clientSubject={}, handler={} properties={}",
                  authContextId, clientSubject, handler, properties
        );
        properties.forEach((k, v) -> LOG.trace("      property {}: {}", k, v));

        try {
            return new SecurityClientAuthContext(handler, properties);
        } catch (AuthException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);

            throw new WrappedAuthException(e);
        }
    }

    public void validate() throws BuilderException {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.paladinsinn.security.jaspic.client.SecurityClientAuthContextBuilder.validate
    }

    public SecurityClientAuthContextBuilder withAuthContextId(final String authContextId) {
        this.authContextId = authContextId;
        return this;
    }

    public SecurityClientAuthContextBuilder withClientSubject(final Subject clientSubject) {
        this.clientSubject = clientSubject;
        return this;
    }

    public SecurityClientAuthContextBuilder withHandler(final CallbackHandler handler) {
        this.handler = handler;
        return this;
    }

    public SecurityClientAuthContextBuilder withProperties(@SuppressWarnings("unchecked") final Map properties) {
        this.properties = properties;
        return this;
    }
}
