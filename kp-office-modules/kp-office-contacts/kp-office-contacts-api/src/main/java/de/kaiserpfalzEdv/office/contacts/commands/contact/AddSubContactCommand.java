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

package de.kaiserpfalzEdv.office.contacts.commands.contact;

import de.kaiserpfalzEdv.office.contacts.contact.Contact;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @version 0.1.0
 * @since 0.1.0
 */
public class AddSubContactCommand extends UpdateContactCommand {
    private static final long serialVersionUID = 1L;


    private Contact subContact;


    @SuppressWarnings({"deprecation", "UnusedDeclaration"})
    @Deprecated // Only for Jackson, JAX-B and JPA!
    public AddSubContactCommand() {
    }

    @SuppressWarnings("deprecation")
    public AddSubContactCommand(@NotNull final UUID contactId, @NotNull final Contact subContact) {
        super(contactId);
        setSubContact(subContact);
    }


    public Contact getSubContact() {
        return subContact;
    }

    @Deprecated // Only for Jackson, JAX-B and JPA!
    public void setSubContact(Contact subContact) {
        this.subContact = subContact;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append(subContact)
                .toString();
    }
}
