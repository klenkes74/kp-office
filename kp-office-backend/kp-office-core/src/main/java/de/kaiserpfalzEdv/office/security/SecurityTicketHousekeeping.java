package de.kaiserpfalzEdv.office.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.OffsetDateTime;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @version 0.1.0
 * @since 0.1.0
 */
@Named
public class SecurityTicketHousekeeping {
    private static final Logger LOG = LoggerFactory.getLogger(SecurityTicketHousekeeping.class);

    @Inject
    private SecurityTicketRepository ticketRepository;


    @Scheduled(fixedRate = 60000L, initialDelay = 60000L)
    public void deleteOldTickets() {
        LOG.info("Removing invalid tickets.");

        Iterable<SecurityTicket> tickets = retrieveInvalideTickets();

        tickets.forEach(t -> removeInvalidTicket(t));
    }

    private Iterable<SecurityTicket> retrieveInvalideTickets() {
        OffsetDateTime now = OffsetDateTime.now();
        LOG.debug("Checking tickets before: {}", now);

        return ticketRepository.findByValidityLessThan(now);
    }

    private void removeInvalidTicket(SecurityTicket ticket) {
        ticketRepository.delete(ticket.getId());
        LOG.trace("Removed invalid ticket: {}", ticket);
    }
}
