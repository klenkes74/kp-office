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
public abstract class AbstractCorrelation implements Correlation {
    private static final long serialVersionUID = -4407586493428545064L;

    private UUID id;
    private UUID sessionId;
    private long sequence = 0;


    public AbstractCorrelation(final Correlation correlation, long sequence) {
        setSessionId(correlation.getSessionId());
        setId(correlation.getId());
        setSequence(sequence);
    }

    public AbstractCorrelation(final UUID sessionId, final UUID id, final long sequence) {
        setSessionId(sessionId);
        setId(id);
        setSequence(sequence);
    }


    public UUID getId() {
        return id;
    }

    private void setId(final UUID id) {
        this.id = id;
    }


    @Override
    public UUID getSessionId() {
        return sessionId;
    }

    private void setSessionId(UUID sessionId) {
        this.sessionId = sessionId;
    }


    @Override
    public long getSequence() {
        return sequence;
    }

    private void setSequence(final long sequence) {
        checkArgument(sequence >= 0, "The sequence has to be a positive number (current value: %s)", sequence);

        this.sequence = sequence;
    }


    public boolean isResponse() {
        return !isRequest();
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
        AbstractCorrelation rhs = (AbstractCorrelation) obj;
        return new EqualsBuilder()
                .append(this.getId(), rhs.getId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getId())
                .append(getSequence())
                .toHashCode();
    }

    @Override
    public String toString() {
        ToStringBuilder result = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .appendSuper(super.toString());

        if (sessionId != null)
            result.append("session", getSessionId());

        result
                .append("id", getId())
                .append("sequence", getSequence());

        return result.toString();
    }
}
