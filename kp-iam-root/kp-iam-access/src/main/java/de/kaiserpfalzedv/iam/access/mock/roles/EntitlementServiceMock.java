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

package de.kaiserpfalzedv.iam.access.mock.roles;

import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;

import de.kaiserpfalzedv.commons.api.cdi.Mock;
import de.kaiserpfalzedv.commons.api.data.BaseService;
import de.kaiserpfalzedv.commons.api.data.ObjectDoesNotExistException;
import de.kaiserpfalzedv.commons.api.data.ObjectExistsException;
import de.kaiserpfalzedv.commons.api.data.paging.Pageable;
import de.kaiserpfalzedv.commons.api.data.paging.PagedListable;
import de.kaiserpfalzedv.commons.api.data.query.Predicate;
import de.kaiserpfalzedv.iam.access.api.roles.Entitlement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-11-23
 */
@ApplicationScoped
@Mock
public class EntitlementServiceMock implements BaseService<Entitlement> {
    private static final Logger LOG = LoggerFactory.getLogger(EntitlementServiceMock.class);

    @Override
    public Entitlement create(Entitlement data) throws ObjectExistsException {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.iam.access.mock.roles.EntitlementServiceMock.create
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Entitlement retrieve(UUID id) throws ObjectDoesNotExistException {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.iam.access.mock.roles.EntitlementServiceMock.retrieve
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public PagedListable<Entitlement> retrieve(Predicate<Entitlement> predicate, Pageable page) {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.iam.access.mock.roles.EntitlementServiceMock.retrieve
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Entitlement update(Entitlement data) throws ObjectExistsException, ObjectDoesNotExistException {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.iam.access.mock.roles.EntitlementServiceMock.update
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void delete(UUID id) {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.iam.access.mock.roles.EntitlementServiceMock.delete
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
