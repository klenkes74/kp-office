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

package de.kaiserpfalzEdv.office.ui.core.i18n;

import com.vaadin.data.Property;

/**
 * Localizes the given string with the {@link MessageProvider} and optional data set. It is read only and setting the
 * value will result in a {@link com.vaadin.data.Property.ReadOnlyException}.
 *
 * @author klenkes
 * @version 2015Q1
 * @since 07.08.15 09:18
 */
public class LocalizedSimpleString implements Property<String> {
    private static final long serialVersionUID = -6600045861946812272L;

    private MessageProvider i18n;
    private String          key;
    private Object[]        data;
    private String          localized;

    private boolean readOnly = false;

    public LocalizedSimpleString(final String key, final MessageProvider i18n) {
        this(key, i18n, (Object) null);
    }

    public LocalizedSimpleString(final String key, final MessageProvider i18n, Object... data) {
        this.key = key;
        this.i18n = i18n;
        this.data = data;

        localized = this.i18n.resolveCode(this.key).format(this.data);
    }

    @Override
    public String getValue() {
        return localized;
    }

    @Override
    public void setValue(String newValue) throws ReadOnlyException {
        localized = i18n.resolveCode(newValue).format(data);
    }

    @Override
    public Class<? extends String> getType() {
        return String.class;
    }

    @Override
    public boolean isReadOnly() {
        return readOnly;
    }

    @Override
    public void setReadOnly(boolean newStatus) {
        readOnly = newStatus;
    }
}