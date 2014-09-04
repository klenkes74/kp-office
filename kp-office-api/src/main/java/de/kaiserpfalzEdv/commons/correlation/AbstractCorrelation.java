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
public class AbstractCorrelation implements Correlation {
    private static final long serialVersionUID = -7618633982740668486L;

    private UUID id;
    private long sequence = 0;

    @Deprecated // Only for Jackson, JAX-B and JPA!
    public AbstractCorrelation() {}

    @SuppressWarnings("deprecation")
    public AbstractCorrelation(final Correlation correlation, long sequence) {
        setId(correlation.getId());
        setSequence(sequence);
    }

    @SuppressWarnings("deprecation")
    public AbstractCorrelation(final UUID correlationId, long sequence) {
        setId(correlationId);
        setSequence(sequence);
    }


    public UUID getId() {
        return id;
    }

    @Deprecated // Only for Jackson, JAX-B and JPA!
    public void setId(final UUID id) {
        this.id = id;
    }


    @Override
    public long getSequence() {
        return sequence;
    }

    @Deprecated // Only for Jackson, JAX-B and JPA!
    public void setSequence(final long sequence) {
        checkArgument(sequence >= 0, "The sequence has to be a positive number (current value: %s)", sequence);

        this.sequence = sequence;
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
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", getId())
                .append("sequence", getSequence())
                .toString();
    }
}
