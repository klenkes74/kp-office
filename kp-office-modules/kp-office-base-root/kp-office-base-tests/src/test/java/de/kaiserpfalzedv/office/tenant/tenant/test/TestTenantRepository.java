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

package de.kaiserpfalzedv.office.tenant.tenant.test;

import java.util.Set;
import java.util.UUID;

import javax.inject.Inject;

import com.google.common.collect.Sets;
import de.kaiserpfalzedv.office.tenant.Tenant;
import de.kaiserpfalzedv.office.tenant.TenantDoesNotExistException;
import de.kaiserpfalzedv.office.tenant.TenantExistsException;
import de.kaiserpfalzedv.office.tenant.adapter.data.TenantDataAdapter;
import de.kaiserpfalzedv.office.tenant.mock.TenantMock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-28
 */
public class TestTenantRepository implements TenantDataAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(TestTenantRepository.class);

    @Inject
    private TenantMock mock;

    @Override
    public Tenant create(Tenant tenant) throws TenantExistsException {
        return mock.create(tenant);
    }

    @Override
    public Tenant retrieve(UUID id) throws TenantDoesNotExistException {
        return mock.retrieve(id);
    }

    @Override
    public Set<Tenant> retrieve() {
        return Sets.newHashSet(mock.retrieve());
    }

    @Override
    public Tenant update(Tenant tenant) throws TenantDoesNotExistException, TenantExistsException {
        return mock.update(tenant);
    }

    @Override
    public Tenant delete(UUID id) {
        try {
            Tenant result = retrieve(id);
            mock.delete(id);

            return result;
        } catch (TenantDoesNotExistException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);

            return null;
        }
    }
}
