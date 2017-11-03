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

package de.kaiserpfalzedv.commons.jpa;

import de.kaiserpfalzedv.commons.api.info.DataSchemaChange;
import de.kaiserpfalzedv.commons.ejb.info.Changes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.inject.Produces;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 1.0.0
 */
public class DataSchemaChangeProvider {
    private static final Logger LOG = LoggerFactory.getLogger(DataSchemaChangeProvider.class);

    @Produces
    @Changes
    public Collection<DataSchemaChange> changes() {
        ArrayList<DataSchemaChange> result = new ArrayList<>();

        return result;
    }
}
