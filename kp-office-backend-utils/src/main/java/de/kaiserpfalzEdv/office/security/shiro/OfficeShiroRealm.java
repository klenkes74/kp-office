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

import com.google.common.base.Converter;
import de.kaiserpfalzEdv.office.security.InvalidTicketException;
import de.kaiserpfalzEdv.office.security.OfficeAuthenticationSystemException;
import de.kaiserpfalzEdv.office.security.OfficePermission;
import de.kaiserpfalzEdv.office.security.OfficePermissionConverter;
import de.kaiserpfalzEdv.office.security.OfficePrincipal;
import de.kaiserpfalzEdv.office.security.OfficeSubject;
import de.kaiserpfalzEdv.office.security.OfficeTicket;
import de.kaiserpfalzEdv.office.security.OfficeToken;
import de.kaiserpfalzEdv.office.security.SecurityClient;
import net.logstash.logback.marker.ObjectFieldsAppendingMarker;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
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
public class OfficeShiroRealm extends AuthorizingRealm {
    private static final Logger LOG = LoggerFactory.getLogger(OfficeShiroRealm.class);


    private SecurityClient verifier;
    private Converter<OfficePermission, String> converter;


    @Inject
    public OfficeShiroRealm(
            final SecurityClient verifier,
            final OfficePermissionConverter converter
    ) {
        this.verifier = verifier;
        this.converter = converter;

        LOG.trace("Created: {}", this);
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken inToken) throws AuthenticationException {
        if (inToken == null) return null;

        try {
            OfficeToken token = (OfficeToken) inToken;

            OfficeTicket ticket = token.getTicket();
            OfficeSubject subject = verifier.createSubject(ticket);

            Set<OfficePrincipal> principals = subject.getAllPrincipal();
            PrincipalCollection principalCollection = new SimplePrincipalCollection(principals, getName());

            LOG.info("Authenticated: {}", subject.getAllPrincipal().iterator().next().getName());
            return new SimpleAuthenticationInfo(principalCollection, ticket);
        } catch (ClassCastException | IllegalStateException | InvalidTicketException e) {
            throw new OfficeAuthenticationSystemException(e);
        }
    }


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection inPrincipals) {
        SimpleAuthorizationInfo result = new SimpleAuthorizationInfo();

        for (Object p : inPrincipals.fromRealm(OfficeShiroRealm.class.getName())) {
            OfficePrincipal principal = (OfficePrincipal)p;

            try {
                OfficeSubject subject = verifier.createSubject(principal);

                addRoles(result, principal, subject);
                addPermissions(result, principal, subject);
            } catch (de.kaiserpfalzEdv.office.security.OfficeAuthenticationException | IllegalStateException e) {
                throw new OfficeAuthenticationException(e);
            }
        }

        return result;
    }

    private void addRoles(SimpleAuthorizationInfo result, OfficePrincipal principal, OfficeSubject subject) {
        Set<String> roles = subject.getRoles();
        LOG.trace(roleMarker(principal, roles), "Adding roles from subject", roles);
        result.addRoles(subject.getRoles());
    }
    private ObjectFieldsAppendingMarker roleMarker(OfficePrincipal principal, Set<String> roles) {
        Map<String, Object> result = new HashMap<>(roles.size());
        result.put("principal", principal);
        result.put("roles", roles);

        return new ObjectFieldsAppendingMarker(result);
    }

    private void addPermissions(SimpleAuthorizationInfo result, OfficePrincipal principal, OfficeSubject subject) {
        Set<OfficePermission> permissions = subject.getPermissions();
        LOG.trace(permissionMarker(principal, permissions), "Adding permissions from subject", permissions);
        for (OfficePermission permission : permissions) {
            result.addStringPermission(converter.convert(permission));
        }
    }
    private ObjectFieldsAppendingMarker permissionMarker(OfficePrincipal principal, Set<OfficePermission> permissions) {
        Map<String, Object> result = new HashMap<>(permissions.size());
        result.put("principal", principal);
        result.put("permissions", permissions);

        return new ObjectFieldsAppendingMarker(result);
    }
}
