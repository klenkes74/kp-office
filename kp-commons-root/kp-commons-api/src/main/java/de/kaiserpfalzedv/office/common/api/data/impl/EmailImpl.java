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

package de.kaiserpfalzedv.office.common.api.data.impl;

import java.util.Objects;

import de.kaiserpfalzedv.office.common.api.data.Email;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-11
 */
public class EmailImpl implements Email {
    private static final long serialVersionUID = 331049522892668133L;

    private String email;

    public EmailImpl(final String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAddress());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmailImpl)) return false;
        EmailImpl email1 = (EmailImpl) o;
        return Objects.equals(getAddress(), email1.getAddress());
    }

    @Override
    public String toString() {
        return new StringBuilder("EmailImpl{").append(getAddress()).append('}').toString();
    }

    @Override
    public String getAddress() {
        return email;
    }
}
