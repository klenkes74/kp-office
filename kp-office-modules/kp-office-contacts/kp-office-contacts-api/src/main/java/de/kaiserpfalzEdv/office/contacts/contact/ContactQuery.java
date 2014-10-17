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

package de.kaiserpfalzEdv.office.contacts.contact;

import de.kaiserpfalzEdv.office.contacts.address.phone.AreaCode;
import de.kaiserpfalzEdv.office.contacts.address.postal.PostCode;

import javax.xml.registry.infomodel.PersonName;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author klenkes
 * @since 2014Q
 */
public class ContactQuery implements Serializable {
    private final HashSet<PostCode> postCodes = new HashSet<>();
    private final HashSet<AreaCode> areaCodes = new HashSet<>();
    private UUID id;
    private String name;
    private String number;
    private PersonName person;
    private Contact contact;
    private Contact owner;
    private PersonName boardMember;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


    public PersonName getPerson() {
        return person;
    }

    public void setPerson(PersonName person) {
        this.person = person;
    }


    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }


    public Contact getOwner() {
        return owner;
    }

    public void setOwner(Contact owner) {
        this.owner = owner;
    }


    public PersonName getBoardMember() {
        return boardMember;
    }

    public void setBoardMember(PersonName boardMember) {
        this.boardMember = boardMember;
    }


    public Set<PostCode> getPostCodes() {
        return Collections.unmodifiableSet(postCodes);
    }

    public void setPostCodes(Collection<? extends PostCode> codes) {
        this.postCodes.clear();

        if (codes != null)
            this.postCodes.addAll(codes);
    }

    public void addPostCode(PostCode code) {
        postCodes.add(code);
    }


    public Set<AreaCode> getAreaCodes() {
        return Collections.unmodifiableSet(areaCodes);
    }

    public void setAreaCodes(Collection<? extends AreaCode> codes) {
        this.areaCodes.clear();

        if (codes != null)
            this.areaCodes.addAll(codes);
    }

    public void addAreaCode(AreaCode code) {
        areaCodes.add(code);
    }
}
