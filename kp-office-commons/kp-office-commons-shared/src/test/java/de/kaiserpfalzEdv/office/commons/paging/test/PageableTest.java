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

package de.kaiserpfalzEdv.office.commons.paging.test;

import de.kaiserpfalzEdv.commons.test.CommonTestBase;
import de.kaiserpfalzEdv.office.commons.paging.PageImpl;
import de.kaiserpfalzEdv.office.commons.paging.PageableImpl;
import de.kaiserpfalzEdv.office.commons.paging.SortImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 16.08.15 19:27
 */
@Test
public class PageableTest extends CommonTestBase {
    private static final Logger LOG = LoggerFactory.getLogger(PageableTest.class);

    private static final int               OFFSET       = 20;
    private static final int               PAGE_SIZE    = 20;
    private static final SortImpl          DEFAULT_SORT = new SortImpl("test1", "test2");
    private static final ArrayList<String> DATA         = new ArrayList<>(60);

    static {
        for (int i = 1; i < 61; i++) {
            DATA.add("Test-Data Nr. " + i);
        }
    }


    private PageableImpl service;


    public PageableTest() {
        super(PageableTest.class, LOG);
    }


    public void checkCreatedPageable() {
        logMethod("base-pageable", "Checking the default PageableImpl ...");

        assertEquals(service.getOffset(), OFFSET, "Wrong data offset!");
        assertEquals(service.getPageSize(), PAGE_SIZE, "Wrong page size!");
        assertEquals(service.getSort(), DEFAULT_SORT, "Wrong sort order!");

        assertEquals(service.getPageNumber(), 1, "Wrong page number");
        assertTrue(service.hasPrevious(), "There should be a previous page");
        assertEquals(service.first(), service.previousOrFirst(), "The previous page should be the first page");

        assertEquals(service.next().getPageNumber(), 2, "Wrong page number for next page");
        assertEquals(service.next().first(), service.first(), "Next page should point to the same first page");
        assertEquals(service.next().previousOrFirst(), service, "Next page previos page should point to service");
    }


    public void checkPage() {
        logMethod("base-page", "Checking the created base page ...");

        PageImpl<String> test = new PageImpl<>(service, DATA.size(), DATA.subList(20, 40));
        LOG.trace("result: {}", test);

        assertEquals(test.getTotalElements(), DATA.size(), "Wrong number of total elements");
        assertEquals(test.getNumberOfElements(), service.getPageSize(), "Wrong number of elements");
        assertEquals(test.getNumber(), 1, "Wrong page number");
        assertEquals(test.getContent(), DATA.subList(20, 40), "Wrong content!");
    }


    @BeforeMethod
    protected void setupService() {
        service = new PageableImpl(OFFSET, PAGE_SIZE, DEFAULT_SORT);
    }
}
