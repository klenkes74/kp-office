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

package de.kaiserpfalzedv.office.webui.ui;

import de.kaiserpfalzedv.vaadin.I18NHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.addon.cdiproperties.TextBundle;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

/**
 * A small mapper to map the interface of the {@link I18NHandler} to the interface {@link TextBundle} used by
 * Vaadin CDI.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-06-12
 */
@Dependent
@ApplicationScoped
public class Localizator implements TextBundle {
    private static final Logger LOG = LoggerFactory.getLogger(Localizator.class);

    private I18NHandler i18n;

    @Inject
    public Localizator(final I18NHandler i18n) {
        this.i18n = i18n;
    }

    @Override
    public String getText(String s, Object... objects) {
        return i18n.get(s, objects);
    }

    @Produces
    public TextBundle createTextBundle(final I18NHandler i18n) {
        return new Localizator(i18n);
    }
}
