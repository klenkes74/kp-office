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

import de.kaiserpfalzEdv.commons.util.BuilderException;
import org.apache.commons.lang3.builder.Builder;

import java.util.ArrayList;
import java.util.UUID;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 21.08.15 11:58
 */
public class ActionBuilder implements Builder<Action> {
    private Object              source;
    private UserTransactionInfo transaction;
    private UUID                eventId;

    @Override
    public Action build() {
        generateDefaults();
        validate();

        return new Action(eventId, transaction, source);
    }


    private void generateDefaults() {
        if (eventId == null) eventId = UUID.randomUUID();
        if (transaction == null) transaction = new NullTransaction();
    }


    public void validate() {
        ArrayList<String> failures = new ArrayList<>(3);

        if (source == null) failures.add("Every action needs to provide its source!");
        if (transaction == null) failures.add("Every action needs to be included in a (null)transaction!");
        if (eventId == null) failures.add("Every action needs to have an unique UUID!");

        if (failures.size() != 0)
            throw new BuilderException(failures);
    }


    public ActionBuilder withSource(Object source) {
        this.source = source;
        return this;
    }

    public ActionBuilder withTransaction(UserTransactionInfo transaction) {
        this.transaction = transaction;
        return this;
    }

    public ActionBuilder withEventId(UUID eventId) {
        this.eventId = eventId;
        return this;
    }
}
