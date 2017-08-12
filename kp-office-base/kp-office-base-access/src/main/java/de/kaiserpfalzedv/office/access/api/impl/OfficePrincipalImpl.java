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

package de.kaiserpfalzedv.office.access.api.impl;

import java.util.Set;
import java.util.UUID;

import de.kaiserpfalzedv.office.access.api.OfficePermission;
import de.kaiserpfalzedv.office.access.api.OfficePrincipal;
import de.kaiserpfalzedv.office.access.api.OfficeRole;
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
        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.office.access.api.OfficePrincipalImplImpl.getId
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public UUID getTenant() {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.office.access.api.implOfficePrincipalImpl.getTenant
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getKey() {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.office.access.api.implOfficePrincipalImpl.getKey
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getName() {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.office.access.api.implOfficePrincipalImpl.getName
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getDisplayName() {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.office.access.api.OfficePrincipalImplImpl.getDisplayName
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getFullName() {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.office.access.api.OfficePrincipalImplImpl.getFullName
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Set<OfficeRole> getRoles() {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.office.access.api.implOfficePrincipalImpl.getRoles
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isInRole(OfficeRole role) {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.office.access.api.implOfficePrincipalImpl.isInRole
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean hasPermission(OfficePermission permission) {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.office.access.api.OfficePrincipalImplImpl.hasPermission
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
