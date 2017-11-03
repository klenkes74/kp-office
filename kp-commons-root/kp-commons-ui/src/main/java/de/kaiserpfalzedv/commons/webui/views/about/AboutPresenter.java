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

import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import de.kaiserpfalzedv.commons.api.info.DataSchemaChange;
import de.kaiserpfalzedv.commons.api.info.SoftwareInformation;
import de.kaiserpfalzedv.commons.webui.i18n.VaadinI18NHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.addon.cdimvp.AbstractMVPPresenter;
import org.vaadin.addon.cdimvp.AbstractMVPPresenter.ViewInterface;

import javax.inject.Inject;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-11-02
 */
@ViewInterface(AboutView.class)
public class AboutPresenter extends AbstractMVPPresenter<AboutView> {
    public static final String VIEW_ENTER = "AboutPresenter_ve";
    private static final Logger LOG = LoggerFactory.getLogger(AboutPresenter.class);

    private VaadinI18NHandler i18n;
    private SoftwareInformation data;

    @Inject
    public AboutPresenter(
            final VaadinI18NHandler i18n,
            final SoftwareInformation information
    ) {
        this.i18n = i18n;
        this.data = information;
    }


    @Override
    public void viewEntered() {
        LOG.info("Activated view: {}", view.getClass().getSimpleName());

        DataProvider<DataSchemaChange, ?> changes = new ListDataProvider<>(data.getChanges());
        DatabaseVersionContainer schemaChanges = new DatabaseVersionContainer(i18n, changes);
        view.setSchemaChanges(schemaChanges);

        SoftwareDisclaimersContainer disclaimers = new SoftwareDisclaimersContainer(i18n, data.getLibraries());
        view.setLibraries(disclaimers);
    }
}
