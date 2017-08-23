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

package de.kaiserpfalzedv.office.access.mock;

import java.util.Collection;
import java.util.Properties;
import java.util.UUID;

import de.kaiserpfalzedv.office.access.api.users.OfficePrincipal;
import de.kaiserpfalzedv.office.access.client.AccessClient;
import de.kaiserpfalzedv.office.common.api.data.ObjectDoesNotExistException;
import de.kaiserpfalzedv.office.common.api.data.ObjectExistsException;
import de.kaiserpfalzedv.office.common.api.init.InitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-10-16
 */
public class AccessMock implements AccessClient {
    private static final Logger LOG = LoggerFactory.getLogger(AccessMock.class);

    @Override
    public void init() throws InitializationException {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.office.access.mock.AccessMock.init
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void init(Properties properties) throws InitializationException {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.office.access.mock.AccessMock.init
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void close() {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.office.access.mock.AccessMock.close
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public OfficePrincipal create(OfficePrincipal data) throws ObjectExistsException {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.office.access.mock.AccessMock.create
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public OfficePrincipal retrieve(UUID id) throws ObjectDoesNotExistException {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.office.access.mock.AccessMock.retrieve
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Collection<OfficePrincipal> retrieve() {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.office.access.mock.AccessMock.retrieve
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public OfficePrincipal update(OfficePrincipal data) throws ObjectExistsException, ObjectDoesNotExistException {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.office.access.mock.AccessMock.update
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void delete(UUID id) {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.office.access.mock.AccessMock.delete
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public OfficePrincipal retrieve(String businessKey) throws ObjectDoesNotExistException {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.office.access.mock.AccessMock.retrieve
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
