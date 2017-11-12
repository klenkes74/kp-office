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

package de.kaiserpfalzedv.commons.impl.data.query.test;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.commons.api.data.query.AttributePredicate;
import de.kaiserpfalzedv.commons.api.data.query.JoinPredicate;
import de.kaiserpfalzedv.commons.api.data.query.Predicate;
import de.kaiserpfalzedv.commons.impl.data.query.PredicateToQueryParser;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import static de.kaiserpfalzedv.commons.api.data.query.Predicate.Comparator.BIGGER_AS;
import static de.kaiserpfalzedv.commons.api.data.query.Predicate.Comparator.EQUALS;
import static de.kaiserpfalzedv.commons.api.data.query.Predicate.Comparator.LOWER_AS_OR_EQUALS;
import static de.kaiserpfalzedv.commons.api.data.query.Predicate.Comparator.NOT_EQUALS;
import static org.junit.Assert.assertEquals;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-11-09
 */
public class PredicateTest {
    private static final Logger LOG = LoggerFactory.getLogger(PredicateTest.class);

    @BeforeClass
    public static void setUpMDC() {
        MDC.put("test-class", "predicate-tester");
    }

    @AfterClass
    public static void tearDownMDC() {
        MDC.remove("test");
        MDC.remove("test-class");
    }

    @Test
    public void shouldReturnCorrectStringWhenGivenOnlyAnIntegerPredicate() {
        logMethod("attribute-integer", "Checking predicate with only a single integer attribute");

        Predicate cut = new AttributePredicate(Integer.class, "INT", EQUALS, 5L);

        String result = new PredicateToQueryParser().generateQuery(cut);
        LOG.trace("Result: {}", result);

        assertEquals("INT=:INT_EQUALS", result);
    }

    private void logMethod(@NotNull final String shortName, @NotNull final String message, Object... data) {
        MDC.put("test", shortName);

        LOG.info(message, data);
    }

    @Test
    public void shouldReturnCorrectStringWhenGivenOnlyAStringPredicate() {
        logMethod("attribute-string", "Checking predicate with only a single string attribute");

        Predicate cut = new AttributePredicate(String.class, "STRING", EQUALS, "abc");

        String result = new PredicateToQueryParser().generateQuery(cut);
        LOG.trace("Result: {}", result);

        assertEquals("STRING=:STRING_EQUALS", result);
    }

    @Test
    public void shouldReturnCorrectStringWhenGivenTwoAttributesWithAnd() {
        logMethod("concat-and", "Checking and predicate");

        Predicate cut = new AttributePredicate(String.class, "STRING", EQUALS, "abc")
                .and(new AttributePredicate(Integer.class, "INT", BIGGER_AS, 5L));

        String result = new PredicateToQueryParser().generateQuery(cut);
        LOG.trace("Result: {}", result);

        assertEquals("(STRING=:STRING_EQUALS) and (INT>:INT_BIGGER_AS)", result);
    }

    @Test
    public void shouldReturnCorrectStringWhenGivenTwoAttributesWithOr() {
        logMethod("concat-or", "Checking or predicate");

        Predicate cut = new AttributePredicate(String.class, "STRING", EQUALS, "abc")
                .or(new AttributePredicate(Integer.class, "INT", BIGGER_AS, 5L));

        String result = new PredicateToQueryParser().generateQuery(cut);
        LOG.trace("Result: {}", result);

        assertEquals("(STRING=:STRING_EQUALS) or (INT>:INT_BIGGER_AS)", result);
    }

    @Test
    public void shouldReturnCorrectStringWhenGivenNestedPredicates() {
        logMethod("concat-nested", "Checking nested predicates");

        Predicate cut = new AttributePredicate(String.class, "STRING", EQUALS, "abc")
                .or(
                        new AttributePredicate(Integer.class, "INT", BIGGER_AS, 5L)
                                .and(new AttributePredicate(Integer.class, "INT", LOWER_AS_OR_EQUALS, 10L))
                );

        String result = new PredicateToQueryParser().generateQuery(cut);
        LOG.trace("Result: {}", result);

        assertEquals("(STRING=:STRING_EQUALS) or ((INT>:INT_BIGGER_AS) and (INT<=:INT_LOWER_AS_OR_EQUALS))", result);
    }

    @Test
    public void shouldReturnCorrectStringWhenGivenConcatenatedPredicates() {
        logMethod("concat-concat", "Checking multiple concatenated predicates");

        Predicate cut = new AttributePredicate(String.class, "STRING", EQUALS, "abc")
                .or(
                        new AttributePredicate(Integer.class, "INT", BIGGER_AS, 5L)
                ).and(
                        new AttributePredicate(Integer.class, "INT", LOWER_AS_OR_EQUALS, 10L)
                );

        String result = new PredicateToQueryParser().generateQuery(cut);
        LOG.trace("Result: {}", result);

        assertEquals("((STRING=:STRING_EQUALS) or (INT>:INT_BIGGER_AS)) and (INT<=:INT_LOWER_AS_OR_EQUALS)", result);
    }


    @Test
    public void shouldAcceptTwoDifferentClassesWhenGivenAJoinPredicate() {
        logMethod("join-predicate", "Checking joining different classes");

        Predicate<TypeA> cut = new AttributePredicate<TypeA, String>(String.class, "STRING", EQUALS, "abc")
                .or(
                        new AttributePredicate<>(Long.class, "INT", BIGGER_AS, 5L)
                ).and(
                        new AttributePredicate<>(Integer.class, "INT", LOWER_AS_OR_EQUALS, 10L)
                ).and(
                        new JoinPredicate<TypeA, TypeB>("Second", new AttributePredicate<TypeB, String>(String.class, "linked.second", NOT_EQUALS, "another"))
                );

        String result = new PredicateToQueryParser<TypeA>().generateQuery(cut);
        LOG.trace("Result: {}", result);

        assertEquals("(((STRING=:STRING_EQUALS) or (INT>:INT_BIGGER_AS)) and (INT<=:INT_LOWER_AS_OR_EQUALS)) and (linked.second<>:linked.second_NOT_EQUALS)", result);
    }

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {
        MDC.remove("test");
    }
}

class TypeA implements Serializable {}

class TypeB implements Serializable {}
