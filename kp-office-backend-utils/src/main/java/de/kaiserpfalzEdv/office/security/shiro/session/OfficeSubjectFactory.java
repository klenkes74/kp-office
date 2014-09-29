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
import de.kaiserpfalzEdv.office.security.shiro.OfficeToken;
import de.kaiserpfalzEdv.office.security.shiro.loginAuthorization.OfficeLoginToken;
import de.kaiserpfalzEdv.office.security.shiro.ticketAuthorization.OfficeTicketToken;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.mgt.DefaultSubjectFactory;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes
 * @since 2014Q
 */
public class OfficeSubjectFactory extends DefaultSubjectFactory {
    private static final Logger LOG = LoggerFactory.getLogger(OfficeSubjectFactory.class);

    private String loginRealm;
    private String ticketRealm;

    public void setLoginRealm(final String realm) {
        this.loginRealm = realm;
    }

    public void setTicketRealm(final String realm) {
        this.ticketRealm = realm;
    }

    @Override
    public Subject createSubject(SubjectContext context) {

        boolean authenticated = context.isAuthenticated();

        if (authenticated) {

            AuthenticationToken token = context.getAuthenticationToken();

            if (token != null && token instanceof OfficeToken) {
                OfficeToken officeToken = (OfficeToken) token;
                // set the authenticated flag of the context to true only if the CAS subject is not in a remember me mode
                if (officeToken.isRememberMe()) {
                    LOG.debug("RememberMe-Login. Needs to reauthenticate!");
                    context.setAuthenticated(false);
                }
            }
        }

        org.apache.shiro.mgt.SecurityManager securityManager = context.resolveSecurityManager();
        Session session = context.resolveSession();
        boolean sessionCreationEnabled = context.isSessionCreationEnabled();
        PrincipalCollection principals = context.resolvePrincipals();
        authenticated = context.resolveAuthenticated();
        String host = context.resolveHost();


        OfficeToken token = (OfficeToken) context.getAuthenticationToken();

        String realm;
        OfficeTicket ticket = null;
        if (token instanceof OfficeLoginToken) {
            realm = loginRealm;
        } else {
            realm = ticketRealm;
            ticket = ((OfficeTicketToken) token).getCredentials();
        }

        return new OfficeSubjectImpl(realm, ticket, principals, authenticated, host, session, sessionCreationEnabled, securityManager);
    }
}
