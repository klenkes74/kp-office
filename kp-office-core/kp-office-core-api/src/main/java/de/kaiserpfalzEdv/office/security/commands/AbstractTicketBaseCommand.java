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

import de.kaiserpfalzEdv.office.commands.OfficeCommand;
import de.kaiserpfalzEdv.office.security.OfficeTicket;

/**
 * @author klenkes
 * @since 2014Q
 */
public class AbstractTicketBaseCommand extends OfficeCommand {
    private static final long serialVersionUID = 1L;


    private OfficeTicket ticket;


    @Deprecated // Only for Jackson, JAX-B and JPA!
    public AbstractTicketBaseCommand() {
    }

    @SuppressWarnings("deprecation")
    public AbstractTicketBaseCommand(final OfficeTicket ticket) {
        setTicket(ticket);
    }


    @Override
    public String getTarget() {
        return "Security Ticket";
    }


    public OfficeTicket getTicket() {
        return ticket;
    }

    @Deprecated // Only for Jackson, JAX-B and JPA!
    public void setTicket(OfficeTicket ticket) {
        this.ticket = ticket;
    }
}
