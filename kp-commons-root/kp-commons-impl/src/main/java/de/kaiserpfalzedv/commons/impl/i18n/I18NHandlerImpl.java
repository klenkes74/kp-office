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

package de.kaiserpfalzedv.commons.impl.i18n;

import de.kaiserpfalzedv.commons.api.i18n.I18NHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.Dependent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * This class handles the internationalization via message bundles.
 *
 * @author klenkes (rlichti@kaiserpfalz-edv.de)
 * @since 1.0.0
 */
@Dependent
public class I18NHandlerImpl implements I18NHandler {
    /**
     * The minimum number of characters in a filename for the I18N handler.
     */
    public static final int MIN_FILE_NAME_LENGTH = 4;
    /**
     * The maximum number of characters in a filename for the I18N handler.
     */
    public static final int MAX_FILE_NAME_LENGTH = 500;
    private static final long serialVersionUID = -8775219620795970093L;
    private static final Logger LOG = LoggerFactory.getLogger(I18NHandlerImpl.class);
    /**
     * The java resource bundle used for retrieving the messages.
     */
    private transient ResourceBundle messageBundle = null;

    /**
     * The filename (basename) of the property file containing the translations.
     */
    @NotNull
    @Size(min = MIN_FILE_NAME_LENGTH, max = MAX_FILE_NAME_LENGTH)
    private String i18nFileName = null;

    /**
     * The locale for this translation handler.
     */
    @NotNull
    private Locale locale = null;

    public I18NHandlerImpl() {
        this("translation", Locale.GERMANY);
    }

    /**
     * Creates a new instance of this handler.
     *
     * @param i18nFileName The filename of the jave resource bundle.
     * @param locale       the locale for this handler.
     *
     * @throws IllegalArgumentException if the filename or the locale is NULL or
     *                                  the filename is empty.
     */
    public I18NHandlerImpl(@NotNull final String i18nFileName, @NotNull final Locale locale) {
        this.i18nFileName = i18nFileName;
        this.locale = locale;

        LOG.trace("Initialized {}: translation file={}, default locale={}", this, this.i18nFileName, this.locale);
    }

    @Override
    public String getI18NFileName() {
        return i18nFileName;
    }

    @Override
    public void setI18NFileName(@NotNull final String i18nFileName) {
        //noinspection ConstantConditions
        if (!i18nFileName.equals(this.i18nFileName)) {
            URL file = getClass().getResource(i18nFileName);
            LOG.trace("Setting message bundle file name to '{}' ({})", i18nFileName, file);

            this.i18nFileName = i18nFileName;

            resetMessageBundle();
        }
    }

    @Override
    public Locale getLocale() {
        return locale;
    }

    @Override
    public void setLocale(@NotNull final Locale locale) {
        //noinspection ConstantConditions
        if (!locale.equals(this.locale)) {
            if (LOG.isTraceEnabled()) {
                LOG.trace("Setting locale to '{}'.", locale.getDisplayName());
            }

            this.locale = locale;

            resetMessageBundle();
        }
    }

    @Override
    public String get(@NotNull final String key) {
        String result;

        try {
            result = getMessageBundle().getString(key);
        } catch (MissingResourceException e) {
            LOG.warn(
                    "Could not find resource string '{}' in resource bundle file '{}'. Returning '{}' instead.",
                    key, i18nFileName, key
            );

            result = key;
        }

        return result;
    }

    @Override
    public String get(final String key, final Object[] parameters) {
        MessageFormat message = new MessageFormat(get(key));
        message.setLocale(locale);

        if (LOG.isTraceEnabled()) {
            LOG.trace("Translating {}='{}' with parameters: {} ...", key, get(key), parameters);
        }

        return message.format(parameters);
    }

    /**
     * Clears our resource bundle cache.
     */
    private void resetMessageBundle() {
        ResourceBundle.clearCache();
        messageBundle = null;
    }

    /**
     * Initializes the message bundle (if not already initialized). This is our lazy loading ...
     *
     * @return The message bundle.
     */
    private synchronized ResourceBundle getMessageBundle() {
        if (messageBundle == null) {
            messageBundle = ResourceBundle.getBundle(i18nFileName, locale);
        }

        return messageBundle;
    }
}