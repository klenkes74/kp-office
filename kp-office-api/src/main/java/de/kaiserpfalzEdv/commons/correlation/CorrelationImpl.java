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

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public class CorrelationImpl implements RequestCorrelation, ResponseCorrelation {
    private static final long serialVersionUID = -8036686329558154277L;

    private UUID correlationID;
    private UUID requestID;
    private UUID responseID;

    public CorrelationImpl(final UUID correlationID, final UUID requestID, final UUID responseID) {
        this.correlationID = correlationID;
        this.requestID = requestID;
        this.responseID = responseID;
    }

    @Override
    public UUID getCorrelationID() {
        return correlationID;
    }

    @Override
    public UUID getRequestID() {
        return requestID;
    }

    @Override
    public UUID getResponseID() {
        return responseID;
    }

    @Override
    public UUID getInResponseTo() {
        return requestID;
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
        CorrelationImpl rhs = (CorrelationImpl) obj;
        return new EqualsBuilder()
                .append(this.correlationID, rhs.correlationID)
                .append(this.requestID, rhs.requestID)
                .append(this.responseID, rhs.responseID)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(correlationID)
                .append(requestID)
                .append(responseID)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("correlationID", correlationID)
                .append("requestID", requestID)
                .append("responseID", responseID)
                .toString();
    }
}
