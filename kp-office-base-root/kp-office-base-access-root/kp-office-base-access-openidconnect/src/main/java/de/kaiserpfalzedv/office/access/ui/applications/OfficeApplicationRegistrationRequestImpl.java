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

package de.kaiserpfalzedv.office.access.ui.applications;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.office.access.api.applications.OfficeApplicationRegistrationRequest;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-08-24
 */
public class OfficeApplicationRegistrationRequestImpl implements OfficeApplicationRegistrationRequest {
    private static final long serialVersionUID = -1931113302199875264L;

    private UUID requestId;

    private String type;
    private String clientName;
    private String clientUrl;
    private String clientDescription;
    private String redirectUrl;
    private String discover;


    public OfficeApplicationRegistrationRequestImpl(
            @NotNull final UUID requestId,
            @NotNull final String type,
            @NotNull final String clientName,
            @NotNull final String clientUrl,
            @NotNull final String clientDescription,
            @NotNull final String redirectUrl,
            @NotNull final String discover
    ) {
        this.requestId = requestId;
        this.type = type;
        this.clientName = clientName;
        this.clientUrl = clientUrl;
        this.clientDescription = clientDescription;
        this.redirectUrl = redirectUrl;
        this.discover = discover;
    }


    public UUID getRequestId() {
        return requestId;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getClientName() {
        return clientName;
    }

    @Override
    public String getClientUrl() {
        return clientUrl;
    }

    @Override
    public String getClientDescription() {
        return clientDescription;
    }

    @Override
    public String getRedirectUrl() {
        return redirectUrl;
    }

    @Override
    public String getDiscover() {
        return discover;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(requestId)
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
        OfficeApplicationRegistrationRequestImpl rhs = (OfficeApplicationRegistrationRequestImpl) obj;
        return new EqualsBuilder()
                .append(this.requestId, rhs.requestId)
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append(System.identityHashCode(this))
                .append("requestId", requestId)
                .append("type", type)
                .append("clientName", clientName)
                .append("clientUrl", clientUrl)
                .append("clientDescription", clientDescription)
                .append("redirectUrl", redirectUrl)
                .append("discover", discover)
                .toString();
    }
}
