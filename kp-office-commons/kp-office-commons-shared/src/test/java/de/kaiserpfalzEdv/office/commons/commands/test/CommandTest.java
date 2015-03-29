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

package de.kaiserpfalzEdv.office.commons.commands.test;

import de.kaiserpfalzEdv.commons.test.CommonTestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 01.03.15 13:47
 */
@Test
public class CommandTest extends CommonTestBase {
    private static final Logger LOG = LoggerFactory.getLogger(CommandTest.class);

    private TestCommandExecutor service;


    public CommandTest() {
        super(CommandTest.class, LOG);
    }


    public void testTestCommand() {
        TestCommand data = new TestCommandImpl("test command");

        data.execute(service);
    }


    public void testSecondCommand() {
        TestCommand data = new SecondCommandImpl("second command");

        data.execute(service);
    }


    @BeforeMethod
    protected void setUpService() {
        service = new TestCommandExecutorImpl();
    }
}
