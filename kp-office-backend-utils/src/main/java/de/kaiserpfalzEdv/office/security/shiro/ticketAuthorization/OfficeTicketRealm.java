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

package de.kaiserpfalzEdv.office.security.shiro.ticketAuthorization;

import de.kaiserpfalzEdv.office.security.InvalidTicketException;
import de.kaiserpfalzEdv.office.security.OfficeAuthenticationSystemException;
import de.kaiserpfalzEdv.office.security.OfficePrincipal;
import de.kaiserpfalzEdv.office.security.OfficeSubject;
import de.kaiserpfalzEdv.office.security.OfficeTicket;
import de.kaiserpfalzEdv.office.security.SecurityClient;
import de.kaiserpfalzEdv.office.security.shiro.OfficeAuthenticationException;
import net.logstash.logback.marker.ObjectFieldsAppendingMarker;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
@Named
public class OfficeTicketRealm extends AuthorizingRealm {
    private static final Logger LOG = LoggerFactory.getLogger(OfficeTicketRealm.class);


    private SecurityClient verifier;


    @Inject
    public OfficeTicketRealm(
            final SecurityClient verifier,
            final CacheManager cacheManger
    ) {
        this.verifier = verifier;
        setCacheManager(cacheManger);

        setAuthenticationTokenClass(OfficeTicketToken.class);

        LOG.trace("Created: {} (Realm name: {}", this, getName());
    }


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken inToken) throws AuthenticationException {
        LOG.debug("Trying to authenticate: {}", inToken);
        if (inToken == null) return null;

        try {
            OfficeTicketToken token = (OfficeTicketToken) inToken;
            OfficeTicket ticket = token.getCredentials();

            OfficeSubject subject = verifier.createSubject(ticket);
            token.setPrincipal(subject.getAllPrincipal().iterator().next());
            LOG.trace("Loaded principals: {}", subject.getAllPrincipal());

            Set<OfficePrincipal> principals = subject.getAllPrincipal();
            PrincipalCollection principalCollection = new SimplePrincipalCollection(principals, getName());

            SimpleAuthenticationInfo result = new SimpleAuthenticationInfo(principalCollection, token.getCredentials());
            LOG.info("Authenticated by token {}: {}", token, result);
            return result;
        } catch (ClassCastException | IllegalStateException | InvalidTicketException e) {
            LOG.error("Caught " + e.getClass().getSimpleName() + " while authenticating.", e);

            throw new OfficeAuthenticationSystemException(e);
        }
    }


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection inPrincipals) {
        SimpleAuthorizationInfo result = new SimpleAuthorizationInfo();

        for (Object p : inPrincipals.fromRealm(getName())) {
            if (!(p instanceof OfficeTicket))
                continue;

            OfficePrincipal principal = (OfficePrincipal) p;
            LOG.trace("Checking principal: {}", principal);

            try {
                OfficeSubject subject = verifier.createSubject(principal);

                addRoles(result, principal, subject);
                addPermissions(result, principal, subject);
            } catch (de.kaiserpfalzEdv.office.security.OfficeAuthenticationException | IllegalStateException e) {
                LOG.error("Caught " + e.getClass().getSimpleName() + " while authorizing: " + inPrincipals, e);
                throw new OfficeAuthenticationException(e);
            }
        }

        return result;
    }

    private void addRoles(SimpleAuthorizationInfo result, OfficePrincipal principal, OfficeSubject subject) {
        Set<String> roles = subject.getRoles();
        LOG.trace(stringSetMarker("roles", principal, roles), "Adding roles from subject", roles);
        result.addRoles(subject.getRoles());
    }

    private ObjectFieldsAppendingMarker stringSetMarker(final String type, OfficePrincipal principal, Set<String> roles) {
        Map<String, Object> result = new HashMap<>(roles.size());
        result.put("principal", principal);
        result.put(type, roles);

        return new ObjectFieldsAppendingMarker(result);
    }

    private void addPermissions(SimpleAuthorizationInfo result, OfficePrincipal principal, OfficeSubject subject) {
        Set<String> permissions = subject.getPermissions();
        LOG.trace(stringSetMarker("permissions", principal, permissions), "Adding permissions from subject", permissions);
        result.addStringPermissions(permissions);
    }
}
