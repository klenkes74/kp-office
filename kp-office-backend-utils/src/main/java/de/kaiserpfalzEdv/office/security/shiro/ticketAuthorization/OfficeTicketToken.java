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

package de.kaiserpfalzEdv.office.security.shiro.ticketAuthorization;

import de.kaiserpfalzEdv.office.security.OfficePrincipal;
import de.kaiserpfalzEdv.office.security.OfficeTicket;
import de.kaiserpfalzEdv.office.security.shiro.OfficeToken;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public class OfficeTicketToken extends OfficeToken {
    private static final long serialVersionUID = -2041014404680140080L;

    private OfficePrincipal principal;

    private OfficeTicket ticket;


    public OfficeTicketToken(final OfficeTicket ticket) {
        setTicket(ticket);
        setValid(true);
    }


    @Override
    public OfficePrincipal getPrincipal() {
        return principal;
    }

    public void setPrincipal(final OfficePrincipal principal) {
        this.principal = principal;
    }


    private void setTicket(final OfficeTicket ticket) {
        this.ticket = ticket;
    }


    @Override
    public OfficeTicket getCredentials() {
        return ticket;
    }


    @Override
    public String toString() {
        ToStringBuilder result = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .appendSuper(super.toString())
                .append("principal", getPrincipal());

        return result.toString();
    }
}
