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

import de.kaiserpfalzedv.commons.impl.i18n.I18NHandlerImpl;

import javax.enterprise.context.Dependent;
import java.util.Locale;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * This class handles the internationalization via message bundles.
 *
 * @author klenkes (rlichti@kaiserpfalz-edv.de)
 * @since 1.0.0
 */
@Dependent
public class VaadinI18NHandlerImpl extends I18NHandlerImpl implements VaadinI18NHandler {
    public VaadinI18NHandlerImpl() {
        this("translation", Locale.GERMANY);
    }

    /**
     * Creates a new instance of this handler.
     *
     * @param i18nFileName The filename of the jave resource bundle.
     * @param locale       the locale for this handler.
     * @throws IllegalArgumentException if the filename or the locale is NULL or
     *                                  the filename is empty.
     */
    public VaadinI18NHandlerImpl(final String i18nFileName, final Locale locale) {
        super(i18nFileName, locale);
    }


    @Override
    public String getText(String s, Object... objects) {
        if (isBlank(s))
            return "";

        return get(s, objects);
    }
}