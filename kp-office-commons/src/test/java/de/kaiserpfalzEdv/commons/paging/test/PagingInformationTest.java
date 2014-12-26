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

package de.kaiserpfalzEdv.commons.paging.test;

import de.kaiserpfalzEdv.commons.paging.PagingInformation;
import de.kaiserpfalzEdv.commons.paging.PagingRequest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
@Test
public class PagingInformationTest {
    private static final long DEFAULT_START = 10L;
    private static final long DEFAULT_TOTAL = 30L;
    private static final long DEFAULT_SIZE = 10L;

    private PagingInformation service;


    public void checkValidStart() {
        service.setStart(DEFAULT_SIZE * 2);

        assertEquals(service.getStart(), DEFAULT_SIZE * 2);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void checkNegativeStart() {
        service.setStart(-2);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void checkNotMatchingStart() {
        service.setStart(4);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void checkTooBigStart() {
        service.setStart(DEFAULT_TOTAL + DEFAULT_SIZE);
    }


    public void checkValidPageSize() {
        service.setPageSize(DEFAULT_SIZE / 2);

        assertEquals(service.getPageSize(), DEFAULT_SIZE / 2);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void checkNegativePageSize() {
        service.setPageSize(-2);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void checkNotMatchingPageSize() {
        service.setPageSize(8);
    }


    public void checkValidTotal() {
        service.setTotal(DEFAULT_TOTAL * 3);

        assertEquals(service.getTotal(), DEFAULT_TOTAL * 3);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void checkTooSmallTotal() {
        service.setTotal(8L);
    }


    public void checkPageElementCount() {
        assertEquals(service.getPageElementsCount(), 10L);
    }

    public void checkPageElementCountLastPage() {
        service.setTotal(DEFAULT_TOTAL - 2);
        service.setStart(DEFAULT_TOTAL - DEFAULT_SIZE);

        assertEquals(service.getPageElementsCount(), 8L);
    }


    public void checkCurrentPageNumber() {
        assertEquals(service.getPage(), 1L);
    }

    public void checkTotalPageNumber() {
        assertEquals(service.getTotalPages(), 3L);
    }


    public void checkHasPreviousPage() {
        assertTrue(service.hasPreviousPage());
    }

    public void checkHasNoPreviousPage() {
        service.setStart(0L);

        assertFalse(service.hasPreviousPage());
    }


    public void checkHasNextPage() {
        assertTrue(service.hasNextPage());
    }

    public void checkHasNoNextPage() {
        service.setStart(20L);

        assertFalse(service.hasNextPage());
    }


    public void checkCurrentPage() {
        assertEquals(service.getCurrentPage(), new PagingRequest(DEFAULT_START, DEFAULT_SIZE));
    }


    public void checkGetFirstPage() {
        assertEquals(service.getFirstPage(), new PagingRequest(0L, DEFAULT_SIZE));
    }

    public void checkGetPreviousPage() {
        assertEquals(service.getPreviousPage(), new PagingRequest(DEFAULT_START - DEFAULT_SIZE, DEFAULT_SIZE));
    }

    public void checkNoPreviousPage() {
        service.setStart(0L);

        assertEquals(service.getPreviousPage(), service.getCurrentPage());
    }

    public void checkGetNextPage() {
        assertEquals(service.getNextPage(), new PagingRequest(DEFAULT_START + DEFAULT_SIZE, DEFAULT_SIZE));
    }

    public void checkGetNoNextPage() {
        service.setStart(service.getLastPage().getStart());

        assertEquals(service.getNextPage(), service.getCurrentPage());
    }


    public void checkGetLastPage() {
        service.setTotal(78L);

        assertEquals(service.getLastPage(), new PagingRequest(70L, DEFAULT_SIZE));
    }

    public void checkGetLastPageWouldBeEmpty() {
        assertEquals(service.getLastPage(), new PagingRequest(20L, DEFAULT_SIZE));
    }


    @BeforeMethod
    protected void createDefaultPagingInformation() {
        service = new PagingInformation(DEFAULT_START, DEFAULT_SIZE, DEFAULT_TOTAL);
    }
}
