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

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public class ResponseCorrelationImpl extends AbstractCorrelation implements ResponseCorrelation {
    private static final long serialVersionUID = 9027321813336033390L;

    private UUID requestId;
    private boolean multipleResponses = false;
    private boolean nextResponse = false;


    @SuppressWarnings({"UnusedDeclaration", "deprecation"})
    @Deprecated // Only for Jackson, JAX-B and JPA!
    public ResponseCorrelationImpl() {}

    @SuppressWarnings("deprecation")
    public ResponseCorrelationImpl(final RequestCorrelation request, final UUID id, final long sequenceNumber,
                                   final boolean multipleResponses, final boolean nextResponse) {
        super(request.getSessionId(), id, sequenceNumber, request.getRequester(), request.getSystem());

        setInResponseTo(request.getId());
        setInMessageSequence(multipleResponses);
        setNextResponse(nextResponse);
    }


    @Override
    public UUID getInResponseTo() {
        return requestId;
    }

    @Deprecated // Only for Jackson, JAX-B and JPA!
    public void setInResponseTo(final UUID id) {
        checkArgument(id != null, "Need a valid original request id for a response!");

        requestId = id;
    }

    @Override
    public boolean isInMessageSequence() {
        return multipleResponses;
    }

    private void setInMessageSequence(boolean multipleResponses) {
        this.multipleResponses = multipleResponses;
    }

    public boolean hasNextResponse() {
        return nextResponse;
    }

    private void setNextResponse(boolean nextResponse) {
        this.nextResponse = nextResponse;
    }


    @Override
    public boolean isRequest() {
        return false;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .appendSuper(super.toString())
                .append("request", getInResponseTo())
                .append("hasNext", hasNextResponse())
                .append("isInSequence", isInMessageSequence())
                .toString();
    }
}
