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

package de.kaiserpfalzEdv.office.security.shiro;

import com.google.common.collect.Sets;
import de.kaiserpfalzEdv.office.security.OfficePrincipal;
import de.kaiserpfalzEdv.office.security.OfficeSubject;
import de.kaiserpfalzEdv.office.security.OfficeTicket;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DelegatingSubject;

import java.util.Set;

/**
 * @author klenkes
 * @since 2014Q
 */
public class OfficeSubjectImpl extends DelegatingSubject implements OfficeSubject {

    private String realm;
    private OfficeTicket ticket;

    public OfficeSubjectImpl(String realm, OfficeTicket ticket, SecurityManager securityManager) {
        super(securityManager);
        this.realm = realm;
        this.ticket = ticket;
    }

    public OfficeSubjectImpl(String realm, OfficeTicket ticket, PrincipalCollection principals, boolean authenticated, String host, Session session, SecurityManager securityManager) {
        super(principals, authenticated, host, session, securityManager);
        this.realm = realm;
        this.ticket = ticket;
    }

    public OfficeSubjectImpl(String realm, OfficeTicket ticket, PrincipalCollection principals, boolean authenticated, String host, Session session, boolean sessionCreationEnabled, SecurityManager securityManager) {
        super(principals, authenticated, host, session, sessionCreationEnabled, securityManager);
        this.realm = realm;
        this.ticket = ticket;
    }

    public String getRealmName() {
        return realm;
    }

    public OfficeTicket getTicket() {
        return ticket;
    }


    @Override
    public Set<OfficePrincipal> getAllPrincipal() {
        //noinspection unchecked
        return Sets.newHashSet(getPrincipals().fromRealm(realm));
    }

    @Override
    public Set<String> getPermissions() {
        throw new UnsupportedOperationException("Sorry, this method is unsupported due to security reasons!");
    }

    @Override
    public Set<String> getRoles() {
        throw new UnsupportedOperationException("Sorry, this method is unsupported due to security reasons!");
    }
}
