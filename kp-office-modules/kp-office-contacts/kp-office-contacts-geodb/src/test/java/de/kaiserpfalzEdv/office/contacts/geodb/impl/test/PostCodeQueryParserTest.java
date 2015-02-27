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

import com.mysema.query.types.Predicate;
import de.kaiserpfalzEdv.commons.test.CommonTestBase;
import de.kaiserpfalzEdv.office.contacts.geodb.PostCodeQueryBuilder;
import de.kaiserpfalzEdv.office.contacts.geodb.impl.PostCodeQueryParser;
import de.kaiserpfalzEdv.office.contacts.geodb.spi.InvalidQueryException;
import de.kaiserpfalzEdv.office.contacts.geodb.spi.PostCodeQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 25.02.15 23:45
 */
@Test
public class PostCodeQueryParserTest extends CommonTestBase {
    private static final Logger LOG = LoggerFactory.getLogger(PostCodeQueryParserTest.class);

    private PostCodeQueryParser service;

    public PostCodeQueryParserTest() {
        super(PostCodeQueryParserTest.class, LOG);
    }

    @Test(
            expectedExceptions = InvalidQueryException.class,
            expectedExceptionsMessageRegExp = "Invalid query to retrieve a post code"
    )
    public void checkNull() throws InvalidQueryException {
        logMethod("null-query", "Checks calling the parser with NULL ...");

        PostCodeQueryParser.withQuery(null);
    }


    public void checkCityQuery() {
        logMethod("city-query", "Checking a query with a city ...");

        Predicate result = PostCodeQueryParser.withCity("Magdeburg");

        assertEquals(result.toString(), "startsWith(kPOInternalPostCodeImpl.city,Magdeburg)");
    }


    public void checkParameterCityQuery() throws InvalidQueryException {
        logMethod("parameter-single-query", "Checking a query with parameter ...");

        PostCodeQuery query = new PostCodeQueryBuilder().withCity("Magdeburg").build();

        Predicate result = PostCodeQueryParser.withQuery(query);

        assertEquals(result.toString(), "startsWith(kPOInternalPostCodeImpl.city,Magdeburg)");
    }


    public void checkParameterMultipleQuery() throws InvalidQueryException {
        logMethod("parameter-multiple-query", "Checking a query with parameter ...");

        PostCodeQuery query = new PostCodeQueryBuilder().withCity("Magdeburg").withId(5).build();

        Predicate result = PostCodeQueryParser.withQuery(query);

        assertEquals(result.toString(), "startsWith(kPOInternalPostCodeImpl.city,Magdeburg) && kPOInternalPostCodeImpl.id = 5");
    }


    @BeforeMethod
    protected void setUp() {
        service = new PostCodeQueryParser();
    }
}
