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
import java.io.Serializable;

/**
 * @author klenkes
 * @since 2014Q
 */
public class CompanyName implements Name, Serializable {
    private static final long serialVersionUID = -8217057139621510433L;

    private String fullName;


    @Deprecated // Only for Jackson, JAX-B and JPA!
    public CompanyName() {
    }


    public CompanyName(@NotNull final String fullName) {
        setDisplayName(fullName);
    }


    @Override
    public String getDisplayName() {
        return fullName;
    }

    public void setDisplayName(@NotNull final String fullName) {
        this.fullName = fullName;
    }
}
