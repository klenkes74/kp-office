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
import javax.security.auth.message.AuthStatus;
import javax.security.auth.message.MessageInfo;
import javax.security.auth.message.config.ServerAuthContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-04-01
 */
public class SecurityServerAuthContext implements ServerAuthContext {
    private static final Logger LOG = LoggerFactory.getLogger(SecurityServerAuthContext.class);

    private SecurityServerAuthModule serverAuthModule;


    public SecurityServerAuthContext(final CallbackHandler handler, Map<String, String> properties) throws AuthException {
        serverAuthModule = new SecurityServerAuthModule();
//        serverAuthModule.initialize(null, null, handler, properties);
    }

    @Override
    public AuthStatus validateRequest(MessageInfo messageInfo, Subject clientSubject, Subject serviceSubject) throws AuthException {
        LOG.debug("{}:validateRequest called: messageInfo={}, clientSubject={}, serviceSubject={}",
                  this, messageInfo, clientSubject, serviceSubject
        );

        return serverAuthModule.validateRequest(messageInfo, clientSubject, serviceSubject);
    }

    @Override
    public AuthStatus secureResponse(MessageInfo messageInfo, Subject serviceSubject) throws AuthException {
        LOG.debug("{}:secureResponse called: messageInfo={}, serviceSubject={}",
                  this, messageInfo, serviceSubject
        );

        return serverAuthModule.secureResponse(messageInfo, serviceSubject);
    }

    @Override
    public void cleanSubject(MessageInfo messageInfo, Subject subject) throws AuthException {
        LOG.debug("{}:cleanSubject called: messageInfo={}, subject={}", this, messageInfo, subject);
        LOG.trace("      working on subject with principals: {}", subject.getPrincipals());
        LOG.trace("      Is request: {}", messageInfo.getRequestMessage() != null);
        messageInfo.getMap().forEach((k, v) -> LOG.trace("      messageInfo[{}]: {}", k, v));

        serverAuthModule.cleanSubject(messageInfo, subject);
    }
}
