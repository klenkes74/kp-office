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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.UUID;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import de.kaiserpfalzedv.office.access.api.applications.ApplicationService;
import de.kaiserpfalzedv.office.access.api.applications.InvalidOfficeApplicationException;
import de.kaiserpfalzedv.office.access.api.applications.OfficeApplication;
import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-08-20
 */
@Path("/authz")
public class AuthorizationEndpoint {
    private static final Logger LOG = LoggerFactory.getLogger(AuthorizationEndpoint.class);

    private ApplicationService applicationService;


    @Inject
    public AuthorizationEndpoint(
            @NotNull final ApplicationService applicationService
    ) {
        this.applicationService = applicationService;
    }


    @GET
    public Response authorize(@Context HttpServletRequest request)
            throws OAuthSystemException, URISyntaxException {

        OAuthAuthzRequest oauthRequest = null;

        OAuthIssuerImpl oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());

        try {
            oauthRequest = new OAuthAuthzRequest(request);

            checkApplication(oauthRequest);

            //build response according to response_type
            String responseType = oauthRequest.getParam(OAuth.OAUTH_RESPONSE_TYPE);

            OAuthASResponse.OAuthAuthorizationResponseBuilder builder =
                    OAuthASResponse.authorizationResponse(request, HttpServletResponse.SC_FOUND);

            if (responseType.equals(ResponseType.CODE.toString())) {
                builder.setCode(oauthIssuerImpl.authorizationCode());
            }
            if (responseType.equals(ResponseType.TOKEN.toString())) {
                builder.setAccessToken(oauthIssuerImpl.accessToken());
                builder.setTokenType(OAuth.DEFAULT_TOKEN_TYPE.toString());
                builder.setExpiresIn(3600L);
            }

            String redirectURI = oauthRequest.getParam(OAuth.OAUTH_REDIRECT_URI);

            final OAuthResponse response = builder.location(redirectURI).buildQueryMessage();
            URI url = new URI(response.getLocationUri());

            return Response.status(response.getResponseStatus()).location(url).build();

        } catch (OAuthProblemException e) {

            final Response.ResponseBuilder responseBuilder = Response.status(HttpServletResponse.SC_FOUND);

            String redirectUri = e.getRedirectUri();

            if (OAuthUtils.isEmpty(redirectUri)) {
                throw new WebApplicationException(
                        responseBuilder.entity("OAuth callback url needs to be provided by client!!!").build());
            }
            final OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_FOUND)
                                                          .error(e)
                                                          .location(redirectUri).buildQueryMessage();
            final URI location = new URI(response.getLocationUri());
            return responseBuilder.location(location).build();
        } catch (InvalidOfficeApplicationException e) {
            final Response.ResponseBuilder responseBuilder = Response.status(HttpServletResponse.SC_FOUND);

            String redirectUri = oauthRequest.getRedirectURI();

            if (OAuthUtils.isEmpty(redirectUri)) {
                throw new WebApplicationException(
                        responseBuilder.entity("OAuth callback url needs to be provided by client!!!").build());
            }
            final OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_FOUND)
                                                          .setError(e.getMessage())
                                                          .location(redirectUri).buildQueryMessage();
            final URI location = new URI(response.getLocationUri());
            return responseBuilder.location(location).build();
        }
    }


    private void checkApplication(@NotNull final OAuthAuthzRequest request) throws InvalidOfficeApplicationException {
        try {
            UUID clientId = UUID.fromString(request.getClientId();
            Optional<OfficeApplication> application = applicationService.retrieveApplication(clientId);
            application.orElseThrow(InvalidOfficeApplicationException::new);
        } catch (IllegalArgumentException e) {
            throw new InvalidOfficeApplicationException("Can't read client id: " + e.getMessage());
        }
    }
}
