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

import javax.security.auth.message.config.AuthConfigFactory;
import javax.security.auth.message.config.AuthConfigProvider;
import javax.security.auth.message.config.RegistrationListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-04-18
 */
public class SecurityAuthConfigFactory extends AuthConfigFactory {
    private static final Logger LOG = LoggerFactory.getLogger(SecurityAuthConfigFactory.class);

    private Map<String, String> properties;


    public SecurityAuthConfigFactory() {
        LOG.debug("{}:<init> constructor called.");

    }

    public SecurityAuthConfigFactory(final String contextId) {
        LOG.debug("{}:<init> constructor called: {}", contextId);

    }


    @Override
    public AuthConfigProvider getConfigProvider(
            final String layer,
            final String appContext,
            final RegistrationListener listener
    ) {
        LOG.debug("{}:getConfigProvider called: layer={}, appContext={}, listener={}", layer, appContext, listener);

        return new SecurityAuthConfigProvider(properties, this);
    }

    @Override
    public String registerConfigProvider(
            final String className,
            final Map properties,
            final String layer,
            final String appContext,
            final String description
    ) {
        LOG.debug("{}:registerConfigProvider called: className={}, properties={}, layer={}, appContext={}, description={}",
                  className, properties, layer, appContext, description
        );
        properties.forEach((k, v) -> LOG.trace("     Property {}: {}", k, v));

        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.paladinsinn.security.jaspic.SecurityAuthConfigFactory.registerConfigProvider
        return null;
    }

    @Override
    public String registerConfigProvider(
            final AuthConfigProvider provider,
            final String layer,
            final String appContext,
            final String description
    ) {
        LOG.debug("{}:registerConfigProvider called: provider={}, layer={}, appContext={}, description={}",
                  provider, layer, appContext, description
        );

        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.paladinsinn.security.jaspic.SecurityAuthConfigFactory.registerConfigProvider
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean removeRegistration(final String registrationID) {
        LOG.debug("{}:removeRegistration called: {}", registrationID);

        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.paladinsinn.security.jaspic.SecurityAuthConfigFactory.removeRegistration
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String[] detachListener(final RegistrationListener listener, final String layer, final String appContext) {
        LOG.debug("{}:detachListener called: listener={}, layer={}, appContext={}",
                  listener, layer, appContext
        );

        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.paladinsinn.security.jaspic.SecurityAuthConfigFactory.detachListener
        return new String[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String[] getRegistrationIDs(final AuthConfigProvider provider) {
        LOG.debug("{}:getRegistrationIDs called: provider={}", provider);

        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.paladinsinn.security.jaspic.SecurityAuthConfigFactory.getRegistrationIDs
        return new String[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public RegistrationContext getRegistrationContext(final String registrationID) {
        LOG.debug("{}:getRegistrationContext called: registrationID={}", registrationID);

        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.paladinsinn.security.jaspic.SecurityAuthConfigFactory.getRegistrationContext
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void refresh() {
        LOG.debug("{}:refresh called.");

        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.paladinsinn.security.jaspic.SecurityAuthConfigFactory.refresh
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
