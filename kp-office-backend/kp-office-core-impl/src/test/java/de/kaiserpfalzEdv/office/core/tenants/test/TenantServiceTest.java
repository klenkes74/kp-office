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

package de.kaiserpfalzEdv.office.core.tenants.test;

import de.kaiserpfalzEdv.commons.test.CommonTestBase;
import de.kaiserpfalzEdv.office.core.tenants.TenantAlreadyExistsException;
import de.kaiserpfalzEdv.office.core.tenants.TenantDO;
import de.kaiserpfalzEdv.office.core.tenants.TenantRepository;
import de.kaiserpfalzEdv.office.core.tenants.TenantService;
import de.kaiserpfalzEdv.office.core.tenants.TenantServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.UUID;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @version 0.1.0
 * @since 0.1.0
 */
@Test
public class TenantServiceTest extends CommonTestBase {
    private static final Logger LOG = LoggerFactory.getLogger(TenantServiceTest.class);
    
    private static final UUID TENANT_ID = UUID.fromString("4a03e32b-8041-43aa-9075-17a5d785ac65");
    private static final TenantDO TENANT = new TenantDO(TENANT_ID, "1", "Test Tenant");
    
    
    private TenantService service;
    private TenantRepository repositoryMock;
    

    public TenantServiceTest() {
        super(TenantServiceTest.class, LOG);
    }
    
    
    public void testCreateTenant() throws TenantAlreadyExistsException {
        logMethod("create-tenant-valid", "Creating a valid tenant");
        
        when(repositoryMock.save(TENANT)).thenReturn(TENANT);
        
        service.create(TENANT);
        
        verify(repositoryMock).save(any(TenantDO.class));
    }
    
    
    @Test(expectedExceptions = TenantAlreadyExistsException.class)
    public void testCreateInvalidTenant() throws TenantAlreadyExistsException {
        logMethod("create-tenant-doublet", "Creating an invalid tenant (doublet in database)");
        
        when(repositoryMock.save(any(TenantDO.class))).thenThrow(new DataIntegrityViolationException("Already exists!"));
        
        service.create(TENANT);
        
        verify(repositoryMock).save(any(TenantDO.class));
    }
    

    @BeforeMethod
    protected void setupService() {
        repositoryMock = mock(TenantRepository.class, "repository");

        service = new TenantServiceImpl(repositoryMock);
    }
}
