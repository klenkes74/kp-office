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

package de.kaiserpfalzedv.commons.api.data;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.*;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-08-15
 */
public class ValidityDuration implements Serializable {
    public static final ZoneId UTC = ZoneId.of(ZoneOffset.UTC.getId());

    private OffsetDateTime start;
    private OffsetDateTime end;

    public ValidityDuration() {}

    public ValidityDuration(@NotNull final OffsetDateTime start, @NotNull final OffsetDateTime end) {
        this.start = start;
        this.end = end;
    }

    public ValidityDuration(@NotNull final Instant start, @NotNull final Instant end) {
        this.start = start.atZone(ValidityDuration.UTC).toOffsetDateTime();
        this.end = end.atZone(ValidityDuration.UTC).toOffsetDateTime();
    }

    public boolean isValid(final OffsetDateTime date) {
        return isValid(date.toInstant());
    }

    public boolean isValid(final Instant instant) {
        return
                (start.toInstant().isBefore(instant) || start.toInstant().equals(instant))
                        && (instant.isBefore(end.toInstant()) || instant.equals(end.toInstant()));
    }

    public boolean isValid(final ZonedDateTime dateTime) {
        return isValid(dateTime.toInstant());
    }

    public boolean isValid(final LocalDate date) {
        return isValid(date.atStartOfDay());
    }

    public boolean isValid(final LocalDateTime date) {
        return isValid(date.toInstant(ZoneOffset.UTC));
    }

    public boolean isNotValid() {
        return !isValid();
    }

    public boolean isValid() {
        return isValid(OffsetDateTime.now(UTC).toInstant());
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(start)
                .append(end)
                .toHashCode();
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
        ValidityDuration rhs = (ValidityDuration) obj;
        return new EqualsBuilder()
                .append(this.getValidFrom(), rhs.getValidFrom())
                .append(this.getValidTill(), rhs.getValidTill())
                .isEquals();
    }

    public OffsetDateTime getValidFrom() {
        return start;
    }

    public void setValidFrom(final OffsetDateTime date) {
        this.start = date;
    }

    public OffsetDateTime getValidTill() {
        return end;
    }

    public void setValidTill(final OffsetDateTime date) {
        this.end = date;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("start", start)
                .append("end", end)
                .toString();
    }
}
