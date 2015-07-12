/*
 * Copyright 2015 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzEdv.office.ui.core.about;

import com.vaadin.navigator.View;
import com.vaadin.ui.Component;
import de.kaiserpfalzEdv.commons.jee.servlet.model.ApplicationMetaData;
import de.kaiserpfalzEdv.office.core.licence.OfficeLicence;

import javax.annotation.PreDestroy;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 12.07.15 08:04
 */
public interface AboutContent extends Component, View {
    OfficeLicence getLicense();

    void setLicense(OfficeLicence license);

    ApplicationMetaData getApplication();

    void setApplication(ApplicationMetaData application);

    void init();

    @PreDestroy
    void close();
}
