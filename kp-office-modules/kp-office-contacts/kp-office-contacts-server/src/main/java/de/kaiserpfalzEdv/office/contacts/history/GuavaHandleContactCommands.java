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

package de.kaiserpfalzEdv.office.contacts.history;

import de.kaiserpfalzEdv.office.contacts.commands.contact.CreateContactCommand;
import de.kaiserpfalzEdv.office.contacts.store.CreateContactCommandRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @version 0.1.0
 * @since 0.1.0
 */
@Service
public class GuavaHandleContactCommands {
    private static final Logger LOG = LoggerFactory.getLogger(GuavaHandleContactCommands.class);

    private CreateContactCommandRepository createContactRepository;


    @Inject
    public GuavaHandleContactCommands(
            CreateContactCommandRepository createContactRepository
    ) {
        this.createContactRepository = createContactRepository;

        LOG.debug("***** Created: {}", this);
        LOG.trace("*   *   create contact repository: {}", this.createContactRepository);
    }

    public void handle(@NotNull final CreateContactCommand contactCommand) {

    }
}
