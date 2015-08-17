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

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 23.02.15 10:24
 */
@Entity
@Table(
        name = "i18n_translations",
        uniqueConstraints = {
                @UniqueConstraint(name = "PRIMARY", columnNames = {"key_", "language_"})
        }
)
public class TranslationEntryImpl {

    @EmbeddedId
    private TranslationKey key;

    @Column(name = "value_", length = 2000)
    private String value;


    @Deprecated // Only for JPA, Jackson, JAX-B, ...
    protected TranslationEntryImpl() {}

    public TranslationEntryImpl(
            @NotNull final String key,
            @NotNull final String language,
            @NotNull final String value
    ) {
        this.key = new TranslationKey(key, language);
        this.value = value;
    }


    public String getKey() {
        return key.getKey();
    }

    public String getLanguage() {
        return key.getLanguage();
    }

    public String getValue() {
        return value;
    }
}
