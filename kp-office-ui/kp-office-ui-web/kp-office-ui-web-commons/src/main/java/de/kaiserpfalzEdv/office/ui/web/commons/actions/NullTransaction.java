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

package de.kaiserpfalzEdv.office.ui.web.commons.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.UUID;

import static java.time.temporal.ChronoUnit.FOREVER;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 21.08.15 06:22
 */
public class NullTransaction extends UserTransactionInfo {
    private static final Logger LOG = LoggerFactory.getLogger(NullTransaction.class);

    public NullTransaction() {
        super(UUID.randomUUID(), null, null, OffsetDateTime.now(ZoneId.of("UTC")), Duration.of(1L, FOREVER));
    }
}
