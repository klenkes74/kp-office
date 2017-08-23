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
import javax.security.auth.message.MessageInfo;
import javax.security.auth.message.config.ClientAuthConfig;
import javax.security.auth.message.config.ClientAuthContext;

import de.kaiserpfalzedv.office.access.jaspic.WrappedAuthException;
import de.kaiserpfalzedv.office.common.api.BuilderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-04-01
 */
public class SecurityClientAuthConfig implements ClientAuthConfig {
    private static final Logger LOG = LoggerFactory.getLogger(SecurityClientAuthConfig.class);


    private String layer;
    private String appContext;
    private CallbackHandler handler;
    private Map<String, String> properties;


    public SecurityClientAuthConfig(
            final String layer,
            final String appContext,
            final CallbackHandler handler,
            final Map<String, String> properties
    ) {
        LOG.debug("***** Creating: {}", this);

        this.layer = layer;
        this.appContext = appContext;
        this.handler = handler;
        this.properties = properties;

        LOG.trace("      layer: {}", this.layer);
        LOG.trace("      application context: {}", this.appContext);
        LOG.trace("      callback handler: {}", this.handler);
        LOG.trace("      properties: {}", this.properties);
    }


    @Override
    public ClientAuthContext getAuthContext(
            final String authContextID,
            final Subject clientSubject,
            final Map properties
    ) throws AuthException {
        LOG.trace("{}:getAuthContext called: authContextID={}, clientSubject={}, properties={}",
                  this, authContextID, clientSubject, properties
        );
        properties.forEach((k, v) -> LOG.trace("      property {}: {}", k, v));

        try {
            return new SecurityClientAuthContextBuilder()
                    .withAuthContextId(authContextID)
                    .withClientSubject(clientSubject)
                    .withProperties(properties)
                    .withHandler(handler)
                    .build();
        } catch (BuilderException e) {
            throw new AuthException(e.getClasz().getSimpleName() + " caught: " + e.getMessage());
        } catch (WrappedAuthException e) {
            throw (AuthException) e.getCause();
        }
    }

    @Override
    public String getMessageLayer() {
        LOG.trace("{}:getMessageLayer called", this);

        return layer;
    }

    @Override
    public String getAppContext() {
        LOG.debug("{}:getAppContext called", this);

        return appContext;
    }

    @Override
    public String getAuthContextID(final MessageInfo messageInfo) {
        LOG.trace("{}:getAuthContextID called: messageInfo={}", this, messageInfo);

        return appContext;
    }

    @Override
    public void refresh() {
        LOG.trace("{}:refresh called", this);

        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.paladinsinn.security.jaspic.client.SecurityClientAuthConfig.refresh
    }

    @Override
    public boolean isProtected() {
        LOG.trace("{}:isProtected called", this);

        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.paladinsinn.security.jaspic.client.SecurityClientAuthConfig.isProtected

        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
