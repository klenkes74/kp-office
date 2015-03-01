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

package de.kaiserpfalzEdv.office.commons.i18n;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 13.02.15 02:34
 */
class MessageKeyImpl implements MessageKey {
    private static final String[] RESULT_TYPE = new String[1];

    private final String key;
    private final String defaultMessage;
    private final LinkedList<MessageDetailData<? extends Serializable>> data = new LinkedList<>();


    public MessageKeyImpl(
            @NotNull final String key,
            @NotNull final String defaultMessage,
            @NotNull final List<MessageDetailData<? extends Serializable>> data
    ) {
        this.key = key;
        this.defaultMessage = defaultMessage;
        this.data.addAll(data);
    }


    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getDefaultMessage() {
        return defaultMessage;
    }

    @Override
    public List<MessageDetailData<? extends Serializable>> getDetailData() {
        return Collections.unmodifiableList(data);
    }


    @Override
    public String[] getData(Locale locale) {
        ArrayList<String> result = new ArrayList<>(data.size());

        data.forEach(
                t -> {
                    result.add(t.getValue(locale));
                }
        );

        return result.toArray(RESULT_TYPE);
    }
}
