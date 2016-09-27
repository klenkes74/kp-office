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

package de.kaiserpfalzedv.office.tenant.shared;

import javax.enterprise.context.ApplicationScoped;

import de.kaiserpfalzedv.office.commons.shared.converter.impl.ConverterRegistryImpl;
import de.kaiserpfalzedv.office.tenant.commands.TenantCreateCommand;
import de.kaiserpfalzedv.office.tenant.shared.converter.TenantCreateCommandConverterGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-27
 */
@ApplicationScoped
public class TenantConverterRegistry extends ConverterRegistryImpl {
    private static final Logger LOG = LoggerFactory.getLogger(TenantConverterRegistry.class);

    public TenantConverterRegistry() {
        registerConverter(TenantCreateCommand.class.getCanonicalName(), new TenantCreateCommandConverterGenerator());
    }
}
