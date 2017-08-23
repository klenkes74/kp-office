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

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.enterprise.inject.spi.CDI;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.message.AuthException;
import javax.security.auth.message.AuthStatus;
import javax.security.auth.message.MessageInfo;
import javax.security.auth.message.MessagePolicy;
import javax.security.auth.message.callback.CallerPrincipalCallback;
import javax.security.auth.message.callback.GroupPrincipalCallback;
import javax.security.auth.message.module.ServerAuthModule;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.kaiserpfalzedv.office.access.api.users.LoginService;
import de.kaiserpfalzedv.office.access.api.users.OfficePrincipal;
import de.kaiserpfalzedv.office.access.api.users.PasswordFailureException;
import de.kaiserpfalzedv.office.common.api.cdi.Implementation;
import de.kaiserpfalzedv.office.common.api.cdi.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-04-01
 */
public class SecurityServerAuthModule implements ServerAuthModule {
    private static final Logger LOG = LoggerFactory.getLogger(SecurityServerAuthModule.class);

    private static final String IS_MANDATORY = "javax.security.auth.message.MessagePolicy.isMandatory";

    private MessagePolicy requestPolicy;
    private MessagePolicy responsePolicy;
    private CallbackHandler handler;
    private Map<String, String> properties;


    private LoginService loginService;


    public SecurityServerAuthModule() {
        LOG.debug("{}:<init> constructor called.", this);
    }


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

        LOG.info("{}:initialize found loginService={}", this, this.loginService);

        this.requestPolicy = requestPolicy;
        this.responsePolicy = responsePolicy;
        this.handler = handler;
        this.properties = (Map<String, String>) options;

        this.loginService = getLoginServiceFromCDI();

        LOG.info("{}:initialize using: loginService={}", this, this.loginService);
    }

    private LoginService getLoginServiceFromCDI() throws AuthException {
        LoginService result = null;

        Iterator<LoginService> loginIterator = CDI.current().select(LoginService.class).iterator();
        Class<? extends Annotation> serviceType;

        switch (this.properties.get("loginServiceType")) {
            case "mock":
                serviceType = Mock.class;
                break;

            case "worker":
            default:
                serviceType = Implementation.class;
                break;
        }

        while (loginIterator.hasNext()) {
            LoginService service = loginIterator.next();
            if (service.getClass().isAnnotationPresent(serviceType)) {
                result = service;
            }
        }

        if (result == null) {
            LOG.error("No login service of type '{}' found.", serviceType);
            throw new AuthException("No login service of type '" + serviceType + "' found.");
        }

        return result;
    }

    @Override
    public Class[] getSupportedMessageTypes() {
        LOG.debug("{}:getSupportedMessageTypes called", this);

        return new Class[]{HttpServletRequest.class, HttpServletResponse.class};
    }

    @Override
    public AuthStatus validateRequest(final MessageInfo messageInfo, Subject clientSubject, Subject serviceSubject) throws AuthException {
        LOG.debug("{}:validateRequest called: messageInfo={}, clientSubject={}, serviceSubject={}",
                  this, messageInfo, clientSubject, serviceSubject
        );

        OfficePrincipal user = loginUser(messageInfo);

        try {
            this.handleCallbacks(messageInfo, clientSubject, user);
        } catch (UnsupportedCallbackException | IOException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);

            return sendStatus(messageInfo, AuthStatus.FAILURE);
        }

        return sendStatus(messageInfo, AuthStatus.SUCCESS);
    }

    private OfficePrincipal loginUser(final MessageInfo messageInfo) throws AuthException {
        String user = getUserFromInfo(messageInfo);
        String password = getPasswordFromInfo(messageInfo);

        try {
            return this.loginService.login(user, password);
        } catch (PasswordFailureException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);

            throw new AuthException("Invalid username or password.");
        }
    }

    private void handleCallbacks(final MessageInfo messageInfo, final Subject clientSubject, final OfficePrincipal user)
            throws IOException, UnsupportedCallbackException {
        HashSet<Callback> callbacks = new HashSet<>(2);

        LOG.debug("Adding principal to (un)protected resource request: {}", user);
        callbacks.add(new CallerPrincipalCallback(clientSubject, user));

        if (isProtectedResourceRequest(messageInfo)) {
            String[] roles = getRolesFromUser(user);

            if (roles.length >= 1) {
                LOG.debug("Adding roles to protected resource request: {}", (Object[]) roles);
                callbacks.add(new GroupPrincipalCallback(clientSubject, roles));
            } else {
                LOG.info(
                        "No roles for the principal and the protected resource request: {}",
                        messageInfo.getRequestMessage()
                );
            }
        }

        this.handler.handle(callbacks.toArray(new Callback[1]));
    }

    private AuthStatus sendStatus(final MessageInfo messageInfo, final AuthStatus status) {
        LOG.info("Sending '{}' for: {}", status.toString(), messageInfo.getRequestMessage());

        return status;
    }

    private String getUserFromInfo(final MessageInfo messageInfo) {
        LOG.debug("Retrieving user from message info: {}", messageInfo);
        //TODO 2017-03-18 klenkes Implement retrieving user name from messageInfo
        return null;
    }

    private String getPasswordFromInfo(final MessageInfo messageInfo) {
        LOG.debug("Retrieving password from message info: {}", messageInfo);
        //TODO 2017-03-18 klenkes Implement retrieving password from messageInfo
        return null;
    }

    /**
     * @param messageInfo The information blurb of this request.
     *
     * @return TRUE if the resource requested is unprotected; FALSE if it is protected.
     */
    private boolean isProtectedResourceRequest(MessageInfo messageInfo) {
        return messageInfo.getMap().containsKey(IS_MANDATORY)
                && Boolean.parseBoolean((String) messageInfo.getMap().get(IS_MANDATORY));
    }

    /**
     * @param user The user to extract the roles from.
     *
     * @return The names of the roles assigned to the user.
     */
    private String[] getRolesFromUser(final OfficePrincipal user) {
        if (user.getRoles().isEmpty()) {
            return new String[]{};
        }


        ArrayList<String> roles = new ArrayList<>(user.getRoles().size());
        user.getRoles().forEach(r -> roles.add(r.getName()));

        return roles.toArray(new String[1]);
    }

    @Override
    public AuthStatus secureResponse(final MessageInfo messageInfo, Subject serviceSubject) throws AuthException {
        LOG.debug("{}:secureResponse called: messageInfo={}, serviceSubject={}", this, messageInfo, serviceSubject);

        return sendStatus(messageInfo, AuthStatus.SEND_SUCCESS);
    }

    @Override
    public void cleanSubject(final MessageInfo messageInfo, Subject subject) throws AuthException {
        LOG.debug("{}:cleanSubject called: messageInfo={}, subject={}", this, messageInfo, subject);
        LOG.trace("      working on subject with principals: {}", subject.getPrincipals());

        Set<OfficePrincipal> principalsToRemove = subject.getPrincipals(OfficePrincipal.class);

        LOG.info("Removing principals: {}", principalsToRemove);
        subject.getPrincipals().removeAll(principalsToRemove);
    }
}
