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

package de.kaiserpfalzedv.office.commons.jdni;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import de.kaiserpfalzedv.office.common.jndi.JndiWalker;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-29
 */
public class JndiWalkerTest {
    private static final Logger LOG = LoggerFactory.getLogger(JndiWalkerTest.class);

    private InitialContext ctx;
    private JndiWalker service;

    @Test
    public void checkStringLookup() throws NamingException {
        String result = (String) ctx.lookup("java:comp/env/test");

        assertEquals("Teststring", result);
    }

    @Test
    public void checkJndiWalk() throws NamingException {
        String result = service.walk(ctx, "java:comp/env");
        LOG.debug("Result: {}", result);

        assertEquals(" JNDI tree for 'java:comp/env': \n" +
                             "test (java.lang.String, not-supported, relative)\n" +
                             "dir1 (org.osjava.sj.memory.MemoryContext, not-supported, relative)\n" +
                             "    entry (java.lang.String, not-supported, relative)\n" +
                             "    dirC (org.osjava.sj.memory.MemoryContext, not-supported, relative)\n" +
                             "        entry (java.lang.String, not-supported, relative)\n" +
                             "        entry2 (java.lang.String, not-supported, relative)\n" +
                             "    dirB (org.osjava.sj.memory.MemoryContext, not-supported, relative)\n" +
                             "        entry (java.lang.String, not-supported, relative)", result);
    }


    @Before
    public void setupService() throws NamingException {
        service = new JndiWalker();

        ctx = new InitialContext();
    }
}
