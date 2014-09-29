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

import de.kaiserpfalzEdv.office.security.OfficePrincipal;
import de.kaiserpfalzEdv.office.security.OfficeTicket;

/**
 * The login command to get the user logged in.
 *
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public class LoginCommand extends LoginBaseCommand {
    private static final long serialVersionUID = 1L;

    private OfficePrincipal user;
    private OfficeTicket applicationTicket;
    private OfficeChallenge challange;


    @Deprecated // Only for Jackson, JAX-B and JPA!
    public LoginCommand() {
    }

    @SuppressWarnings("deprecation")
    public LoginCommand(final OfficePrincipal user, final OfficeTicket applicationTicket, final OfficeChallenge challenge) {
        setUser(user);
        setApplicationTicket(applicationTicket);
        setChallange(challenge);
    }


    public OfficePrincipal getUser() {
        return user;
    }

    @Deprecated // Only for Jackson, JAX-B and JPA!
    public void setUser(final OfficePrincipal user) {
        this.user = user;
    }


    public OfficeTicket getApplicationTicket() {
        return applicationTicket;
    }

    @Deprecated // Only for Jackson, JAX-B and JPA!
    public void setApplicationTicket(final OfficeTicket applicationTicket) {
        this.applicationTicket = applicationTicket;
    }


    public OfficeChallenge getChallange() {
        return challange;
    }

    @Deprecated // Only for Jackson, JAX-B and JPA!
    public void setChallange(final OfficeChallenge challange) {
        this.challange = challange;
    }
}
