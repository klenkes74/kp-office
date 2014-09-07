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

package de.kaiserpfalzEdv.office.security.cdi;

import de.kaiserpfalzEdv.office.tenants.NullTenant;
import de.kaiserpfalzEdv.office.tenants.Tenant;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.lang.annotation.Annotation;

/**
 * @author Hendy Irawan
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
@Secured @Interceptor
public class ShiroInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(ShiroInterceptor.class);

    @Inject
    Subject subject;

    @Inject
    org.apache.shiro.mgt.SecurityManager securityManager;


    @AroundInvoke
    public Object interceptGet(InvocationContext ctx) throws Exception {
        MDC.put("user", subject.getPrincipal().toString());
        LOG.debug("Securing {} {}", new Object[] { ctx.getMethod(), ctx.getParameters() });
        LOG.trace("Principal is: {}", subject.getPrincipal());

        final Class<? extends Object> runtimeClass = ctx.getTarget().getClass();
        LOG.trace("Runtime extended classes: {}", (Object[])runtimeClass.getClasses());
        LOG.trace("Runtime implemented interfaces: {}", (Object[])runtimeClass.getInterfaces());
        LOG.trace("Runtime annotations ({}): {}", runtimeClass.getAnnotations().length, runtimeClass.getAnnotations());
        final Class<?> declaringClass = ctx.getMethod().getDeclaringClass();
        LOG.trace("Declaring class: {}", declaringClass);
        LOG.trace("Declaring extended classes: {}", (Object[])declaringClass.getClasses());
        LOG.trace("Declaring annotations ({}): {}", declaringClass.getAnnotations().length, declaringClass.getAnnotations());
        String entityName;
        try {
            Secured namedResource = runtimeClass.getAnnotation(Secured.class);
            entityName = namedResource.value();
            if (StringUtils.isBlank(entityName)) throw new NullPointerException();

            LOG.trace("Got @NamedResource={}", entityName);
        } catch (NullPointerException e) {
            entityName = declaringClass.getSimpleName().toLowerCase(); // TODO: should be lowerFirst camelCase
            LOG.warn("@Secured not named, inferred from declaring classname: {} -> {}", declaringClass.getSimpleName(), entityName);
        }

        String action = getAction(ctx);
        Tenant tenant = getTenantParameter(ctx);
        String permission = String.format("%s:%s:%s", entityName, tenant.getDisplayNumber(), action);
        MDC.put("permission", permission);
        MDC.put("tenant", tenant.getDisplayName());

        LOG.trace("Checking permission '{}' for user '{}'", permission, subject.getPrincipal());
        try {
            subject.checkPermission(permission);
        } catch (Exception e) {
            LOG.error("Access denied for {} - {}: {}", subject.getPrincipal(), e.getClass().getName(), e.getMessage());
            throw e;
        }

        try {
            LOG.debug("Security check passed: {}", permission);
            return ctx.proceed();
        } finally {
            MDC.remove("tenant");
            MDC.remove("permission");
            MDC.remove("action");
            MDC.remove("user");
        }
    }

    private String getAction(InvocationContext ctx) {
        String action = "admin";

        if (ctx.getMethod().getName().matches("create[A-Z].*"))
            action = "create";

        if (ctx.getMethod().getName().matches("(retrieve|get|list)[A-Z].*"))
            action = "retrieve";

        if (ctx.getMethod().getName().matches("(update|set)[A-Z].*"))
            action = "update";

        if (ctx.getMethod().getName().matches("delete[A-Z].*"))
            action = "delete";
        return action;
    }

    private Tenant getTenantParameter(final InvocationContext ctx) {
        Tenant result = new NullTenant();

        Annotation[][] annotations = ctx.getMethod().getParameterAnnotations();

        Class<?>[] parameterTypes =  ctx.getMethod().getParameterTypes();

        for (int i = 0; i < parameterTypes.length ; i++) {
            Class<?> clasz = parameterTypes[i];

            if (Tenant.class.isAssignableFrom(clasz)) {
                Annotation[] parameterAnnotation = annotations[i];

                for (Annotation a : parameterAnnotation) {
                    if (TenantMarker.class.isAssignableFrom(a.annotationType()))
                        result = (Tenant) ctx.getParameters()[i];
                }
            }
        }

        return result;
    }
}
