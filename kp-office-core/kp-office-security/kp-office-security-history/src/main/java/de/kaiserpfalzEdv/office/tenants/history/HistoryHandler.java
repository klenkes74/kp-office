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

package de.kaiserpfalzEdv.office.tenants.history;

import de.kaiserpfalzEdv.office.tenants.NoSuchTenantException;
import de.kaiserpfalzEdv.office.tenants.OnlyInvalidTenantFoundException;
import de.kaiserpfalzEdv.office.tenants.Tenant;
import de.kaiserpfalzEdv.office.tenants.TenantCommandException;
import de.kaiserpfalzEdv.office.tenants.TenantDTO;
import de.kaiserpfalzEdv.office.tenants.commands.CreateTenantCommand;
import de.kaiserpfalzEdv.office.tenants.commands.DeleteTenantCommand;
import de.kaiserpfalzEdv.office.tenants.commands.RenameTenantCommand;
import de.kaiserpfalzEdv.office.tenants.commands.RenumberTenantCommand;
import de.kaiserpfalzEdv.office.tenants.commands.SyncTenantCommand;
import de.kaiserpfalzEdv.office.tenants.commands.TenantStoreCommand;
import de.kaiserpfalzEdv.office.tenants.notifications.CreateTenantNotification;
import de.kaiserpfalzEdv.office.tenants.notifications.DeleteTenantNotification;
import de.kaiserpfalzEdv.office.tenants.notifications.SyncTenantNotification;
import de.kaiserpfalzEdv.office.tenants.notifications.UpdateTenantNotification;
import de.kaiserpfalzEdv.office.tenants.store.CreateTenantCommandRepository;
import de.kaiserpfalzEdv.office.tenants.store.DeleteTenantCommandRepository;
import de.kaiserpfalzEdv.office.tenants.store.RenameTenantCommandRepository;
import de.kaiserpfalzEdv.office.tenants.store.RenumberTenantCommandRepository;
import de.kaiserpfalzEdv.office.tenants.store.StoredTenantReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;

import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;
import java.time.ZonedDateTime;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
@Named
public class HistoryHandler {
    private static final Logger LOG = LoggerFactory.getLogger(HistoryHandler.class);

    private CreateTenantCommandRepository createRepository;
    private RenameTenantCommandRepository renameRepository;
    private RenumberTenantCommandRepository renumberRepository;
    private DeleteTenantCommandRepository deleteRepository;

    private StoredTenantReader storeReader;
    private AmqpTemplate notifier;

    @Inject
    public HistoryHandler(
            final CreateTenantCommandRepository createRepository,
            final RenameTenantCommandRepository renameRepository,
            final RenumberTenantCommandRepository renumberRepository,
            final DeleteTenantCommandRepository deleteRepository,
            @Named("tenantNotification") final AmqpTemplate notifier,
            final StoredTenantReader storeReader
    ) {
        this.notifier = notifier;
        this.storeReader = storeReader;

        this.createRepository = createRepository;
        this.renameRepository = renameRepository;
        this.renumberRepository = renumberRepository;
        this.deleteRepository = deleteRepository;

        LOG.trace("***** Created: {}", this);
        LOG.trace("*   *   notification queue: {}", this.notifier);
        LOG.trace("*   *   store reader: {}", this.storeReader);
        LOG.trace("*   *   tenant create repository: {}", this.createRepository);
        LOG.trace("*   *   tenant rename repository: {}", this.renameRepository);
        LOG.trace("*   *   tenant renumber repository: {}", this.renumberRepository);
        LOG.trace("*   *   tenant delete repository: {}", this.deleteRepository);
    }

    @PreDestroy
    public void close() {
        LOG.trace("***** Destroyed: {}", this.toString());
    }


    public void handle(CreateTenantCommand command) throws TenantCommandException {
        setHandledTimestamp(command);

        command = createRepository.save(command);


        Tenant tenant = new TenantDTO(command.getTenantId(), command.getDisplayNumber(), command.getDisplayName());
        notifier.convertAndSend(new CreateTenantNotification(command, tenant));
        LOG.info("Created tenant: {}", tenant);
    }

    public void handle(RenameTenantCommand command) throws TenantCommandException {
        setHandledTimestamp(command);

        command = renameRepository.save(command);

        Tenant tenant = null;
        try {
            tenant = storeReader.retrieveCurrentInformation(command);
        } catch (NoSuchTenantException e) {
            throw new TenantCommandException(command, e);
        }

        notifier.convertAndSend(new UpdateTenantNotification(command, tenant));
        LOG.info("Renamed tenant: {}", tenant);
    }

    public void handle(RenumberTenantCommand command) throws TenantCommandException {
        setHandledTimestamp(command);

        command = renumberRepository.save(command);

        Tenant tenant = null;
        try {
            tenant = storeReader.retrieveCurrentInformation(command);
        } catch (NoSuchTenantException e) {
            throw new TenantCommandException(command, e);
        }

        notifier.convertAndSend(new UpdateTenantNotification(command, tenant));
        LOG.info("Renumbered tenant: {}", tenant);
    }

    public void handle(DeleteTenantCommand command) throws TenantCommandException {
        setHandledTimestamp(command);


        command = deleteRepository.save(command);

        Tenant tenant = null;
        try {
            tenant = storeReader.retrieveCurrentInformation(command);
        } catch (OnlyInvalidTenantFoundException e) {
            tenant = e.getInvalidTenant();
        } catch (NoSuchTenantException e) {
            throw new TenantCommandException(command, e);
        }

        notifier.convertAndSend(new DeleteTenantNotification(command, tenant));
        LOG.info("Deleted tenant: {}", tenant);
    }

    public void handle(SyncTenantCommand command) throws TenantCommandException {
        setHandledTimestamp(command);

        Tenant tenant = null;
        try {
            tenant = storeReader.retrieveCurrentInformation(command);
        } catch (NoSuchTenantException e) {
            throw new TenantCommandException(command, e);
        }

        notifier.convertAndSend(new SyncTenantNotification(command, tenant));
        LOG.info("Synced tenant: {}", tenant);
    }


    private void setHandledTimestamp(TenantStoreCommand command) {
        command.setHandledTimestamp(ZonedDateTime.now());
    }
}
