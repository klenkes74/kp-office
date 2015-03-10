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

package de.kaiserpfalzEdv.office.cli.test;

import de.kaiserpfalzEdv.commons.test.CommonTestBase;
import de.kaiserpfalzEdv.office.cli.executor.CliModuleRegistrar;
import de.kaiserpfalzEdv.office.cli.executor.events.ExecutionCommand;
import de.kaiserpfalzEdv.office.cli.executor.events.ShutdownCommand;
import de.kaiserpfalzEdv.office.cli.executor.impl.RegistrarImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 03.03.15 01:26
 */
@Test
public class RegistrarTest extends CommonTestBase {
    private static final Logger LOG = LoggerFactory.getLogger(RegistrarTest.class);


    private CliModuleRegistrar service;


    public RegistrarTest() {
        super(RegistrarTest.class, LOG);
    }


    public void checkInitialization() {
        logMethod("registrar-initialization", "Initializing a new registrar '" + service + "' ...");

        service.init();

        service.execute(new ExecutionCommand());

        service.execute(new ShutdownCommand());
    }


    @BeforeMethod
    protected void setUpService() {
        service = new RegistrarImpl("/beans-test.xml");

    }
}
