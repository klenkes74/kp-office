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

package de.kaiserpfalzedv.commons.ejb.info;

import de.kaiserpfalzedv.commons.api.i18n.I18NHandler;
import de.kaiserpfalzedv.commons.api.info.DataSchemaChange;
import de.kaiserpfalzedv.commons.api.info.SoftwareInformation;
import de.kaiserpfalzedv.commons.api.info.SoftwareLibrary;
import de.kaiserpfalzedv.commons.api.licensing.ApplicationLicense;
import de.kaiserpfalzedv.commons.impl.info.ApplicationLicenseBuilder;
import de.kaiserpfalzedv.commons.impl.info.SoftwareInformationBuilder;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import java.util.Collection;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 1.0.0
 */
@ApplicationScoped
public class SoftwareInformationProvider {
    private static final String TITLE_KEY = "application.name";
    private static final String DESCRIPTION_KEY = "application.description";

    @Produces
    public SoftwareInformation getSoftwareInformation(
            final ApplicationLicense applicationLicense,
            @Libraries final Collection<SoftwareLibrary> libraries,
            @Changes final Collection<DataSchemaChange> changes,
            final I18NHandler i18n
    ) {
        return new SoftwareInformationBuilder()
                .withTitle(i18n.get(TITLE_KEY))
                .withDescription(i18n.get(DESCRIPTION_KEY))
                .withLicense(new ApplicationLicenseBuilder().withLicense(applicationLicense).build())
                .withLibraries(libraries)
                .withChanges(changes)
                .build();
    }
}
