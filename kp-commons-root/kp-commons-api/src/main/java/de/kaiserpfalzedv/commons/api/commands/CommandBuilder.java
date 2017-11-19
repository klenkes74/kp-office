/*
 * Copyright 2017 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzedv.commons.api.commands;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.commons.api.BuilderException;
import de.kaiserpfalzedv.commons.api.data.paging.Pageable;
import de.kaiserpfalzedv.commons.api.data.query.Predicate;
import org.apache.commons.lang3.builder.Builder;

import static de.kaiserpfalzedv.commons.api.commands.CrudCommands.CREATE;
import static de.kaiserpfalzedv.commons.api.commands.CrudCommands.DELETE;
import static de.kaiserpfalzedv.commons.api.commands.CrudCommands.RETRIEVE;
import static de.kaiserpfalzedv.commons.api.commands.CrudCommands.UPDATE;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-25
 */
public class CommandBuilder<C extends BaseCommand, T extends Serializable> implements Builder<C> {
    private CrudCommandCreator<T> creator;
    private CrudCommandDefaultValueCalculator<T> calculator;
    private CrudCommandValidator<T> validator;
    private Class<?> clasz;
    private CrudCommands command;

    private UUID source;
    private UUID id;

    private UUID dataId;
    private T data;
    private Predicate<T> predicate;
    private Pageable page;


    public CommandBuilder(
            @NotNull final Class<?> clasz,
            @NotNull final CrudCommandCreator<T> creator
    ) {
        this.clasz = clasz;
        this.creator = creator;
    }

    public CommandBuilder<C, T> withDefaultCalculator(@NotNull final CrudCommandDefaultValueCalculator<T> calculator) {
        this.calculator = calculator;

        return this;
    }


    public CommandBuilder<C, T> withValidator(@NotNull final CrudCommandValidator<T> validator) {
        this.validator = validator;

        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public C build() {
        setDefaults();
        validate();

        switch (command) {
            case CREATE:
                return (C) creator.create(source, id, data);
            case RETRIEVE:
                return (C) creator.retrieve(source, id, predicate, page);
            case UPDATE:
                return (C) creator.update(source, id, data);
            case DELETE:
                return (C) creator.delete(source, id, dataId);
            default:
                throw new IllegalStateException("The builder failed internally. Please report bug!");
        }
    }

    private void setDefaults() {
        if (id == null) {
            id = UUID.randomUUID();
        }

        if (calculator != null) {
            dataId = calculator.setDefaultId(command, dataId, data, predicate, page);
            data = calculator.setDefaultData(command, dataId, data, predicate, page);
            predicate = calculator.setDefaultPredicate(command, dataId, data, predicate, page);
            page = calculator.setDefaultPage(command, dataId, data, predicate, page);
        }
    }

    private void validate() {
        ArrayList<String> failures = new ArrayList<>();

        if (validator != null) {
            failures.addAll(validator.validateData(command, dataId, data, predicate, page));
            failures.addAll(validator.validateId(command, dataId, data, predicate, page));
            failures.addAll(validator.validatePredicate(command, dataId, data, predicate, page));
            failures.addAll(validator.validatePage(command, dataId, data, predicate, page));
        }

        if (data == null && (CREATE.equals(command) || UPDATE.equals(command))) {
            failures.add("No data data given for the " + command + " command");
        }

        if (dataId == null && DELETE.equals(command)) {
            failures.add("No data id given for the " + command + " command");
        }

        if (predicate == null && RETRIEVE.equals(command)) {
            failures.add("No predicate given for the " + command + " command");
        }

        if (command == null) {
            failures.add("No command specified");
        }

        if (source == null) {
            failures.add("No source UUID given. Please generate an unique identifier for your service calling object!");
        }

        if (!failures.isEmpty()) {
            throw new BuilderException(clasz, failures);
        }
    }


    public CommandBuilder<C, T> withSource(UUID source) {
        this.source = source;
        return this;
    }

    public CommandBuilder<C, T> withCommandId(@NotNull UUID id) {
        this.id = id;
        return this;
    }

    public CommandBuilder<C, T> withId(@NotNull UUID TId) {
        this.dataId = TId;
        return this;
    }

    public CommandBuilder withData(@NotNull final T data) {
        this.data = data;
        return this;
    }

    public CommandBuilder<C, T> withPredicate(@NotNull final Predicate<T> predicate) {
        this.predicate = predicate;
        return this;
    }

    public CommandBuilder<C, T> withPage(@NotNull final Pageable page) {
        this.page = page;
        return this;
    }

    public CommandBuilder<C, T> create() {
        command = CREATE;
        return this;
    }

    public CommandBuilder<C, T> retrieve() {
        command = RETRIEVE;
        return this;
    }

    public CommandBuilder<C, T> update() {
        command = UPDATE;
        return this;
    }

    public CommandBuilder<C, T> delete() {
        command = DELETE;
        return this;
    }
}
