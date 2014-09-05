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
import org.apache.shiro.authc.RememberMeAuthenticationToken;

import java.io.Serializable;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public class OfficeToken implements RememberMeAuthenticationToken, Serializable {
    private static final long serialVersionUID = -5961796175346838277L;

    private boolean rememberMe = false;

    private OfficePrincipal user;
    private OfficePrincipal system;

    private Serializable credentials;


    public OfficeToken(final String userAccount, final String userRealm, final Serializable credentials) {
        setPrincipal(new OfficePrincipalDTO(userAccount, userRealm));
        setCredentials(credentials);
    }

    public OfficeToken(final String userAccount, final String userRealm, final String systemAccount, final String systemRealm, final Serializable credentials) {
        setPrincipal(new OfficePrincipalDTO(userAccount, userRealm));
        setSystem(new OfficePrincipalDTO(systemAccount, systemRealm));
        setCredentials(credentials);
    }


    @Override
    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(final boolean state) {
        this.rememberMe = state;
    }

    @Override
    public Object getPrincipal() {
        return user;
    }

    private void setPrincipal(final OfficePrincipal principal) {
        this.user = principal;
    }


    public Object getSystem() {
        return system;
    }

    private void setSystem(final OfficePrincipal principal) {
        this.system = principal;
    }


    @Override
    public Serializable getCredentials() {
        return credentials;
    }

    private void setCredentials(Serializable credentials) {
        this.credentials = credentials;
    }


    public OfficeTicket getTicket() {
        try {
            return (OfficeTicket) credentials;
        } catch (ClassCastException e) {
            throw new IllegalStateException("Sorry, the credentials were no valid ticket!");
        }
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
        OfficeToken rhs = (OfficeToken) obj;
        return new EqualsBuilder()
                .append(this.getPrincipal(), rhs.getPrincipal())
                .append(this.getSystem(), rhs.getSystem())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getPrincipal())
                .append(getSystem())
                .toHashCode();
    }


    @Override
    public String toString() {
        ToStringBuilder result = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("user", getPrincipal());

        if (system != null)
            result.append("system", getSystem());

        return result
                .append("rememberMe", rememberMe)
                .append("hasCredentials", credentials != null)
                .toString();
    }
}
