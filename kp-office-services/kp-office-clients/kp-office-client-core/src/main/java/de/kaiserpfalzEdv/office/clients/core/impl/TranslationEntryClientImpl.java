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

/**
 * @author klenkes
 * @version 2015Q1
 * @since 06.04.15 17:24
 */
public class TranslationEntryClientImpl extends TranslationEntry {
    private static final long serialVersionUID = 4576527357534731071L;

    public TranslationEntryClientImpl(
            @JsonProperty("key") String key,
            @JsonProperty("language") String language,
            @JsonProperty("value") String value
    ) {
        super(key, language, value);
    }
}
