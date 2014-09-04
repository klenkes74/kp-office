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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public class ResponseCorrelationImpl extends AbstractCorrelation implements ResponseCorrelation {
    private static final long serialVersionUID = -107721607764025942L;

    private UUID requestId;
    private UUID responseId;
    private boolean multipleResponses = false;
    private boolean nextResponse = false;

    @SuppressWarnings({"UnusedDeclaration", "deprecation"})
    @Deprecated // Only for Jackson, JAX-B and JPA!
    public ResponseCorrelationImpl() {}

    @SuppressWarnings("deprecation")
    public ResponseCorrelationImpl(final RequestCorrelation request, final UUID id, final long sequence,
                                   final boolean multipleResponses, final boolean nextResponse) {
        super(request, sequence);

        setResponseId(id);
        setInResponseTo(request.getRequestId());
        setMultipleResponses(multipleResponses);
        setNextResponse(nextResponse);
    }


    @Override
    public UUID getResponseId() {
        return responseId;
    }

    @Deprecated // Only for Jackson, JAX-B and JPA!
    public void setResponseId(final UUID id) {
        checkArgument(id != null, "Need a valid response id for a response!");

        responseId = id;
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
    public boolean hasMultipleResponses() {
        return multipleResponses;
    }

    private void setMultipleResponses(boolean multipleResponses) {
        this.multipleResponses = multipleResponses;
    }

    public boolean hasNextResponse() {
        return nextResponse;
    }

    private void setNextResponse(boolean nextResponse) {
        this.nextResponse = nextResponse;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        ResponseCorrelationImpl rhs = (ResponseCorrelationImpl) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(rhs))
                .append(this.getResponseId(), rhs.getResponseId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(getResponseId())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .appendSuper(super.toString())
                .append("response", getResponseId())
                .append("request", getInResponseTo())
                .append("hasNext", hasNextResponse())
                .append("isInSequence", hasMultipleResponses())
                .toString();
    }
}
