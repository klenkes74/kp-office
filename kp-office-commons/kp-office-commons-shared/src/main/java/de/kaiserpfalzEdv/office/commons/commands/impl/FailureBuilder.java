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

package de.kaiserpfalzEdv.office.commons.commands.impl;

import de.kaiserpfalzEdv.office.commons.i18n.MessageContainable;
import de.kaiserpfalzEdv.office.commons.notifications.Failure;
import org.apache.commons.lang3.builder.Builder;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 01.03.15 20:43
 */
public class FailureBuilder implements Builder<Failure> {
    private MessageContainable messageHolder;
    private OffsetDateTime     timestamp;

    @Override
    public Failure build() {
        if (timestamp == null) timestamp = OffsetDateTime.now();

        return new DefaultFailureImpl(messageHolder.getI18n(), messageHolder.toString(), timestamp);
    }


    public FailureBuilder withException(@NotNull final MessageContainable exception) {
        this.messageHolder = exception;

        return this;
    }

    public FailureBuilder withTimestamp(@NotNull final OffsetDateTime timestamp) {
        this.timestamp = timestamp;

        return this;
    }
}
