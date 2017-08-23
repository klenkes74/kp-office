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

package de.kaiserpfalzedv.office.access.ui;

import java.io.Serializable;
import java.util.Optional;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import de.kaiserpfalzedv.office.access.api.applications.ApplicationService;
import de.kaiserpfalzedv.office.access.api.applications.OfficeApplication;
import de.kaiserpfalzedv.office.access.api.applications.OfficeApplicationRegistrationRequest;
import de.kaiserpfalzedv.office.access.ui.applications.ApplicationRegistrationApplicationBuilder;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.ext.dynamicreg.server.request.JSONHttpServletRequestWrapper;
import org.apache.oltu.oauth2.ext.dynamicreg.server.request.OAuthServerRegistrationRequest;
import org.apache.oltu.oauth2.ext.dynamicreg.server.response.OAuthServerRegistrationResponse;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-08-23
 */
@Path("/register")
public class RegistrationEndpoint implements Serializable {

    /**
     * The service used to register the application to this authorization provider.
     */
    private ApplicationService service;


    @Inject
    public RegistrationEndpoint(
            @NotNull final ApplicationService service
    ) {
        this.service = service;
    }


    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response register(@Context HttpServletRequest request) throws OAuthSystemException {
        try {
            OAuthServerRegistrationRequest oauthRequest = new OAuthServerRegistrationRequest(new JSONHttpServletRequestWrapper(request));

            oauthRequest.getType();
            oauthRequest.discover();
            oauthRequest.getClientName();
            oauthRequest.getClientUrl();
            oauthRequest.getClientDescription();
            oauthRequest.getRedirectURI();

            OfficeApplicationRegistrationRequest applicationRegistrationRequest =
                    new ApplicationRegistrationApplicationBuilder()
                            .withOAuthRegistrationRequest(oauthRequest)
                            .build();

            Optional<OfficeApplication> registeredApplication = service.registerApplication(applicationRegistrationRequest);

            OAuthResponse response;
            if (registeredApplication.isPresent()) {
                OfficeApplication application = registeredApplication.get();

                response = OAuthServerRegistrationResponse
                        .status(HttpServletResponse.SC_OK)
                        .setClientId(application.getClientId())
                        .setClientSecret(application.getClientSecret())
                        .setIssuedAt(Long.toString(application.getRegistrationTimestamp().toEpochSecond(), 10))
                        .setExpiresIn(Long.toString(application.getRegistrationTimeframe()
                                                               .getValidTill()
                                                               .toEpochSecond(), 10))
                        .buildJSONMessage();
            } else {
                response = OAuthServerRegistrationResponse
                        .errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                        .setError("Can't register application!")
                        .buildJSONMessage();
            }

            return Response.status(response.getResponseStatus()).entity(response.getBody()).build();
        } catch (OAuthProblemException e) {
            OAuthResponse response = OAuthServerRegistrationResponse
                    .errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                    .error(e)
                    .buildJSONMessage();
            return Response.status(response.getResponseStatus()).entity(response.getBody()).build();
        }

    }
}