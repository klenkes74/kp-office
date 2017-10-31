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

package de.kaiserpfalzedv.office.access.client.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.jwk.source.RemoteJWKSet;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SimpleSecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import de.kaiserpfalzedv.office.access.api.users.OfficePrincipal;
import de.kaiserpfalzedv.office.access.client.JWTService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-08-24
 */
public class JWTServiceImpl implements JWTService {
    private static final Logger LOG = LoggerFactory.getLogger(JWTServiceImpl.class);

    @Override
    public Optional<OfficePrincipal> readToken(@NotNull final String jwt, @NotNull final String remoteJWK, @NotNull JWSAlgorithm algorithm) {
        LOG.trace("Converting bearer tocken to principal: token={}..., provider={}, algorithm={}",
                  jwt.substring(0, 20), remoteJWK, algorithm
        );

        try {
            JWTClaimsSet claims = transform(jwt, remoteJWK, algorithm);

            LOG.debug("JWT: {}", claims.toJSONObject());

            return Optional.empty();
        } catch (ParseException | JOSEException | MalformedURLException | BadJOSEException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);

            return Optional.empty();
        }
    }

    private JWTClaimsSet transform(@NotNull final String jwt, @NotNull final String remoteJWK, JWSAlgorithm algorithm) throws ParseException, JOSEException, BadJOSEException, MalformedURLException {
        // Set up a JWT processor to parse the tokens and then check their signature
        // and validity time window (bounded by the "iat", "nbf" and "exp" claims)
        ConfigurableJWTProcessor jwtProcessor = new DefaultJWTProcessor();

        // The public RSA keys to validate the signatures will be sourced from the
        // OAuth 2.0 server's JWK set, published at a well-known URL. The RemoteJWKSet
        // object caches the retrieved keys to speed up subsequent look-ups and can
        // also gracefully handle key-rollover
        JWKSource<SimpleSecurityContext> keySource = (JWKSource<SimpleSecurityContext>) new RemoteJWKSet(new URL(remoteJWK));

        // Configure the JWT processor with a key selector to feed matching public
        // RSA keys sourced from the JWK set URL
        JWSKeySelector<SimpleSecurityContext> keySelector = new JWSVerificationKeySelector(algorithm, keySource);
        jwtProcessor.setJWSKeySelector(keySelector);

        // Process the token
        SimpleSecurityContext ctx = null; // optional context parameter, not required here
        return jwtProcessor.process(jwt, null);
    }
}
