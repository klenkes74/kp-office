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
import com.vaadin.ui.Grid;
import de.kaiserpfalzedv.commons.api.metainfo.DataSchemaChange;
import de.kaiserpfalzedv.commons.webui.i18n.I18NHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 1.0.0
 */
public class DatabaseVersionContainer extends Grid<DataSchemaChange> {
    private static final Logger LOG = LoggerFactory.getLogger(DatabaseVersionContainer.class);

    public DatabaseVersionContainer(final I18NHandler i18n, final DataProvider<DataSchemaChange, ?> data) {
        setSizeFull();
        setResponsive(true);
        setReadOnly(true);
        setHeaderVisible(true);

        setCaption(i18n.get("about.dataschema.caption"));
        setDescription(i18n.get("about.dataschema.description"));

        setColumnOrder("id", "dateExecuted", "execType", "description", "comments", "tag", "contexts", "label");
        setFrozenColumnCount(1);

        setDataProvider(data);
    }

}
