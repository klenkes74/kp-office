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

package de.kaiserpfalzEdv.office.accounting.automation.impl;

import de.kaiserpfalzEdv.office.accounting.automation.FunctionKey;
import de.kaiserpfalzEdv.office.commons.server.data.KPOEntity;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 15.08.15 06:40
 */
@Entity
@Cacheable
@Table(
        schema = "accounting",
        catalog = "accounting",
        name = "functionkey",
        uniqueConstraints = {
                @UniqueConstraint(name = "functionkey_name_fk", columnNames = {"tenant_", "display_name_"}),
                @UniqueConstraint(name = "functionkey_number_fk", columnNames = {"tenant_", "display_number_"})
        }
)
public class FunctionKeyImpl extends KPOEntity implements FunctionKey {
}
