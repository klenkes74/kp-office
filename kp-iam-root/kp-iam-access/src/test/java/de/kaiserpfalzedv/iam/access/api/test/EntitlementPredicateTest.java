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

package de.kaiserpfalzedv.iam.access.api.test;

import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.commons.api.data.query.Predicate;
import de.kaiserpfalzedv.commons.api.data.query.QueryParameter;
import de.kaiserpfalzedv.commons.impl.data.query.PredicateToParameterParser;
import de.kaiserpfalzedv.commons.impl.data.query.PredicateToQueryParser;
import de.kaiserpfalzedv.iam.access.api.roles.Entitlement;
import de.kaiserpfalzedv.iam.access.api.roles.EntitlementPredicate;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-11-09
 */
public class EntitlementPredicateTest {
    private static final Logger LOG = LoggerFactory.getLogger(EntitlementPredicateTest.class);

    private static final UUID id = UUID.fromString("44444444-4444-4444-4444-444444444444");

    private PredicateToQueryParser<Entitlement> queryParser;
    private PredicateToParameterParser<Entitlement> parameterParser;

    private Predicate<Entitlement> cut;

    @BeforeClass
    public static void setUpMDC() {
        MDC.put("test-class", "entitlement-predicate");
    }

    @AfterClass
    public static void tearDownMDC() {
        MDC.remove("test");
        MDC.remove("test-class");
    }

    @Test
    public void shouldGenerateValidQuery() {
        logMethod("generate-query", "Generate the queries");

        String result = queryParser.generateQuery(cut);
        LOG.trace("Result: {}", result);

        assertEquals(
                "(((id=:id_EQUALS) and (displayName<>:displayName_NOT_EQUALS)) and (fullName<>:fullName_NOT_EQUALS)) and (descriptionKey=:descriptionKey_EQUALS)",
                result
        );
    }

    private void logMethod(@NotNull final String shortName, @NotNull final String message, Object... data) {
        MDC.put("test", shortName);

        LOG.info(message, data);
    }

    @Test
    public void shouldGenerateValidParameters() {
        logMethod("generate-parameter", "Generate the parameters list");

        List<QueryParameter> result = parameterParser.generateParameters(cut);
        LOG.trace("Result: {}", result);

        if (!result.contains(new QueryParameter("id_EQUALS", id)))
            fail("ID is missing");

        if (!result.contains(new QueryParameter("displayName_NOT_EQUALS", "abc")))
            fail("DISPLAYNAME is missing");

        if (!result.contains(new QueryParameter("fullName_NOT_EQUALS", "def")))
            fail("FULLNAME is missing");

        if (!result.contains(new QueryParameter("descriptionKey_EQUALS", "com.iam.testkey"))) {
            fail("DESCRIPTIONKEY is missing!");
        }
    }

    @Before
    public void setUp() {
        queryParser = new PredicateToQueryParser<>();
        parameterParser = new PredicateToParameterParser<>();

        cut = EntitlementPredicate.id().isEqualTo(id)
                                  .and(EntitlementPredicate.displayName().isNotEqualTo("abc"))
                                  .and(EntitlementPredicate.fullName().isNotEqualTo("def"))
                                  .and(EntitlementPredicate.descriptionKey().isEqualTo("com.iam.testkey"));
    }

    @After
    public void tearDown() {
        MDC.remove("test");
    }
}
