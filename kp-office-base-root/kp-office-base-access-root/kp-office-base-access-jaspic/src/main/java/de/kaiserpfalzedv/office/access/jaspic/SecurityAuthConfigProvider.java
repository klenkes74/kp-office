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

package de.kaiserpfalzedv.office.access.jaspic;

import java.util.Map;

import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.message.AuthException;
import javax.security.auth.message.config.AuthConfigFactory;
import javax.security.auth.message.config.AuthConfigProvider;
import javax.security.auth.message.config.ClientAuthConfig;
import javax.security.auth.message.config.ServerAuthConfig;

import de.kaiserpfalzedv.paladinsinn.commons.api.BuilderValidationException;
import de.kaiserpfalzedv.paladinsinn.security.jaspic.client.SecurityClientAuthConfig;
import de.kaiserpfalzedv.paladinsinn.security.jaspic.server.SecurityServerAuthConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-04-01
 */
public class SecurityAuthConfigProvider implements AuthConfigProvider {
    private static final Logger LOG = LoggerFactory.getLogger(SecurityAuthConfigProvider.class);

    private static final String CALLBACK_HANDLER_PROPERTY_NAME = "authconfigprovider.client.callbackhandler";

    private Map<String, String> properties;


    public SecurityAuthConfigProvider(Map<String, String> properties, AuthConfigFactory factory) {
        LOG.info("***** Creating: {}", this);
        LOG.trace("      authentication configuration factory: {}", factory);
        properties.forEach((k, v) -> LOG.trace("      property {}: {}", k, v));

        this.properties = properties;

        if (factory != null) {
            LOG.info("Registering sec auth config provider to factory ...");

            factory.registerConfigProvider(
                    this,
                    null,
                    null,
                    "Paladins Inn Security Authentication"
            );
        }
    }


    @Override
    public ClientAuthConfig getClientAuthConfig(String layer, String appContext, CallbackHandler handler) throws AuthException {
        LOG.debug("{}:getClientAuthConfig: layer={}, appContext={}, handler={}", this, layer, appContext, handler);

        if (handler == null) {
            handler = createDefaultCallbackHandler();
        }

        try {
            return new AuthConfigBuilder<SecurityClientAuthConfig>(properties)
                    .withLayer(layer)
                    .withAppContext(appContext)
                    .withHandler(handler)
                    .withProperties(properties)
                    .buildClientAuthConfig();
        } catch (BuilderValidationException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);

            throw new AuthException(e.getName() + " caught: " + e.getMessage());
        }
    }

    @Override
    public ServerAuthConfig getServerAuthConfig(String layer, String appContext, CallbackHandler handler) throws AuthException {
        LOG.debug("{}:getServerAuthConfig: layer={}, appContext={}, handler={}", this, layer, appContext, handler);

        try {
            return new AuthConfigBuilder<SecurityServerAuthConfig>(properties)
                    .withLayer(layer)
                    .withAppContext(appContext)
                    .withHandler(handler)
                    .withProperties(properties)
                    .buildServerAuthConfig();
        } catch (BuilderValidationException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);

            throw new AuthException(e.getName() + " caught: " + e.getMessage());
        }
    }

    @Override
    public void refresh() {
        LOG.debug("{}:refresh()", this);

        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.paladinsinn.security.jaspic.SecurityAuthConfigProvider.refresh
    }

    private CallbackHandler createDefaultCallbackHandler() throws AuthException {
        // TODO 2017-04-17 klenkes Read from properties instead of from a system property.
        String cbClassName = System.getProperty(CALLBACK_HANDLER_PROPERTY_NAME);

        if (cbClassName == null || cbClassName.isEmpty()) {
            throw new AuthException("No default callback handler set via system property: " + CALLBACK_HANDLER_PROPERTY_NAME);
        }

        try {
            return (CallbackHandler) Thread.currentThread()
                                           .getContextClassLoader()
                                           .loadClass(cbClassName)
                                           .newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            LOG.error("{}:createDefaultCallbackHandler: {} caught: {}", this, e.getClass()
                                                                               .getSimpleName(), e.getMessage());

            throw new AuthException(e.getMessage());
        }
    }
}
