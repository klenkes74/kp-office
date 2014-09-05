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

package de.kaiserpfalzEdv.office.security.commands;

import java.io.Serializable;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * An office challenge for password or any other authentication. The server will check the response by using the
 * same method to work with the challenge than the client. If the response matches, then a ticket will be created.
 *
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public class OfficeChallenge extends LoginBaseCommand {
    private static final long serialVersionUID = 1L;

    private UUID challengeRequest;

    private Serializable challenge;
    private Serializable response;


    @Deprecated // Only for Jackson, JAX-B and JPA!
    public OfficeChallenge() {}

    @SuppressWarnings("deprecation")
    public OfficeChallenge(final UUID challengeRequest, final Serializable challenge) {
        setChallenge(challenge);
        setChallengeRequest(challengeRequest);
    }


    public UUID getChallengeRequest() {
        return challengeRequest;
    }

    @Deprecated // Only for Jackson, JAX-B and JPA!
    public void setChallengeRequest(UUID challengeRequest) {
        this.challengeRequest = challengeRequest;
    }


    public Serializable getChallenge() {
        return challenge;
    }

    @Deprecated // Only for Jackson, JAX-B and JPA!
    public void setChallenge(Serializable value) {
        checkArgument(value != null, "Sorry, can't set <null> as challenge!");

        this.challenge = value;
    }


    public Serializable getResponse() {
        return response;
    }

    public void setResponse(Serializable value) {
        checkArgument(value != null, "Sorry, can't set <null> as challenge response!");

        this.response = value;
    }
}
