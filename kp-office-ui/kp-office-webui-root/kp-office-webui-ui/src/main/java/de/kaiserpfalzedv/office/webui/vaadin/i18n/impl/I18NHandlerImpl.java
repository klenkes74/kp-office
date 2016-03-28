/*
 * Copyright 2016 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzEdv.vaadin.i18n.impl;

import de.kaiserpfalzEdv.vaadin.i18n.I18NHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * This class handles the internationalization via message bundles.
 *
 * @author rlic
 * @since 1.0.0
 */
public class I18NHandlerImpl implements I18NHandler {
    /**
     * The minimum number of characters in a filename for the I18N handler.
     */
    public static final  int            MIN_FILE_NAME_LENGTH = 4;
    /**
     * The maximum number of characters in a filename for the I18N handler.
     */
    public static final  int            MAX_FILE_NAME_LENGTH = 500;
    private static final long           serialVersionUID     = -7530775063259133220L;
    private static final Logger         LOG                  = LoggerFactory.getLogger(I18NHandlerImpl.class);
    /**
     * The java resource bundle used for retrieving the messages.
     */
    private transient    ResourceBundle messageBundle        = null;

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

    /**
     * Creates a new instance of this handler.
     *
     * @param i18nFileName The filename of the jave resource bundle.
     * @param locale       the locale for this handler.
     *
     * @throws IllegalArgumentException if the filename or the locale is NULL or
     *                                  the filename is empty.
     */
    public I18NHandlerImpl(final String i18nFileName, final Locale locale) {
        LOG.trace("***** Created: {}", this);
        checkArgument(isNotBlank(i18nFileName), "i18nFileName must not be empty");
        checkArgument(locale != null, "locale must be set (null is not allowed)");

        this.i18nFileName = i18nFileName;
        LOG.trace("*   *   translation file: {} ({})", this.i18nFileName, getClass().getResource(this.i18nFileName));

        this.locale = locale;
        LOG.trace("*   *   default locale: {}", this.locale);

        LOG.debug("***** Initialized: {}", this);
    }

    @Override
    public String getI18NFileName() {
        return i18nFileName;
    }

    @Override
    public void setI18NFileName(@NotNull final String i18nFileName) {
        checkArgument(i18nFileName != null && !i18nFileName.isEmpty(), "i18nFileName must not be empty");

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
        checkArgument(locale != null, "locale must be not null");

        //noinspection ConstantConditions
        if (!locale.equals(this.locale)) {
            LOG.trace("Setting locale to '{}'.", locale.getDisplayName());

            this.locale = locale;

            resetMessageBundle();
        }
    }

    /**
     * Initializes the message bundle (if not already initialized). This is our lazy loading ...
     *
     * @return The message bundle.
     */
    private ResourceBundle getMessageBundle() {
        if (messageBundle == null) {
            messageBundle = ResourceBundle.getBundle(i18nFileName, locale);
        }

        return messageBundle;
    }

    /**
     * Clears our resource bundle cache.
     */
    private void resetMessageBundle() {
        ResourceBundle.clearCache();
        messageBundle = null;
    }

    @Override
    public String get(final String key) {
        checkArgument(isNotBlank(key), "key must not be empty");

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

        LOG.trace("Translating {}='{}' with parameters: {} ...", key, get(key), parameters);

        return message.format(parameters);
    }
}