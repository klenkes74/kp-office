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
public class RequestCorrelationImpl extends AbstractCorrelation implements RequestCorrelation {
    private static final long serialVersionUID = 8499437843915417296L;


    private UUID requestId;
    private Subject requester;
    private ActingSystem system;


    @SuppressWarnings({"deprecation", "UnusedDeclaration"})
    @Deprecated // Only for Jackson, JAX-B and JPA!
    public RequestCorrelationImpl() {}

    @SuppressWarnings("deprecation")
    public RequestCorrelationImpl(
            final UUID correlationId, final UUID requestId, final long sequence,
            final Subject requester, final ActingSystem system) {
        super(correlationId, sequence);

        setRequestId(requestId);
        setRequester(requester);
        setSystem(system);
    }


    @Override
    public UUID getRequestId() {
        return requestId;
    }

    @Deprecated // Only for Jackson, JAX-B and JPA!
    public void setRequestId(final UUID id) {
        checkArgument(id != null, "Can't set <null> as request id!");

        this.requestId = id;
    }


    @Override
    public Subject getRequester() {
        return requester;
    }

    @Deprecated // Only for Jackson, JAX-B and JPA!
    public void setRequester(final Subject requester) {
        this.requester = requester;
    }


    @Override
    public ActingSystem getSystem() {
        return system;
    }

    @Deprecated // Only for Jackson, JAX-B and JPA!
    public void setSystem(final ActingSystem system) {
        this.system = system;
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
        RequestCorrelationImpl rhs = (RequestCorrelationImpl) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(rhs))
                .append(this.getRequestId(), rhs.getRequestId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(getRequestId())
                .toHashCode();
    }

    @Override
    public String toString() {
        ToStringBuilder result = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .appendSuper(super.toString())
                .append("request", getRequestId());

        if (requester != null)
            result.append("requester", getRequester());

        if (system != null)
            result.append("system", getSystem());

        return result.toString();
    }
}
