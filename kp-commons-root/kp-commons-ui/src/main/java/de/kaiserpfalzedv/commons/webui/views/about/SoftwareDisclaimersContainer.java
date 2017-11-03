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

package de.kaiserpfalzedv.commons.webui.views.about;

import com.vaadin.ui.Accordion;
import com.vaadin.ui.Label;
import de.kaiserpfalzedv.commons.api.info.SoftwareLibrary;
import de.kaiserpfalzedv.commons.webui.i18n.VaadinI18NHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;
import java.util.Collection;

/**
 * The accordion containing all software disclaimers needed.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 1.0.0
 */
public class SoftwareDisclaimersContainer extends Accordion {
    private static final Logger LOG = LoggerFactory.getLogger(SoftwareDisclaimersContainer.class);

    public SoftwareDisclaimersContainer(final VaadinI18NHandler i18n, final Collection<? extends SoftwareLibrary> libraries) {
        removeAllComponents();

        setSizeFull();
        setCaption(i18n.get("about.disclaimer.caption"));
        setDescription(i18n.get("about.disclaimer.description"));
        setResponsive(true);

        libraries.forEach(this::addLibrary);
    }

    private void addLibrary(final SoftwareLibrary library) {
        Label disclaimer = new Label(library.getDisclaimer());

        addTab(disclaimer, library.getLibraryName());

        LOG.trace("Added disclamer for library: {}", library.getLibraryName());
    }

    @PreDestroy
    public void close() {
        LOG.trace("Removing all disclaimers.");
        removeAllComponents();
    }
}