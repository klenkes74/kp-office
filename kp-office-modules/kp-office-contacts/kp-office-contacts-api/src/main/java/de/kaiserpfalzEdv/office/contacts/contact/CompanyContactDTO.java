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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public class CompanyContactDTO extends ContactDTO implements CompanyContact {
    private static final Logger LOG = LoggerFactory.getLogger(CompanyContactDTO.class);
    private final HashSet<Contact> owner = new HashSet<>();
    private final HashSet<ContactPerson> board = new HashSet<>();
    private CompanyName companyName;


    @SuppressWarnings("deprecation")
    @Deprecated // Only for Jackson, JAX-B and JPA!
    public CompanyContactDTO() {
    }


    @Override
    public CompanyName getName() {
        return companyName;
    }

    protected void setName(@NotNull final CompanyName companyName) {
        this.companyName = companyName;
    }


    @Override
    public Set<ContactPerson> getBoard() {
        return Collections.unmodifiableSet(board);
    }

    public void setBoard(@NotNull final Collection<? extends ContactPerson> board) {
        this.board.clear();

        if (board != null)
            this.board.addAll(board);
    }

    public void addBoardMember(@NotNull final ContactPerson person) {
        board.add(person);
    }

    public void removeBoardMember(@NotNull final ContactPerson person) {
        board.remove(person);
    }


    public Set<Contact> getOwner() {
        return Collections.unmodifiableSet(owner);
    }

    public void setOwner(@NotNull final Collection<? extends Contact> owner) {
        this.owner.clear();

        if (owner != null)
            this.owner.addAll(owner);
    }

    public void addOwner(@NotNull final Contact owner) {
        this.owner.add(owner);
    }

    public void removeOwner(@NotNull final Contact owner) {
        this.owner.remove(owner);
    }
}
