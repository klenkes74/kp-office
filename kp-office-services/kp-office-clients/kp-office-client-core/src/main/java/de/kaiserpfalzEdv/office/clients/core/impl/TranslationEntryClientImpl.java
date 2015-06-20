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

package de.kaiserpfalzEdv.office.clients.core.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.kaiserpfalzEdv.office.core.i18n.TranslationEntry;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 06.04.15 17:24
 */
public class TranslationEntryClientImpl implements TranslationEntry, Serializable {
    private static final long serialVersionUID = -6498892949081698892L;


    private String key;
    private String language;
    private String value;


    public TranslationEntryClientImpl(
            @JsonProperty("key") String key,
            @JsonProperty("language") String language,
            @JsonProperty("value") String value
    ) {
        this.key = key;
        this.language = language;
        this.value = value;
    }


    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getLanguage() {
        return language;
    }

    @Override
    public String getValue() {
        return value;
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
        TranslationEntryClientImpl rhs = (TranslationEntryClientImpl) obj;
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
                .append("key", key)
                .append("language", language)
                .append("value", value)
                .toString();
    }
}
