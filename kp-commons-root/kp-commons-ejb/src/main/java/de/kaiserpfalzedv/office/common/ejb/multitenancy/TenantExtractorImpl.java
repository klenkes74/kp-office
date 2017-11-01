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

package de.kaiserpfalzedv.office.common.ejb.multitenancy;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.interceptor.InvocationContext;

import de.kaiserpfalzedv.office.common.api.multitenancy.Tenant;
import de.kaiserpfalzedv.office.common.api.multitenancy.TenantExtractor;
import de.kaiserpfalzedv.office.common.api.multitenancy.TenantHoldingServiceRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Extracts the UUID annotated with {@link Tenant} as long as the class or method is annotated with
 * {@link TenantHoldingServiceRequest}.
 * 
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-08-13
 */
@ApplicationScoped
public class TenantExtractorImpl implements TenantExtractor {
    private static final Logger LOG = LoggerFactory.getLogger(TenantExtractorImpl.class);

    @Override
    public Optional<UUID> extract(InvocationContext ctx) {
        Optional<UUID> result = Optional.empty();

        Object[] parameters = ctx.getParameters();
        Method method = ctx.getMethod();
        Annotation[][] annotations = method.getParameterAnnotations();

        int parameterIndex = getParameterWithAnnotation(annotations, Tenant.class);

        if (parameterIndex >= 0) {
            result = getParameter(parameters[parameterIndex], UUID.class);
        }

        LOG.trace("Extracted tenant from parameters: {}", result.orElse(null));
        return result;
    }

    private int getParameterWithAnnotation(
            final Annotation[][] annotations,
            final Class<? extends Annotation> annotation
    ) {
        int parameterIndex = 0;

        for (Annotation[] parameterAnnotations : annotations) {
            if (parameterAnnotations.length > 0) {
                for (Annotation a : parameterAnnotations) {
                    if (annotation.isAssignableFrom(a.getClass())) {
                        return parameterIndex;
                    }
                }
            }

            parameterIndex++;
        }

        return -1;
    }

    @SuppressWarnings("unchecked")
    private <T> Optional<T> getParameter(Object parameter, Class<T> parameterType) {
        if (parameterType.isAssignableFrom(parameter.getClass())) {
            return Optional.of((T) parameter);
        }

        return Optional.empty();
    }
}
