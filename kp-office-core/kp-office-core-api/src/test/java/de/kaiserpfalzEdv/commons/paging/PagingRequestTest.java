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

package de.kaiserpfalzEdv.commons.paging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
@Test
public class PagingRequestTest {
    private static final Logger LOG = LoggerFactory.getLogger(PagingRequestTest.class);

    private static final long DEFAULT_START = 20L;
    private static final long DEFAULT_SIZE = 10L;


    private PagingRequest service;


    @SuppressWarnings("deprecation")
    public void checkValidStart() {
        service.setStart(DEFAULT_SIZE * 3);

        assertEquals(service.getStart(), DEFAULT_SIZE * 3);
    }

    @SuppressWarnings("deprecation")
    @Test(
            expectedExceptions = IllegalArgumentException.class
    )
    public void checkNegativeStart() {
        service.setStart(-2);
    }

    @SuppressWarnings("deprecation")
    @Test(
            expectedExceptions = IllegalArgumentException.class
    )
    public void checkNotMatchingStart() {
        service.setStart(4);
    }


    @SuppressWarnings("deprecation")
    public void checkValidPageSize() {
        service.setPageSize(DEFAULT_SIZE / 2);

        assertEquals(service.getPageSize(), DEFAULT_SIZE / 2);
    }

    @SuppressWarnings("deprecation")
    @Test(
            expectedExceptions = IllegalArgumentException.class
    )
    public void checkNegativePageSize() {
        service.setPageSize(-2);
    }

    @SuppressWarnings("deprecation")
    @Test(
            expectedExceptions = IllegalArgumentException.class
    )
    public void checkNotMatchingPageSize() {
        service.setPageSize(8);
    }


    public void checkValidPageSizeChange() {
        service.changePageSize(8);

        assertEquals(service.getStart(), DEFAULT_START - (DEFAULT_START % 8));
        assertEquals(service.getPageSize(), 8);
    }

    @Test(
            expectedExceptions = IllegalArgumentException.class
    )
    public void checkNegativePageSizeChange() {
        service.changePageSize(-2);
    }


    @BeforeMethod
    protected void createDefaultPagingRequest() {
        service = new PagingRequest(DEFAULT_START, DEFAULT_SIZE);
    }
}
