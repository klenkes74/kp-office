/*
 * Copyright 2015 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzEdv.office.ui.accounting.journal.test;

import de.kaiserpfalzEdv.commons.test.SpringTestNGBase;
import de.kaiserpfalzEdv.office.accounting.journal.Journal;
import de.kaiserpfalzEdv.office.accounting.journal.JournalEntry;
import de.kaiserpfalzEdv.office.ui.accounting.journal.JournalDataLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.util.UUID;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 19.02.15 08:25
 */
@Test
@ContextConfiguration("/beans-test.xml")
public class JournalTest extends SpringTestNGBase {
    private static final Logger LOG = LoggerFactory.getLogger(JournalTest.class);

    @Inject
    private JournalDataLoader service;

    public JournalTest() {
        super(JournalTest.class, LOG);
    }


    public void testFakeJournal() {
        Journal journal = service.loadJournal(UUID.fromString("400b4f5d-216e-4457-9dce-79859d8396af"));
        LOG.info("Journal found: {}", journal);

        for (JournalEntry entry : journal.getEntries()) {
            LOG.debug("Entry: {}", entry);
        }
    }
}
