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

package de.kaiserpfalzedv.office.contacts.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import de.kaiserpfalzedv.office.contacts.api.Gender;
import de.kaiserpfalzedv.office.contacts.api.PersonContact;
import de.kaiserpfalzedv.office.contacts.api.names.HeraldicTitles;
import de.kaiserpfalzedv.office.contacts.api.names.HonorificTitles;
import de.kaiserpfalzedv.office.contacts.api.names.Name;
import de.kaiserpfalzedv.office.contacts.api.names.NamePart;
import de.kaiserpfalzedv.office.contacts.api.names.PersonalName;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-08-17
 */
@Entity
@Table(name = "CONTACTS_PERSONS")
@DiscriminatorValue("PERSON")
public class JPAPersonContact extends JPAContact implements PersonContact {
    private static final long serialVersionUID = -3611908901620908293L;


    @Override
    public String getDisplayName() {
        return getName().getDisplayName();
    }

    @Override
    public String getFullName() {
        return getName().getFullName();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Name> T getName() {
        return (T) getPersonName();
    }

    @Override
    public PersonalName getPersonName() {
        // FIXME 2017-08-17 klenkes Replace as soon as modeling for the name exist!
        return new PersonalName() {
            @Override
            public String getNamePrefix() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getNameSuffix() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public NamePart getGivenName() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public List<NamePart> getAdditionalGivenNames() {
                return new ArrayList<>();
            }

            @Override
            public NamePart getSurName() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public List<HonorificTitles> getHonorificTitles() {
                return new ArrayList<>();
            }

            @Override
            public List<HeraldicTitles> getHeraldicTitles() {
                return new ArrayList<>();
            }

            @Override
            public String getDisplayName() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getFullName() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }
        };
    }

    @Override
    public Gender getGender() {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.office.contacts.jpa.JPAPersonContact.getGender
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
