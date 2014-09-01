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

package de.kaiserpfalzEdv.commons.jndi.test;

import de.kaiserpfalzEdv.commons.jndi.SimpleContextFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.File;

import static org.testng.Assert.assertEquals;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
@Test
public class SimpleContextFactoryTest  {
    private static final Logger LOG = LoggerFactory.getLogger(SimpleContextFactoryTest.class);

    public void checkFileOnlyConfiguration() throws NamingException {
        System.getProperties().remove(SimpleContextFactory.CONFIG_FILE_NAME_PROPERTY);

        InitialContext ic = new InitialContext();

        String test = (String) ic.lookup("test");

        assertEquals(test, "teststring");
    }



    public void checkConfiguredConfiguration() throws NamingException {
        System.setProperty(SimpleContextFactory.CONFIG_FILE_NAME_PROPERTY,
                new File(".").getAbsolutePath() + "./kp-office-commons/target/test-classes/config2");
        InitialContext ic = new InitialContext();

        String test = (String) ic.lookup("test");

        assertEquals(test, "otherteststring");
    }
}
