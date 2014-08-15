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

import org.apache.commons.lang3.builder.Builder;

import java.util.UUID;

/**
 * The builder builds request and response ids.
 *
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public class CorrelationBuilder<T extends Correlation> implements Builder<T> {
    private UUID correlation;
    private UUID request;
    private UUID response;

    @Override
    public T build() {
        if (correlation == null)    generateCorrelationId();
        if (request == null)        generateRequestId();
        if (response == null)       response = request;

        return (T) new CorrelationImpl(correlation, request, response);
    }


    public CorrelationBuilder<T> withCorrelationId(final UUID id) {
        this.correlation = id;
        return this;
    }


    public CorrelationBuilder<T> withRequestId(final UUID id) {
        this.request = id;
        return this;
    }


    public CorrelationBuilder<T> withResponseId(final UUID id) {
        this.response = id;
        return this;
    }


    public CorrelationBuilder<T> inResponseTo(final RequestCorrelation request) {
        withRequest(request);
        generateResponseId();

        return this;
    }


    public CorrelationBuilder<T> withRequest(final RequestCorrelation request) {
        this.correlation = request.getCorrelationID();
        this.request = request.getRequestID();

        return this;
    }


    public CorrelationBuilder<T> withResponse(final ResponseCorrelation response) {
        this.correlation = response.getCorrelationID();
        this.request = response.getInResponseTo();
        this.response = response.getResponseID();

        return this;
    }


    public CorrelationBuilder<T> generateCorrelationId() {
        this.correlation = UUID.randomUUID();

        return this;
    }


    public CorrelationBuilder<T> generateRequestId() {
        this.request = UUID.randomUUID();

        return this;
    }


    public CorrelationBuilder<T> generateResponseId() {
        this.response = UUID.randomUUID();

        return this;
    }
}
