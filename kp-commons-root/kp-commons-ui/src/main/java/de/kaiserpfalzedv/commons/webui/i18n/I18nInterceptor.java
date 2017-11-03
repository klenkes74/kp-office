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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Locale;

/**
 * @author rlic  (rlichti@kaiserpfalz-edv.de)
 * @version 1.0.0
 * @since 28.08.15 08:35
 */
public class I18nInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(I18nInterceptor.class);

    private static final String LOCALE_FILE = "translations";

    private final HashMap<Locale, VaadinI18NHandler> i18nCache = new HashMap<>(5);


    public I18nInterceptor() {
        LOG.trace("***** Created: {}", this);
    }


    // @Pointcut("execution(void com.vaadin..*.setCaption(String))")
    public void defaultSetCaption() {}

    // @Pointcut("execution(void com.vaadin.ui.Label.setValue(String))")
    public void labelSetValue() {}


    // @Around("defaultSetCaption() || labelSetValue()")
    public Object doTranslation(final Object invocation) {
/*
        LOG.trace("Checking translations for {} ...", invocation.getSignature().getName());
        Object result = null;

        Object[] args = invocation.getArgs();
        args[0] = translate((String) args[0], UI.getCurrent().getLocale());

        try {
            result = invocation.proceed(args);
        } catch (Throwable throwable) {
            LOG.error(throwable.getClass().getSimpleName() + " caught: " + throwable.getMessage(), throwable);
        }

        return result;
*/
        return null;
    }


    private String translate(final String key, final Locale locale) {
        VaadinI18NHandler i18n = getI18nHandler(locale);

        try {
            return i18n.get(key);
        } catch (IllegalStateException e) {
            LOG.error(e.getMessage(), e);

            return key;
        }
    }

    private VaadinI18NHandler getI18nHandler(Locale locale) {
        if (!i18nCache.containsKey(locale)) {
            VaadinI18NHandler i18n = new VaadinI18NHandlerImpl(LOCALE_FILE, locale);

            i18nCache.put(locale, i18n);
        }

        return i18nCache.get(locale);
    }
}
