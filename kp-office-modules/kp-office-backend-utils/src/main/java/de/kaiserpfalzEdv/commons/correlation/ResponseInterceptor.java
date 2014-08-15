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

package de.kaiserpfalzEdv.commons.correlation;

import org.slf4j.MDC;

import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.lang.annotation.Annotation;
import java.util.UUID;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
@Interceptor @ResponseCorrelated
public class ResponseInterceptor extends CorrelationInterceptor {
    public ResponseCorrelation getCorrelation(final InvocationContext ctx) {
        CorrelationBuilder<ResponseCorrelation> result = new CorrelationBuilder<>();


        Annotation[][] annotations = ctx.getMethod().getParameterAnnotations();

        for (int i = 0; i < annotations.length; i++) {
            for (Annotation a : annotations[i]) {
                if (CorrelationId.class.isAssignableFrom(a.annotationType())
                        && ResponseCorrelation.class.isAssignableFrom(ctx.getParameters()[i].getClass())) {
                    return (ResponseCorrelation) ctx.getParameters()[i];
                }


                if (CorrelationId.class.isAssignableFrom(a.annotationType())
                        && UUID.class.isAssignableFrom(ctx.getParameters()[i].getClass()))
                    result.withCorrelationId((UUID) ctx.getParameters()[i]);

                if (RequestId.class.isAssignableFrom(a.annotationType())
                        && UUID.class.isAssignableFrom(ctx.getParameters()[i].getClass()))
                    result.withRequestId((UUID) ctx.getParameters()[i]);
            }
        }

        return result.build();
    }

    @Override
    public <T extends Correlation> void putCorrelationIntoMDC(T correlation) {
        ResponseCorrelation c = (ResponseCorrelation) correlation;

        MDC.put("correlationId", c.getCorrelationID().toString());
        MDC.put("requestId", c.getInResponseTo().toString());
        MDC.put("responseId", c.getResponseID().toString());
    }

}
