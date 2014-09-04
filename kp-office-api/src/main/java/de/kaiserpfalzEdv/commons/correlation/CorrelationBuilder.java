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

import de.kaiserpfalzEdv.commons.security.ActingSystem;
import de.kaiserpfalzEdv.commons.security.Subject;
import org.apache.commons.lang3.builder.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * The builder builds request and response ids.
 *
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public class CorrelationBuilder<T extends Correlation> implements Builder<T> {
    private static final Logger LOG = LoggerFactory.getLogger(CorrelationBuilder.class);

    private UUID correlationId;
    private UUID requestId;
    private UUID responseId;
    private long sequence = 0;
    private boolean multipleRepsonses = false;
    private boolean nextResponse = false;

    private Subject requester;
    private ActingSystem system;

    @SuppressWarnings("unchecked") // The generic T needs this ...
    @Override
    public T build() {
        T result;
        if (correlationId == null)    generateCorrelationId();

        result = (T) (
                responseId == null
                ? new RequestCorrelationImpl(correlationId, getRequestId(), sequence, requester, system)
                : new ResponseCorrelationImpl(getRequest(), getResponseId(), sequence, multipleRepsonses, nextResponse)
        );

        LOG.trace("Created: {}", result);
        return result;
    }

    private RequestCorrelation getRequest() {
        return new RequestCorrelationImpl(correlationId, getRequestId(), sequence, requester, system);
    }

    private UUID getRequestId() {
        return requestId != null ? requestId : UUID.randomUUID();
    }

    private UUID getResponseId() {
        return responseId != null ? responseId : UUID.randomUUID();
    }


    public CorrelationBuilder<T> withCorrelationId(final UUID id) {
        this.correlationId = id;
        return this;
    }


    public CorrelationBuilder<T> withRequestId(final UUID id) {
        this.requestId = id;
        return this;
    }


    public CorrelationBuilder<T> withResponseId(final UUID id) {
        this.responseId = id;
        return this;
    }


    public CorrelationBuilder<T> withRequester(final Subject principal) {
        this.requester = principal;
        return this;
    }


    public CorrelationBuilder<T> withSystem(final ActingSystem principal) {
        this.system = principal;
        return this;
    }


    public CorrelationBuilder<T> inResponseTo(final RequestCorrelation request) {
        withRequest(request);
        generateResponseId();

        return this;
    }

    public CorrelationBuilder<T> inResponseTo(final RequestCorrelation request, final long sequence) {
        withRequest(request);
        generateResponseId();
        withSequence(sequence);

        return this;
    }

    public CorrelationBuilder<T> withSequence(final long sequence) {
        this.sequence = sequence;

        return this;
    }

    public CorrelationBuilder<T> increaseSequence() {
        this.sequence++;

        return this;
    }


    public CorrelationBuilder<T> withRequest(final RequestCorrelation request) {
        checkArgument(request != null, "Can't give <null> as request!");

        //noinspection ConstantConditions
        this.correlationId = request.getId();
        this.requestId = request.getRequestId();

        return this;
    }


    public CorrelationBuilder<T> withResponse(final ResponseCorrelation response) {
        this.correlationId = response.getId();
        this.requestId = response.getInResponseTo();
        this.responseId = response.getResponseId();
        this.sequence = response.getSequence();

        return this;
    }


    public CorrelationBuilder<T> hasMultipleResponses() {
        this.multipleRepsonses = true;

        return this;
    }

    public CorrelationBuilder<T> isSingleResponse() {
        this.multipleRepsonses = false;

        return this;
    }


    public CorrelationBuilder<T> hasNextResponse() {
        this.nextResponse = true;

        return this;
    }

    public CorrelationBuilder<T> isLastResponse() {
        this.nextResponse = false;

        return this;
    }


    public CorrelationBuilder<T> generateCorrelationId() {
        this.correlationId = UUID.randomUUID();

        return this;
    }


    public CorrelationBuilder<T> generateRequestId() {
        this.requestId = UUID.randomUUID();

        return this;
    }


    public CorrelationBuilder<T> generateResponseId() {
        this.responseId = UUID.randomUUID();

        return this;
    }
}
