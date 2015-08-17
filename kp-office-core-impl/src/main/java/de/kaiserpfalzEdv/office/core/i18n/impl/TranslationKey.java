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

package de.kaiserpfalzEdv.office.core.i18n.impl;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 23.02.15 10:26
 */
@Embeddable
public class TranslationKey implements Serializable {
    private static final long serialVersionUID = 9185114967069556204L;


    @Column(name = "key_", length = 100, nullable = false, updatable = false)
    private String key;

    @Column(name = "language_", length = 20, nullable = false, updatable = false)
    private String language;


    @Deprecated // Only for JPA, Jackson, JAX-B, ...
    protected TranslationKey() {}


    public TranslationKey(
            @NotNull final String key,
            @NotNull final String language
    ) {
        this.key = key;
        this.language = language;
    }


    public String getKey() {
        return key;
    }

    public String getLanguage() {
        return language;
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
        TranslationKey rhs = (TranslationKey) obj;
        return new EqualsBuilder()
                .append(this.key, rhs.key)
                .append(this.language, rhs.language)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(key)
                .append(language)
                .toHashCode();
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append(key)
                .append(language)
                .toString();
    }
}
