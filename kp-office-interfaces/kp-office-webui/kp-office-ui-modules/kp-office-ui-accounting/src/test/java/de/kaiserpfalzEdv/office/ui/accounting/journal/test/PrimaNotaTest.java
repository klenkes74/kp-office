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
import de.kaiserpfalzEdv.office.accounting.postingRecord.PostingRecord;
import de.kaiserpfalzEdv.office.accounting.primaNota.Primanota;
import de.kaiserpfalzEdv.office.accounting.primaNota.impl.PrimanotaDataLoader;
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
public class PrimanotaTest extends SpringTestNGBase {
    private static final Logger LOG = LoggerFactory.getLogger(PrimanotaTest.class);

    @Inject
    private PrimanotaDataLoader service;

    public PrimanotaTest() {
        super(PrimanotaTest.class, LOG);
    }


    public void testFakeJournal() {
        Primanota primanota = service.loadJournal(UUID.fromString("400b4f5d-216e-4457-9dce-79859d8396af"));
        LOG.info("Journal found: {}", primanota);

        for (PostingRecord entry : primanota.getEntries()) {
            LOG.debug("Entry: {}", entry);
        }
    }
}
