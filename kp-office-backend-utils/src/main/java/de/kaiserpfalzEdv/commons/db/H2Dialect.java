/*
 * Copyright (c) 2014 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzEdv.commons.db;

import org.hibernate.metamodel.spi.TypeContributions;
import org.hibernate.service.ServiceRegistry;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&lt;
 * @since 0.1.0
 */
public class H2Dialect extends org.hibernate.dialect.H2Dialect {
    @Override
    public void contributeTypes(final TypeContributions typeContributions, final ServiceRegistry serviceRegistry) {
        super.contributeTypes(typeContributions, serviceRegistry);
        typeContributions.contributeType(new ZonedDateTimeType());
    }
}
