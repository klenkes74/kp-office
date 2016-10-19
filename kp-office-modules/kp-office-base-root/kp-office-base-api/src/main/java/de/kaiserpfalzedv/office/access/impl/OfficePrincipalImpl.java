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

package de.kaiserpfalzedv.office.access.impl;

import java.util.UUID;

import de.kaiserpfalzedv.office.access.OfficePrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-10-16
 */
public class OfficePrincipalImpl implements OfficePrincipal {
    private static final Logger LOG = LoggerFactory.getLogger(OfficePrincipalImpl.class);

    @Override
    public UUID getId() {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.office.access.impl.OfficePrincipalImpl.getId
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public UUID getTenant() {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.office.access.impl.OfficePrincipalImpl.getTenant
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getKey() {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.office.access.impl.OfficePrincipalImpl.getKey
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getName() {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.office.access.impl.OfficePrincipalImpl.getName
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
