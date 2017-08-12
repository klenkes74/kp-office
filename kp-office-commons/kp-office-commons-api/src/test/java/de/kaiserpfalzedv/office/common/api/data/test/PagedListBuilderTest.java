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

package de.kaiserpfalzedv.office.common.api.data.test;

import java.util.ArrayList;

import de.kaiserpfalzedv.office.common.api.BuilderException;
import de.kaiserpfalzedv.office.common.api.data.Pageable;
import de.kaiserpfalzedv.office.common.api.data.PagedListable;
import de.kaiserpfalzedv.office.common.api.data.impl.PageableBuilder;
import de.kaiserpfalzedv.office.common.api.data.impl.PagedListBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 30.12.15 15:38
 */
public class PagedListBuilderTest {
    private static final ArrayList<String> DEFAULT_DATA = new ArrayList<>();
    private static final Pageable DEFAULT_PAGEABLE = new PageableBuilder()
            .withPage(0)
            .withSize(10)
            .withTotalCount(2)
            .withTotalPages(1)
            .build();

    private PagedListBuilder<String> service;

    @Before
    public void setUp() throws Exception {
        service = new PagedListBuilder<>();

        DEFAULT_DATA.clear();
        DEFAULT_DATA.add("Test 1");
        DEFAULT_DATA.add("Test 2");
    }


    @Test
    public void testFull() {
        PagedListable<String> result = service
                .withPageable(DEFAULT_PAGEABLE)
                .withData(DEFAULT_DATA)
                .build();

        assertEquals(0, result.getPage().getPage());
        assertEquals(10, result.getPage().getSize());
        assertEquals(1, result.getPage().getTotalPages());
        assertEquals(2, result.getPage().getTotalCount());

        assertEquals(2, result.getEntries().size());
    }


    @Test(expected = BuilderException.class)
    public void testNoData() {
        service
                .withPageable(DEFAULT_PAGEABLE)
                .build();
    }

    @Test
    public void testNoPageInformation() {
        PagedListable<String> result = service
                .withData(DEFAULT_DATA)
                .build();

        assertEquals(0, result.getPage().getPage());
        assertEquals(2, result.getPage().getSize());
        assertEquals(1, result.getPage().getTotalPages());
        assertEquals(2, result.getPage().getTotalCount());
    }

    @Test(expected = BuilderException.class)
    public void testTooMuchData() {
        service
                .withPageable(new PageableBuilder().withPaging(DEFAULT_PAGEABLE).withTotalCount(1).build())
                .withData(DEFAULT_DATA)
                .build();
    }
}