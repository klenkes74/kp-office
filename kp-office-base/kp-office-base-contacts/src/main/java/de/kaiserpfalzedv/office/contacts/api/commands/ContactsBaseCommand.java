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

package de.kaiserpfalzedv.office.contacts.api.commands;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.kaiserpfalzedv.office.common.api.commands.BaseCommand;
import de.kaiserpfalzedv.office.common.api.commands.CrudCommands;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-08-11
 */
public abstract class ContactsBaseCommand extends BaseCommand {

    @JsonIgnore
    private CrudCommands crudType;


    /**
     * @param crudType The type of the command to be implemented.
     * @param source   The object on which the Event initially occurred.
     * @param command  The unique ID of this command.
     *
     * @throws IllegalArgumentException if source is null.
     */
    public ContactsBaseCommand(
            @NotNull final CrudCommands crudType,
            @NotNull final UUID source,
            @NotNull final UUID command
    ) {
        super(source, command);

        this.crudType = crudType;
    }


    public abstract void execute(@NotNull final ContactsCommandExecutor executor) throws ContactsCommandExecutionException;

    @JsonIgnore
    public CrudCommands getCrudType() {
        return crudType;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .appendSuper(super.toString())
                .append("crudType", crudType)
                .toString();
    }
}
