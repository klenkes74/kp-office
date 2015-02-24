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

import javax.validation.constraints.NotNull;
import javax.xml.registry.JAXRException;
import javax.xml.registry.infomodel.PersonName;
import java.io.Serializable;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public class PersonalName implements Name, PersonName, Serializable {
    private static final long serialVersionUID = -999837725434284673L;


    private String fullName;

    private String firstName;
    private String middleName;
    private String lastName;


    @Deprecated // Only for jackson, JAX-B and JPA!
    public PersonalName() {
    }

    public PersonalName(@NotNull final String fullName,
                        @NotNull final String firstName,
                        @NotNull final String middleName,
                        @NotNull final String lastName) {
        this.fullName = fullName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

    @Override
    public String getDisplayName() {
        return fullName;
    }

    @Override
    public String getLastName() throws JAXRException {
        return lastName;
    }

    @Override
    public void setLastName(String lastName) throws JAXRException {
        fullName = fullName.replace(this.lastName, lastName);

        this.lastName = lastName;
    }

    @Override
    public String getFirstName() throws JAXRException {
        return firstName;
    }

    @Override
    public void setFirstName(String firstName) throws JAXRException {
        fullName = fullName.replace(this.firstName, firstName);

        this.firstName = firstName;
    }

    @Override
    public String getMiddleName() throws JAXRException {
        return middleName;
    }

    @Override
    public void setMiddleName(String middleName) throws JAXRException {
        fullName = fullName.replace(this.middleName, middleName);

        this.middleName = middleName;
    }

    @Override
    public String getFullName() throws JAXRException {
        return fullName;
    }

    @Override
    public void setFullName(String fullName) throws JAXRException {
        throw new JAXRException("Can't set a full name. Please set the name parts seperately!");
    }
}
