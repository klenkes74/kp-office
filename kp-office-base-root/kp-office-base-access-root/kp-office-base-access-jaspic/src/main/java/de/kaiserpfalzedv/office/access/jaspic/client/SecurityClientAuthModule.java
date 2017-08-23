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
import java.util.Set;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.message.AuthException;
import javax.security.auth.message.AuthStatus;
import javax.security.auth.message.MessageInfo;
import javax.security.auth.message.MessagePolicy;
import javax.security.auth.message.module.ClientAuthModule;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.kaiserpfalzedv.office.access.api.users.OfficePrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-04-01
 */
public class SecurityClientAuthModule implements ClientAuthModule {
    private static final Logger LOG = LoggerFactory.getLogger(SecurityClientAuthModule.class);

    private MessagePolicy requestPolicy;
    private MessagePolicy responsePolicy;
    private CallbackHandler handler;
    private Map<String, String> properties;

    @SuppressWarnings("unchecked")
    @Override
    public void initialize(
            final MessagePolicy requestPolicy,
            final MessagePolicy responsePolicy,
            final CallbackHandler handler,
            final Map options
    ) throws AuthException {
        LOG.debug("{}:initialize called: requestPolicy={}, responsePolicy={}, handler={}",
                  this, requestPolicy, responsePolicy, handler, options
        );
        options.forEach((k, v) -> LOG.trace("      property {}: {}", k, v));

        this.requestPolicy = requestPolicy;
        this.responsePolicy = responsePolicy;
        this.handler = handler;
        this.properties = (Map<String, String>) options;
    }

    @Override
    public Class[] getSupportedMessageTypes() {
        LOG.debug("{}:getSupportedMessageTypes called", this);

        return new Class[]{HttpServletRequest.class, HttpServletResponse.class};
    }

    @Override
    public AuthStatus secureRequest(MessageInfo messageInfo, Subject clientSubject) throws AuthException {
        LOG.debug("{}:secureRequest called: messageInfo={}, clientSubject={}", this, messageInfo, clientSubject);
        LOG.trace("      Is request: {}", messageInfo.getRequestMessage() != null);
        messageInfo.getMap().forEach((k, v) -> LOG.trace("      messageInfo[{}]: {}", k, v));

        return AuthStatus.SEND_SUCCESS;
    }

    @Override
    public AuthStatus validateResponse(MessageInfo messageInfo, Subject clientSubject, Subject serviceSubject) throws AuthException {
        LOG.debug("{}:validateResponse called: messageInfo={}, clientSubject={}, serviceSubject={}",
                  this, messageInfo, clientSubject, serviceSubject
        );
        LOG.trace("      Is request: {}", messageInfo.getRequestMessage() != null);
        messageInfo.getMap().forEach((k, v) -> LOG.trace("      messageInfo[{}]: {}", k, v));

        // TODO 2017-03-18 klenkes74 Implement validateResponse

        return AuthStatus.SEND_SUCCESS;
    }

    @Override
    public void cleanSubject(final MessageInfo messageInfo, Subject subject) throws AuthException {
        LOG.debug("{}:cleanSubject called: messageInfo={}, subject={}", this, messageInfo, subject);
        LOG.trace("      working on subject with principals: {}", subject.getPrincipals());
        LOG.trace("      Is request: {}", messageInfo.getRequestMessage() != null);
        messageInfo.getMap().forEach((k, v) -> LOG.trace("      messageInfo[{}]: {}", k, v));

        Set<OfficePrincipal> principalsToRemove = subject.getPrincipals(OfficePrincipal.class);

        LOG.debug("Removing principals: {}", principalsToRemove);
        subject.getPrincipals().removeAll(principalsToRemove);
    }
}
