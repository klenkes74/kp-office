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

package de.kaiserpfalzEdv.commons.security;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.security.Principal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public class SubjectDTO implements Subject, Serializable {
    private static final long serialVersionUID = 7811761344743525287L;


    private Set<Principal> principals = new HashSet<Principal>();


    @Deprecated // Only for Jackson, JAX-P and JPA!
    public SubjectDTO() {}

    @SuppressWarnings("deprecation")
    public SubjectDTO(final Collection<Principal> principals) {
        setPrincipals(principals);
    }


    public Set<Principal> getPrincipals() {
        return principals;
    }

    @Deprecated // Only for Jackson, JAX-P and JPA!
    public void setPrincipals(Collection<Principal> principals) {
        this.principals.clear();

        if (principals != null) {
            this.principals.addAll(principals);
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
        SubjectDTO rhs = (SubjectDTO) obj;
        return new EqualsBuilder()
                .append(this.getPrincipals(), rhs.getPrincipals())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getPrincipals())
                .toHashCode();
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("principals", formatPrincipals())
                .toString();
    }

    private String formatPrincipals() {
        StringBuilder result = new StringBuilder("[");

        for (Principal p : principals) {
            result.append(p.getName()).append(",");
        }

        return result.append("]").toString();
    }
}
