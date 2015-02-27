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

package de.kaiserpfalzEdv.office.contacts.geodb.impl.test;

import de.kaiserpfalzEdv.commons.test.SpringTestNGBase;
import de.kaiserpfalzEdv.office.contacts.geodb.PostCode;
import de.kaiserpfalzEdv.office.contacts.geodb.PostCodeProviderLoader;
import de.kaiserpfalzEdv.office.contacts.geodb.PostCodeQueryBuilder;
import de.kaiserpfalzEdv.office.contacts.geodb.spi.InvalidQueryException;
import de.kaiserpfalzEdv.office.contacts.geodb.spi.NoSuchPostCodeException;
import de.kaiserpfalzEdv.office.contacts.geodb.spi.PostCodeProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.util.ServiceLoader;

import static org.testng.Assert.assertEquals;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 26.02.15 07:47
 */
@Test
@ContextConfiguration("/beans.xml")
public class PostCodeProviderTest extends SpringTestNGBase {
    private static final Logger LOG = LoggerFactory.getLogger(PostCodeProviderTest.class);

    @Inject
    private PostCodeProviderLoader service;


    public PostCodeProviderTest() {
        super(PostCodeProviderTest.class, LOG);
    }


    public void testServiceLocator() {
        logMethod("service-loader", "Checking if there is an implementation found by ServiceLoader");

        ServiceLoader<PostCodeProvider> providers = ServiceLoader.load(PostCodeProvider.class);

        providers.forEach(p -> LOG.info("PostCodeProvider found: {}", p));

        Assert.assertTrue(providers.iterator().hasNext(), "There is no service found!");
    }


    public void testProviderFactory() throws InvalidQueryException, NoSuchPostCodeException {
        logMethod("provider-factory", "Checking a working provider factory.");

        PostCodeProvider result = service.getInstance();

        LOG.info("Working with post code provider: {}", result.getDisplayName());

        PostCode code = result.findOne(new PostCodeQueryBuilder().withCity("Oebisfelde").build());

        LOG.info("PostCode found: {}", code);

        assertEquals(code.getDisplayNumber(), "39646", "Wrong post code for Oebisfelde!");
    }
}
