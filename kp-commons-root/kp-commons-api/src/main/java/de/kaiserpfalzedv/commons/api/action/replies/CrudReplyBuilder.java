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

package de.kaiserpfalzedv.commons.api.action.replies;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.commons.api.BuilderException;
import de.kaiserpfalzedv.commons.api.action.commands.CrudCommand;
import de.kaiserpfalzedv.commons.api.data.paging.PagedListBuilder;
import de.kaiserpfalzedv.commons.api.data.paging.PagedListable;
import org.apache.commons.lang3.builder.Builder;

import static de.kaiserpfalzedv.commons.api.action.CrudCommandType.CREATE;
import static de.kaiserpfalzedv.commons.api.action.CrudCommandType.DELETE;
import static de.kaiserpfalzedv.commons.api.action.CrudCommandType.RETRIEVE;
import static de.kaiserpfalzedv.commons.api.action.CrudCommandType.UPDATE;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-25
 */
public class CrudReplyBuilder<R extends CrudReply<T>, C extends CrudCommand<T>, T extends Serializable> implements Builder<R> {
    private CrudReplyBuilderCreator<T> creator;
    private CrudReplyBuilderValueCalculator<T> calculator;
    private CrudReplyBuilderValidator<T> validator;
    private Class<?> clasz;

    private UUID source;
    private UUID replyId;
    private C command;

    private UUID dataId;
    private T data;
    private PagedListable<T> datalist;


    public CrudReplyBuilder(
            @NotNull final Class<?> clasz,
            @NotNull final CrudReplyBuilderCreator<T> creator
    ) {
        this.clasz = clasz;
        this.creator = creator;
    }

    public CrudReplyBuilder<R, C, T> withDefaultCalculator(@NotNull final CrudReplyBuilderValueCalculator<T> calculator) {
        this.calculator = calculator;

        return this;
    }


    public CrudReplyBuilder<R, C, T> withValidator(@NotNull final CrudReplyBuilderValidator<T> validator) {
        this.validator = validator;

        return this;
    }


    @SuppressWarnings("unchecked")
    @Override
    public R build() {
        setDefaults();
        validate();

        UUID commandId = command.getCommand();
        switch (command.getType()) {
            case CREATE:
                return (R) creator.create(source, commandId, replyId, data);
            case RETRIEVE:
                return (R) creator.retrieve(source, commandId, replyId, datalist);
            case UPDATE:
                return (R) creator.update(source, commandId, replyId, data);
            case DELETE:
                return (R) creator.delete(source, commandId, replyId, dataId);
            default:
                throw new IllegalStateException("The builder failed internally. Please report bug!");
        }
    }


    private void setDefaults() {
        if (replyId == null) {
            replyId = UUID.randomUUID();
        }

        if (calculator != null) {
            dataId = calculator.setDefaultId(command, replyId, dataId, data, datalist);
            data = calculator.setDefaultData(command, replyId, dataId, data, datalist);
            datalist = calculator.setDefaultDataList(command, replyId, dataId, data, datalist);
        }
    }

    private void validate() {
        ArrayList<String> failures = new ArrayList<>(2);

        if (validator != null) {
            failures.addAll(validator.validateId(command, replyId, dataId, data, datalist));
            failures.addAll(validator.validateData(command, replyId, dataId, data, datalist));
            failures.addAll(validator.validateDataList(command, replyId, dataId, data, datalist));
        }

        if (data == null && (CREATE.equals(command) || UPDATE.equals(command))) {
            failures.add("No data data given for the " + command + " command");
        }

        if (dataId == null && DELETE.equals(command)) {
            failures.add("No data id given for the " + command + " command");
        }

        if (datalist == null && RETRIEVE.equals(command)) {
            failures.add("No result data given for the " + command + " command");
        }

        if (command == null) {
            failures.add("No command specified");
        }

        if (source == null) {
            failures.add("No source UUID given. Please generate an unique identifier for your service calling object!");
        }

        if (!failures.isEmpty()) {
            throw new BuilderException(clasz, failures.toArray(new String[1]));
        }
    }

    public CrudReplyBuilder<R, C, T> withCommand(@NotNull final C command) {
        this.command = command;
        return this;
    }

    public CrudReplyBuilder<R, C, T> withSource(@NotNull final UUID source) {
        this.source = source;
        return this;
    }

    public CrudReplyBuilder<R, C, T> withReplyId(@NotNull final UUID replyId) {
        this.replyId = replyId;
        return this;
    }

    public CrudReplyBuilder<R, C, T> withData(@NotNull final T tenant) {
        this.data = tenant;
        return this;
    }

    public CrudReplyBuilder<R, C, T> withDataPage(@NotNull final PagedListable<T> tenants) {
        this.datalist = new PagedListBuilder<T>()
                .withData(tenants.getEntries())
                .withPageable(tenants.getPage())
                .build();
        return this;
    }
}
