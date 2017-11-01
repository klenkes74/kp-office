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

package de.kaiserpfalzedv.office.metainfo.impl;

import java.lang.reflect.Method;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.office.common.api.Logging;
import de.kaiserpfalzedv.office.metainfo.api.LicenseService;
import de.kaiserpfalzedv.office.metainfo.api.Licensed;
import de.kaiserpfalzedv.office.metainfo.api.NotLicensedException;
import de.kaiserpfalzedv.office.metainfo.api.OfficeLicense;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The licensing interceptor. It reads the license from the injected {@link LicenseService} and then checks if the
 * feature specified by {@link Licensed} is really licensed.
 *
 * If the feature is not licensed, then an {@link de.kaiserpfalzedv.office.license.api.NotLicensedException} is thrown. We need an unchecked
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-08-17
 */
@Interceptor
@Licensed
public class LicenseInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(LicenseInterceptor.class);

    private OfficeLicense license;


    @Inject
    public LicenseInterceptor(@NotNull final LicenseService licenseService) {
        license = licenseService.getLicense();
    }


    @AroundInvoke
    public Object intercept(final InvocationContext ctx) throws Exception {
        try {
            checkLicense(ctx);
            
            return ctx.proceed();
        } finally {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Intercepted call to: method={}, feature={}", getFullMethodName(ctx), getFeatureFromAnnotation(ctx));
            }
        }
    }


    private void checkLicense(final InvocationContext ctx) {
        checkAvailableLicense();
        checkLicensedFeature(ctx);
    }

    private String getFeatureFromAnnotation(final InvocationContext ctx) {
        Method method = ctx.getMethod();
        Licensed result = method.getAnnotation(Licensed.class);

        if (result == null) {
            Class clasz = method.getDeclaringClass();
            LOG.trace("Method not annotated. Checking declaring class: {}", clasz.getCanonicalName());
            result = (Licensed) clasz.getAnnotation(Licensed.class);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Found license Annotation: {}={}", result.getClass().getSimpleName(), result.value());
        }

        return result.value();
    }

    private void checkAvailableLicense() {
        if (license == null) {
            throw new IllegalStateException("No license service running. Can't check the license");
        }
    }


    private String getFullMethodName(final InvocationContext ctx) {
        return new StringBuilder()
                .append(ctx.getMethod().getDeclaringClass().getCanonicalName())
                .append(".")
                .append(ctx.getMethod().getName())
                .toString();
    }

    private void checkLicensedFeature(InvocationContext ctx) {
        String feature = getFeatureFromAnnotation(ctx);
        if (!license.isLicensed(feature)) {
            Logging.OPLOG.error("Feature not covered by license: license={}, feature={}, coveredFeatures={}",
                                license.getId(), feature, license.getOptions()
            );
            throw new NotLicensedException(license, feature);
        }
    }
}
