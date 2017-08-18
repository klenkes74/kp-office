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

package de.kaiserpfalzedv.office.license.impl;

import java.lang.reflect.Method;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import de.kaiserpfalzedv.office.license.api.Licensed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-08-17
 */
@Interceptor
@Licensed
public class LicenseInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(LicenseInterceptor.class);


    @AroundInvoke
    public Object intercept(final InvocationContext ctx) throws Exception {
        try {
            return ctx.proceed();
        } finally {
            LOG.info("Intercepted call to: method={}, license={}", getFullMethodName(ctx), getNeededLicense(ctx));
        }
    }

    private String getFullMethodName(final InvocationContext ctx) {
        return new StringBuilder()
                .append(ctx.getMethod().getDeclaringClass().getCanonicalName())
                .append(".")
                .append(ctx.getMethod().getName())
                .toString();
    }

    private String getNeededLicense(final InvocationContext ctx) {
        Method method = ctx.getMethod();
        Licensed result = method.getAnnotation(Licensed.class);

        if (result == null) {
            Class clasz = method.getDeclaringClass();
            LOG.trace("Method not annotated. Checking declaring class: {}", clasz.getCanonicalName());
            result = (Licensed) clasz.getAnnotation(Licensed.class);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Found Annotation: {}={}", result.getClass().getSimpleName(), result.value());
        }

        return result.value();
    }


}
