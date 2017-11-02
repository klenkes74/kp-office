/*
 * Copyright 2017 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzedv.commons.webui.i18n;

import java.io.Serializable;
import java.util.Locale;

import org.vaadin.addon.cdiproperties.TextBundle;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 28.08.15 10:38
 */
public interface I18NHandler extends TextBundle, Serializable {
    /**
     * Retrieves the filename for this localization resource bundle file.
     *
     * @return The name set for this resource bundle file.
     */
    String getI18NFileName();

    /**
     * Sets the name of the localization resource bundle file.
     *
     * @param i18nFileName The filename of the resource bundle.
     *
     * @throws IllegalArgumentException if the file name given is NULl or empty.
     */
    void setI18NFileName(final String i18nFileName);

    Locale getLocale();

    void setLocale(final Locale locale);

    String get(final String key);

    String get(final String key, final Object[] parameters);
}
