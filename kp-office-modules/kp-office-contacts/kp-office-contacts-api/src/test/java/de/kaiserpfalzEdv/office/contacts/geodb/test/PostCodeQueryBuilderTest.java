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

package de.kaiserpfalzEdv.office.contacts.geodb.test;

import de.kaiserpfalzEdv.commons.test.CommonTestBase;
import de.kaiserpfalzEdv.office.contacts.geodb.PostCodeQueryBuilder;
import de.kaiserpfalzEdv.office.contacts.geodb.spi.PostCodeQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 25.02.15 22:59
 */
@Test
public class PostCodeQueryBuilderTest extends CommonTestBase {
    private static final Logger LOG = LoggerFactory.getLogger(PostCodeQueryBuilderTest.class);


    private PostCodeQueryBuilder service;


    public PostCodeQueryBuilderTest() {
        super(PostCodeQueryBuilderTest.class, LOG);
    }


    public void checkCityQuery() {
        logMethod("city-query", "Checks a query with only one city ...");

        PostCodeQuery result = service.withCity("Magdeburg").build();
        PostCodeQuery.QueryParameter parameter = result.getParameters().iterator().next();

        assertEquals(parameter.getKey(), "city");
        assertEquals(parameter.getValue(), "Magdeburg");
    }


    @BeforeTest
    protected void setUp() {
        this.service = new PostCodeQueryBuilder();
    }
}
