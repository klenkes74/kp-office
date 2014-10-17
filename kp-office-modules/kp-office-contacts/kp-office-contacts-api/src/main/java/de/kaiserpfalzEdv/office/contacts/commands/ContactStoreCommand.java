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

package de.kaiserpfalzEdv.office.contacts.commands;

import de.kaiserpfalzEdv.office.commands.OfficeCommand;
import de.kaiserpfalzEdv.office.contacts.contact.Contact;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public abstract class ContactStoreCommand extends OfficeCommand {
    public static final String TARGET_ENTITY = "Contact";
    private static final long serialVersionUID = 1L;

    private UUID tenantId;
    private UUID contactId;

    @SuppressWarnings("deprecation")
    @Deprecated // Only for Jackson, JAX-B and JPA!
    public ContactStoreCommand() {
    }

    public ContactStoreCommand(@NotNull final UUID tenantId, @NotNull final UUID contactId) {
        setTenantId(tenantId);
        setContactId(contactId);
    }


    public String getTarget() {
        return TARGET_ENTITY;
    }

    public UUID getTenantId() {
        return tenantId;
    }

    protected void setTenantId(final UUID tenantId) {
        this.tenantId = tenantId;
    }


    public UUID getContactId() {
        return contactId;
    }

    protected void setContactId(final UUID contactId) {
        this.contactId = contactId;
    }


    public abstract Contact updateContact(Contact contact);

    public boolean validContact(boolean current) {
        return current;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("contactId", contactId)
                .toString();
    }
}
