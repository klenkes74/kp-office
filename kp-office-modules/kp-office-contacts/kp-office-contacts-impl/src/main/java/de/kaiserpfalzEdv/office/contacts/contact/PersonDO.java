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

package de.kaiserpfalzEdv.office.contacts.contact;

import de.kaiserpfalzEdv.office.contacts.address.Address;

import java.util.Set;
import java.util.UUID;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 28.02.15 01:04
 */
public class PersonDO implements ContactPerson {
    @Override
    public ContactType getType() {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzEdv.office.contacts.contact.PersonDO.getType
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public PersonalName getName() {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzEdv.office.contacts.contact.PersonDO.getName
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Set<? extends Address> getAddresses() {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzEdv.office.contacts.contact.PersonDO.getAddresses
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public <A extends Address> void addAddress(A address) {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzEdv.office.contacts.contact.PersonDO.addAddress
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public <A extends Address> void removeAddress(A address) {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzEdv.office.contacts.contact.PersonDO.removeAddress
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Set<? extends Contact> getSubContacts() {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzEdv.office.contacts.contact.PersonDO.getSubContacts
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public <C extends Contact> void addSubContact(C contact) {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzEdv.office.contacts.contact.PersonDO.addSubContact
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public <C extends Contact> void removeSubContact(C contact) {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzEdv.office.contacts.contact.PersonDO.removeSubContact
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getDisplayName() {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzEdv.office.contacts.contact.PersonDO.getDisplayName
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getDisplayNumber() {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzEdv.office.contacts.contact.PersonDO.getDisplayNumber
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isHidden() {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzEdv.office.contacts.contact.PersonDO.isHidden
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public UUID getId() {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzEdv.office.contacts.contact.PersonDO.getId
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public UUID getTenantId() {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzEdv.office.contacts.contact.PersonDO.getTenantId
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
