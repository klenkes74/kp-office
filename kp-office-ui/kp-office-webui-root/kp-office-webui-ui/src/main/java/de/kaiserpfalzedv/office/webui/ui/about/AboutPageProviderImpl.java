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

package de.kaiserpfalzEdv.piracc.about;

import de.kaiserpfalzEdv.vaadin.about.ModuleVersionListBuilder;
import de.kaiserpfalzEdv.vaadin.backend.about.AboutPageProvider;
import de.kaiserpfalzEdv.vaadin.backend.about.ModuleVersion;
import de.kaiserpfalzEdv.vaadin.backend.db.main.DatabaseChangeLog;
import de.kaiserpfalzEdv.vaadin.backend.db.main.DatabaseChangeLogRepository;
import de.kaiserpfalzEdv.vaadin.i18n.I18NHandler;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.List;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 12.09.15 19:10
 */
@Named
public class AboutPageProviderImpl implements AboutPageProvider {
    private static final long serialVersionUID = -4657336902502034767L;

    private static final Logger LOG = LoggerFactory.getLogger(AboutPageProviderImpl.class);

    private static final String[] MODULES = {
            "vaadin",
            "spring",
            "vaadin-spring",
            "spring-data-jpa", "hibernate", "ehcache", "liquibase", "querydsl",
            "jbcrypt",
            "guava",
            "log4j",
            "log4jdbc", "mysql", "postgresql", "h2", "derby",
    };

    private I18NHandler                 i18n;
    private DatabaseChangeLogRepository changeLogRepository;


    @Inject
    public AboutPageProviderImpl(
            final I18NHandler i18n,
            final DatabaseChangeLogRepository changeLogRepository
    ) {
        this.i18n = i18n;
        this.changeLogRepository = changeLogRepository;
    }


    @Override
    public List<ModuleVersion> getModuleVersions() {
        return new ModuleVersionListBuilder(i18n).withModules(MODULES).build();
    }

    @Override
    public String getLicense() {
        String result;

        try {
            InputStream is = getClass().getResourceAsStream("/LICENSE.html");
            StringWriter writer = new StringWriter();
            IOUtils.copy(is, writer, "UTF-8");
            result = writer.toString();
            writer.close();
            is.close();
        } catch (IOException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);

            result = i18n.get("about.license.loading-failed");
        }

        return result;
    }

    @Override
    public List<DatabaseChangeLog> getDatabaseChangeLog() {
        return changeLogRepository.findAll();
    }
}
