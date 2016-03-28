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

package de.kaiserpfalzEdv.vaadin.about;

import com.vaadin.spring.annotation.UIScope;
import de.kaiserpfalzEdv.vaadin.backend.about.AboutPageProvider;
import de.kaiserpfalzEdv.vaadin.backend.about.ModuleVersion;
import de.kaiserpfalzEdv.vaadin.backend.db.main.DatabaseChangeLog;
import de.kaiserpfalzEdv.vaadin.ui.mvp.impl.BasePresenter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 10.09.15 07:32
 */
@Named
@UIScope
public class AboutPresenter extends BasePresenter<AboutView> {
    private static final long serialVersionUID = 6148124921553637817L;

    private static final Logger LOG = LoggerFactory.getLogger(AboutPresenter.class);


    private AboutPageProvider provider;


    @Inject
    public AboutPresenter(
            final AboutPageProvider provider
    ) {
        this.provider = provider;
    }


    @Override
    public void setView(AboutView view) {
        super.setView(view);

        view.setModuleVersions(getModuleVersions());
        view.setDatabaseChangeLog(getDatabaseChangeLog());
        view.setLicense(getLicense());
    }


    public List<ModuleVersion> getModuleVersions() {
        return provider.getModuleVersions();
    }

    public List<DatabaseChangeLog> getDatabaseChangeLog() {
        return provider.getDatabaseChangeLog();
    }

    public String getLicense() {
        return provider.getLicense();
    }
}
