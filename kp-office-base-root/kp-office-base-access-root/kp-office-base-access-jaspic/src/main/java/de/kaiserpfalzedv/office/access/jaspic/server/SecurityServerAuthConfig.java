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

package de.kaiserpfalzedv.office.access.jaspic.server;

import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.message.AuthException;
import javax.security.auth.message.MessageInfo;
import javax.security.auth.message.config.ServerAuthConfig;
import javax.security.auth.message.config.ServerAuthContext;

import de.kaiserpfalzedv.office.access.jaspic.WrappedAuthException;
import de.kaiserpfalzedv.office.common.api.BuilderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-04-01
 */
public class SecurityServerAuthConfig implements ServerAuthConfig {
    private static final Logger LOG = LoggerFactory.getLogger(SecurityServerAuthConfig.class);

    private String layer;
    private String appContext;
    private CallbackHandler handler;
    private Map<String, String> properties;

    public SecurityServerAuthConfig(
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

        LOG.trace("      layer: {}", layer);
        LOG.trace("      application context: {}", appContext);
        LOG.trace("      callback handler: {}", handler);
        LOG.trace("      properties: {}", properties);
    }


    @Override
    public ServerAuthContext getAuthContext(String authContextID, Subject serviceSubject, Map properties) throws AuthException {
        LOG.debug("{}:getAuthContext called: authContextID={}, subject={}, properties={}", this, authContextID, serviceSubject, properties);
        properties.forEach((k, v) -> LOG.trace("      property {}: {}", k, v));

        try {
            return new SecurityServerAuthContextBuilder()
                    .withAuthContextId(authContextID)
                    .withServiceSubject(serviceSubject)
                    .withHandler(handler)
                    .withProperties(properties)
                    .build();
        } catch (BuilderException e) {
            throw new AuthException(e.getClass().getSimpleName() + " caught: " + e.getMessage());
        } catch (WrappedAuthException e) {
            throw (AuthException) e.getCause();
        }
    }

    @Override
    public String getMessageLayer() {
        LOG.debug("{}:getMessageLayer called", this);

        return layer;
    }

    @Override
    public String getAppContext() {
        LOG.debug("{}:getAppContext called", this);

        return appContext;
    }

    @Override
    public String getAuthContextID(MessageInfo messageInfo) {
        LOG.debug("{}:getAuthContextID called: messageInfo={}", this, messageInfo);

        return appContext;
    }

    @Override
    public void refresh() {
        LOG.debug("{}:refresh called", this);

        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.paladinsinn.security.jaspic.server.SecurityServerAuthConfig.refresh
    }

    @Override
    public boolean isProtected() {
        LOG.debug("{}:isProtected called", this);

        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.paladinsinn.security.jaspic.server.SecurityServerAuthConfig.isProtected

        return false;
    }

    public Map<String, String> getProperties() {
        LOG.debug("{}:getProperties called", this);

        return properties;
    }
}
