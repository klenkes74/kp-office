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

package de.kaiserpfalzEdv.office.core.i18n;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 27.02.15 17:49
 */
public class TranslationEntry implements Serializable {
    private static final long serialVersionUID = 5522787541139868626L;

    private String key;
    private String language;
    private String value;


    @Deprecated // Only for JPA, JAX-B and Jackson
    public TranslationEntry() {}


    public TranslationEntry(final String key, final String language, final String value) {
        setKey(key);
        setLanguage(language);
        setValue(value);
    }


    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
    }


    public String getLanguage() {
        return language;
    }

    public void setLanguage(final String language) {
        this.language = language;
    }


    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
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
        TranslationEntry rhs = (TranslationEntry) obj;
        return new EqualsBuilder()
                .append(this.key, rhs.key)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(key)
                .toHashCode();
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("key", key)
                .append("language", language)
                .toString();
    }
}
