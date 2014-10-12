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

package de.kaiserpfalzEdv.office.security;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public class OfficePrincipalDTO implements OfficePrincipal {
    private static final long serialVersionUID = -9132588027701605671L;

    private String user;
    private String realm;


    public OfficePrincipalDTO(final String user, final String realm) {
        setUser(user);
        setRealm(realm);
    }


    @Override
    public String getName() {
        return user + "@" + realm;
    }


    @Override
    public String getUser() {
        return user;
    }

    private void setUser(String user) {
        this.user = user;
    }


    @Override
    public String getRealm() {
        return realm;
    }

    private void setRealm(String realm) {
        this.realm = realm;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        OfficePrincipal rhs = (OfficePrincipal) obj;
        return new EqualsBuilder()
                .append(this.getUser(), rhs.getUser())
                .append(this.getRealm(), rhs.getRealm())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getUser())
                .append(getRealm())
                .toHashCode();
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("user", getUser())
                .append("realm", getRealm())
                .toString();
    }
}
