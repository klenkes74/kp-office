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

package de.kaiserpfalzEdv.office.contacts;

import de.kaiserpfalzEdv.office.contacts.address.Address;
import de.kaiserpfalzEdv.office.contacts.address.AddressType;
import de.kaiserpfalzEdv.office.contacts.address.AddressUsage;
import de.kaiserpfalzEdv.office.contacts.address.phone.PhoneNumber;
import de.kaiserpfalzEdv.office.contacts.address.phone.PhoneNumberType;
import de.kaiserpfalzEdv.office.contacts.contact.Contact;
import de.kaiserpfalzEdv.office.contacts.contact.ContactAlreadyExistsException;
import de.kaiserpfalzEdv.office.contacts.contact.ContactNotRemovedException;
import de.kaiserpfalzEdv.office.contacts.contact.ContactQuery;
import de.kaiserpfalzEdv.office.contacts.contact.InvalidContactException;
import de.kaiserpfalzEdv.office.contacts.contact.NoSuchContactException;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * This service handles accounting and all changes to them.
 *
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public interface ContactService {
    /**
     * Creates a new contact in the database.
     *
     * @param contact The contact to be saved into the database.
     * @throws ContactAlreadyExistsException There is already a contact like this (at least with the same UUID).
     * @throws InvalidContactException       The contact given is not consistent. Not all data needed is given.
     */
    public void createContact(@NotNull final Contact contact) throws ContactAlreadyExistsException, InvalidContactException;


    /**
     * @param id UUID of the contact to be retrieved.
     * @return The contact with the given UUID.
     * @throws NoSuchContactException There is no contact with the given UUID.
     */
    public Contact retrieveContact(@NotNull final UUID id) throws NoSuchContactException;


    /**
     * @param query The query to load all accounting for.
     * @return An iterable of all accounting matching the query. May be empty.
     */
    public Iterable<Contact> retrieveContacts(@NotNull final ContactQuery query);


    /**
     * Adds the given address to the contact.
     *
     * @param contact Contact to which the address should be added.
     * @param address Address to be added to the contact.
     * @throws NoSuchContactException If there is no such contact in the database.
     */
    public void addAddress(@NotNull final Contact contact, @NotNull final Address address) throws NoSuchContactException;


    /**
     * Changes the given address of this contact.
     *
     * @param contact    Contact to be worked on.
     * @param oldAddress Old address to be replaced.
     * @param newAddress New address to replace the old one.
     * @throws NoSuchContactException There is no such contact in the database.
     */
    public void replaceAddress(@NotNull final Contact contact, @NotNull final Address oldAddress, @NotNull final Address newAddress) throws NoSuchContactException;


    /**
     * Changes the type of address.
     *
     * @param contact Contact to be worked on.
     * @param address Address whose type is to be changed.
     * @param type    New type of the address.
     * @throws NoSuchContactException There is no such contact in the database.
     */
    public void changeAddressType(@NotNull final Contact contact, @NotNull final Address address, @NotNull final AddressType type) throws NoSuchContactException;


    /**
     * Changes the usage type of the address.
     *
     * @param contact Contact to be worked on.
     * @param address Address whose usage type is to be changed.
     * @param usage   New usage of the address.
     * @throws NoSuchContactException There is no such contact in the database.
     */
    public void changeAddressUsage(@NotNull final Contact contact, @NotNull final Address address, @NotNull final AddressUsage usage) throws NoSuchContactException;

    /**
     * Changes the kind of phone number for this phone number address.
     *
     * @param contact Contact to be worked on.
     * @param number  The phone number which is of another kind.
     * @param kind    The new type of phone number.
     * @throws NoSuchContactException There is no contact with this id in the database.
     */
    public void changePhoneNumberKind(@NotNull final Contact contact, @NotNull final PhoneNumber number, @NotNull final PhoneNumberType kind) throws NoSuchContactException;

    /**
     * Removes the address from this contact.
     *
     * @param contact Contact to be worked on.
     * @param address Address to be removed.
     * @throws NoSuchContactException There is no such contact in the database.
     */
    public void deleteAddress(@NotNull final Contact contact, @NotNull final Address address) throws NoSuchContactException;


    /**
     * Adds a new sub contact to this contact.
     *
     * @param contact    Contact to be worked on.
     * @param subContact Contact to be added as sub contact.
     * @throws NoSuchContactException There is no such contact in the database.
     */
    public void addSubContact(@NotNull final Contact contact, @NotNull final Contact subContact) throws NoSuchContactException;

    /**
     * Replaces a sub contact of this contact.
     *
     * @param contact       Contact to be worked on.
     * @param oldSubContact old sub contact to be replaced.
     * @param newSubContact new sub contact to replace old one.
     * @throws NoSuchContactException There is no such contact in the database.
     */
    public void replaceSubContact(@NotNull final Contact contact, @NotNull final Contact oldSubContact, @NotNull final Contact newSubContact) throws NoSuchContactException;

    /**
     * Removes a sub contact from this contact.
     *
     * @param contact    Contact to be worked on.
     * @param subContact Sub contact to be removed from this contact.
     * @throws NoSuchContactException There is no such contact in the database.
     */
    public void deleteSubContact(@NotNull final Contact contact, @NotNull final Contact subContact) throws NoSuchContactException;


    /**
     * Deletes a contact.
     *
     * @param contact Contact to be removed from database.
     * @throws ContactNotRemovedException The contact can not be removed. Check the cause.
     */
    public void deleteContact(Contact contact) throws ContactNotRemovedException;
}
