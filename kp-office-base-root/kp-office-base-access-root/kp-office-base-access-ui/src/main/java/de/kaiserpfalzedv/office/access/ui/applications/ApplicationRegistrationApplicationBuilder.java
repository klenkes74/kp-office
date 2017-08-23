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

import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.office.access.api.applications.OfficeApplicationRegistrationRequest;
import org.apache.commons.lang3.builder.Builder;
import org.apache.oltu.oauth2.ext.dynamicreg.server.request.OAuthServerRegistrationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-08-23
 */
public class ApplicationRegistrationApplicationBuilder implements Builder<OfficeApplicationRegistrationRequest> {
    private static final Logger LOG = LoggerFactory.getLogger(ApplicationRegistrationApplicationBuilder.class);

    private String type;
    private String clientName;
    private String clientUrl;
    private String clientDescription;
    private String redirectUrl;


    @Override
    public OfficeApplicationRegistrationRequest build() {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.office.access.ui.applications.ApplicationRegistrationApplicationBuilder.build
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }


    public ApplicationRegistrationApplicationBuilder withOAuthRegistrationRequest(@NotNull final OAuthServerRegistrationRequest request) {
        withType(request.getType());
        withClientName(request.getClientName());
        withClientDescription(request.getClientDescription());
        withClientUrl(request.getClientUrl());
        withRedirectUrl(request.getRedirectURI());

        return this;
    }

    public ApplicationRegistrationApplicationBuilder withType(@NotNull final String type) {
        this.type = type;
        return this;
    }

    public ApplicationRegistrationApplicationBuilder withClientName(@NotNull final String clientName) {
        this.clientName = clientName;
        return this;
    }

    public ApplicationRegistrationApplicationBuilder withClientDescription(@NotNull final String clientDescription) {
        this.clientDescription = clientDescription;
        return this;
    }

    public ApplicationRegistrationApplicationBuilder withClientUrl(@NotNull final String clientUrl) {
        this.clientUrl = clientUrl;
        return this;
    }

    public ApplicationRegistrationApplicationBuilder withRedirectUrl(@NotNull final String redirectUrl) {
        this.redirectUrl = redirectUrl;
        return this;
    }
}
