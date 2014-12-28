package de.kaiserpfalzEdv.office.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import javax.inject.Inject;
import java.time.ZonedDateTime;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @version 0.1.0
 * @since 0.1.0
 */
public class SecurityTicketHousekeeping {
    private static final Logger LOG = LoggerFactory.getLogger(SecurityTicketHousekeeping.class);

    @Inject
    private SecurityTicketRepository ticketRepository;


    @Scheduled(fixedRate = 60000L, initialDelay = 60000L)
    public void deleteOldTickets() {
        Iterable<SecurityTicket> tickets = ticketRepository.findByValidityLessThan(ZonedDateTime.now());

        for (SecurityTicket t : tickets) {
            ticketRepository.delete(t.getId());
        }

        LOG.debug("Removed invalid tickets: {}", tickets);
    }
}
