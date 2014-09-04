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

package de.kaiserpfalzEdv.commons.security;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.ExecutionException;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author klenkes
 * @since 2014Q
 */
public class ShiroSubject implements org.apache.shiro.subject.Subject {
    private static final Logger LOG = LoggerFactory.getLogger(ShiroSubject.class);

    Subject delegate;

    @Override
    public Object getPrincipal() {
        return delegate.getPrincipal();
    }

    @Override
    public PrincipalCollection getPrincipals() {
        return delegate.getPrincipals();
    }

    @Override
    public boolean isPermitted(String permission) {
        return delegate.isPermitted(permission);
    }

    @Override
    public boolean isPermitted(Permission permission) {
        return delegate.isPermitted(permission);
    }

    @Override
    public boolean[] isPermitted(String... permissions) {
        return delegate.isPermitted(permissions);
    }

    @Override
    public boolean[] isPermitted(List<Permission> permissions) {
        return delegate.isPermitted(permissions);
    }

    @Override
    public boolean isPermittedAll(String... permissions) {
        return delegate.isPermittedAll(permissions);
    }

    @Override
    public boolean isPermittedAll(Collection<Permission> permissions) {
        return delegate.isPermittedAll(permissions);
    }

    @Override
    public void checkPermission(String permission) throws AuthorizationException {
        delegate.checkPermission(permission);
    }

    @Override
    public void checkPermission(Permission permission) throws AuthorizationException {
        delegate.checkPermission(permission);
    }

    @Override
    public void checkPermissions(String... permissions) throws AuthorizationException {
        delegate.checkPermissions(permissions);
    }

    @Override
    public void checkPermissions(Collection<Permission> permissions) throws AuthorizationException {
        delegate.checkPermissions(permissions);
    }

    @Override
    public boolean hasRole(String roleIdentifier) {
        return delegate.hasRole(roleIdentifier);
    }

    @Override
    public boolean[] hasRoles(List<String> roleIdentifiers) {
        return delegate.hasRoles(roleIdentifiers);
    }

    @Override
    public boolean hasAllRoles(Collection<String> roleIdentifiers) {
        return delegate.hasAllRoles(roleIdentifiers);
    }

    @Override
    public void checkRole(String roleIdentifier) throws AuthorizationException {
        delegate.checkRole(roleIdentifier);
    }

    @Override
    public void checkRoles(Collection<String> roleIdentifiers) throws AuthorizationException {
        delegate.checkRoles(roleIdentifiers);
    }

    @Override
    public void checkRoles(String... roleIdentifiers) throws AuthorizationException {
        delegate.checkRoles(roleIdentifiers);
    }

    @Override
    public void login(AuthenticationToken token) throws AuthenticationException {
        delegate.login(token);
    }

    @Override
    public boolean isAuthenticated() {
        return delegate.isAuthenticated();
    }

    @Override
    public boolean isRemembered() {
        return delegate.isRemembered();
    }

    @Override
    public Session getSession() {
        return delegate.getSession();
    }

    @Override
    public Session getSession(boolean create) {
        return delegate.getSession(create);
    }

    @Override
    public void logout() {
        delegate.logout();
    }

    @Override
    public <V> V execute(Callable<V> callable) throws ExecutionException {
        return delegate.execute(callable);
    }

    @Override
    public void execute(Runnable runnable) {
        delegate.execute(runnable);
    }

    @Override
    public <V> Callable<V> associateWith(Callable<V> callable) {
        return delegate.associateWith(callable);
    }

    @Override
    public Runnable associateWith(Runnable runnable) {
        return delegate.associateWith(runnable);
    }

    @Override
    public void runAs(PrincipalCollection principals) throws NullPointerException, IllegalStateException {
        delegate.runAs(principals);
    }

    @Override
    public boolean isRunAs() {
        return delegate.isRunAs();
    }

    @Override
    public PrincipalCollection getPreviousPrincipals() {
        return delegate.getPreviousPrincipals();
    }

    @Override
    public PrincipalCollection releaseRunAs() {
        return delegate.releaseRunAs();
    }
}
