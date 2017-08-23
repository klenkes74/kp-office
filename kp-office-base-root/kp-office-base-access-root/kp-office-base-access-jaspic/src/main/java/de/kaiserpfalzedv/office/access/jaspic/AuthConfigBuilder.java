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
import javax.security.auth.message.config.AuthConfig;

import de.kaiserpfalzedv.paladinsinn.commons.api.Builder;
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
public class AuthConfigBuilder<T extends AuthConfig> implements Builder<T> {
    private static final Logger LOG = LoggerFactory.getLogger(AuthConfigBuilder.class);

    private boolean serverAuthConfig = true;

    private String layer;
    private String appContext;
    private CallbackHandler handler;
    private Map<String, String> properties;


    public AuthConfigBuilder(final Map<String, String> properties) {
        LOG.debug("***** Creating: {}", this);
        properties.forEach((k, v) -> LOG.trace("      Property {}: {}", k, v));

        this.properties = properties;
    }

    public SecurityClientAuthConfig buildClientAuthConfig() throws BuilderValidationException {
        client();

        return (SecurityClientAuthConfig) build();
    }

    public AuthConfigBuilder client() {
        serverAuthConfig = false;
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T build() throws BuilderValidationException {
        LOG.trace("Building new client authentication context: layer={}, appContext={}, handler={}",
                  layer, appContext, handler
        );

        T result;
        if (serverAuthConfig) {
            result = (T) new SecurityServerAuthConfig(layer, appContext, handler, properties);
        } else {
            result = (T) new SecurityClientAuthConfig(layer, appContext, handler, properties);
        }

        return result;
    }

    public SecurityServerAuthConfig buildServerAuthConfig() throws BuilderValidationException {
        server();

        return (SecurityServerAuthConfig) build();
    }

    public AuthConfigBuilder server() {
        serverAuthConfig = true;
        return this;
    }

    @Override
    public void validate() throws BuilderValidationException {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.paladinsinn.security.jaspic.client.AuthConfigBuilder.validate
    }

    public AuthConfigBuilder withLayer(final String layer) {
        this.layer = layer;
        return this;
    }

    public AuthConfigBuilder withAppContext(final String appContext) {
        this.appContext = appContext;
        return this;
    }

    public AuthConfigBuilder withHandler(final CallbackHandler handler) {
        this.handler = handler;
        return this;
    }

    public AuthConfigBuilder withProperties(final Map<String, String> properties) {
        this.properties = properties;
        return this;
    }
}
