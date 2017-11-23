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

import javax.enterprise.context.Dependent;

import de.kaiserpfalzedv.commons.api.cdi.Mock;
import de.kaiserpfalzedv.commons.api.data.ObjectDoesNotExistException;
import de.kaiserpfalzedv.commons.api.data.ObjectExistsException;
import de.kaiserpfalzedv.commons.api.data.paging.Pageable;
import de.kaiserpfalzedv.commons.api.data.paging.PagedListable;
import de.kaiserpfalzedv.commons.api.data.query.Predicate;
import de.kaiserpfalzedv.iam.access.api.roles.Role;
import de.kaiserpfalzedv.iam.access.api.roles.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-11-23
 */
@Dependent
@Mock
public class RoleServiceMock implements RoleService {
    private static final Logger LOG = LoggerFactory.getLogger(RoleServiceMock.class);

    @Override
    public Role create(Role data) throws ObjectExistsException {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.iam.access.mock.roles.RoleServiceMock.create
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Role retrieve(UUID id) throws ObjectDoesNotExistException {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.iam.access.mock.roles.RoleServiceMock.retrieve
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public PagedListable<Role> retrieve(Predicate<Role> predicate, Pageable page) {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.iam.access.mock.roles.RoleServiceMock.retrieve
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Role update(Role data) throws ObjectExistsException, ObjectDoesNotExistException {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.iam.access.mock.roles.RoleServiceMock.update
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void delete(UUID id) {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.iam.access.mock.roles.RoleServiceMock.delete
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
