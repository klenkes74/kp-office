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

package de.kaiserpfalzedv.office.common.data.test;

import de.kaiserpfalzedv.office.common.BuilderException;
import de.kaiserpfalzedv.office.common.data.Pageable;
import de.kaiserpfalzedv.office.common.data.impl.PageImpl;
import de.kaiserpfalzedv.office.common.data.impl.PageableBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * This simple test implementation tests the basic pageable object. It is needed to check this thouroughly since it is
 * used throughout the system and really needs to be bug free.
 *
 * @author klenkes
 * @version 2015Q1
 * @since 30.12.15 14:30
 */
public class PageableBuilderTest {
    private static final long DEFAULT_PAGE        = 10;
    private static final long DEFAULT_SIZE        = 20;
    private static final long DEFAULT_TOTAL_PAGES = 20;
    private static final long DEFAULT_TOTAL_COUNT = 394;

    private PageImpl service;

    @Before
    public void setUp() throws Exception {
        service = new PageableBuilder()
                .withPage(DEFAULT_PAGE)
                .withSize(DEFAULT_SIZE)
                .withTotalCount(DEFAULT_TOTAL_COUNT)
                .withTotalPages(DEFAULT_TOTAL_PAGES)
                .build();
    }


    @Test
    public void testFullInformation() {
        assertEquals(DEFAULT_PAGE, service.getPage());
        assertEquals(DEFAULT_SIZE, service.getSize());
        assertEquals(DEFAULT_TOTAL_COUNT, service.getTotalCount());
        assertEquals(DEFAULT_TOTAL_PAGES, service.getTotalPages());
    }


    @Test(expected = BuilderException.class)
    public void testNegativePage() {
        new PageableBuilder()
                .withPage(-1)
                .withSize(DEFAULT_SIZE)
                .withTotalPages(DEFAULT_TOTAL_PAGES)
                .withTotalCount(DEFAULT_TOTAL_COUNT)
                .build();
    }

    @Test(expected = BuilderException.class)
    public void testDataSetToBig() {
        new PageableBuilder()
                .withPage(DEFAULT_TOTAL_PAGES)
                .withSize(DEFAULT_SIZE)
                .withTotalPages(DEFAULT_TOTAL_PAGES)
                .withTotalCount(DEFAULT_TOTAL_COUNT)
                .build();
    }

    @Test(expected = BuilderException.class)
    public void testNegativePageSize() {
        new PageableBuilder()
                .withPage(DEFAULT_PAGE)
                .withSize(-1)
                .withTotalPages(DEFAULT_TOTAL_PAGES)
                .withTotalCount(DEFAULT_TOTAL_COUNT)
                .build();
    }

    @Test(expected = BuilderException.class)
    public void testNegativePageCount() {
        new PageableBuilder()
                .withPage(DEFAULT_PAGE)
                .withSize(DEFAULT_SIZE)
                .withTotalPages(-1)
                .withTotalCount(DEFAULT_TOTAL_COUNT)
                .build();
    }

    @Test(expected = BuilderException.class)
    public void testNegativeTotalCount() {
        new PageableBuilder()
                .withPage(DEFAULT_PAGE)
                .withSize(DEFAULT_SIZE)
                .withTotalPages(DEFAULT_TOTAL_PAGES)
                .withTotalCount(-1)
                .build();
    }


    @Test
    public void testIsFirstPage() {
        assertFalse(service.isFirstPage());
    }

    @Test
    public void hasPreviousPage() {
        assertTrue(service.hasPrevPage());
    }

    @Test
    public void hasNextPage() {
        assertTrue(service.hasNextPage());
    }

    @Test
    public void isLastPage() {
        assertFalse(service.isLastPage());
    }


    @Test
    public void testFirstPage() {
        Pageable result = service.getFirstPage();

        assertEquals(0, result.getPage());
        assertTrue(result.isFirstPage());
        assertFalse(result.hasPrevPage());
    }

    @Test
    public void testPreviousPage() {
        Pageable result = service.getPrevPage();

        assertEquals(DEFAULT_PAGE - 1, result.getPage());
    }

    @Test
    public void testNextPage() {
        Pageable result = service.getNextPage();

        assertEquals(DEFAULT_PAGE + 1, result.getPage());
    }

    @Test
    public void testLastPage() {
        Pageable result = service.getLastPage();

        assertEquals(DEFAULT_TOTAL_PAGES - 1, result.getPage());
        assertTrue(result.isLastPage());
        assertFalse(result.hasNextPage());
    }
}