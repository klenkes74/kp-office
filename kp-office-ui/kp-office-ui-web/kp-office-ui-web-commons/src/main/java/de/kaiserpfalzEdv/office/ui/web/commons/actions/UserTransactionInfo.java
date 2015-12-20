/*
 * Copyright 2015 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzEdv.office.ui.web.commons.actions;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 21.08.15 05:47
 */
public class UserTransactionInfo implements Serializable {
    private static final Duration DEFAULT_DURATION = Duration.of(1L, ChronoUnit.MINUTES);

    private UUID           id;
    private String         httpSessionId;
    private String         user;
    private OffsetDateTime started;
    private Duration       timeout;


    public UserTransactionInfo(
            final UUID id,
            final String httpSessionId,
            final String user,
            final OffsetDateTime started,
            final Duration timeout
    ) {
        this.id = id;
        this.httpSessionId = httpSessionId;
        this.user = user;
        this.started = started;
        this.timeout = timeout;
    }

    public UserTransactionInfo(
            final String httpSessionId,
            final String user
    ) {
        this(UUID.randomUUID(), httpSessionId, user, OffsetDateTime.now(ZoneId.of("UTC")), DEFAULT_DURATION);
    }


    public UserTransactionInfo() {
        this(null, null);
    }


    public UUID getId() {
        return id;
    }

    public String getHttpSessionId() {
        return httpSessionId;
    }

    public String getUser() {
        return user;
    }

    public OffsetDateTime getStarted() {
        return started;
    }

    public Duration getTimeout() {
        return timeout;
    }

    public boolean isValid() {
        OffsetDateTime end = started.plus(timeout);
        OffsetDateTime now = OffsetDateTime.now();

        return end.isBefore(now) || end.isEqual(now);
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
        UserTransactionInfo rhs = (UserTransactionInfo) obj;
        return new EqualsBuilder()
                .append(this.id, rhs.getId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .toHashCode();
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("httpSessionId", httpSessionId)
                .append("start", started)
                .append("duration", timeout)
                .toString();
    }
}
