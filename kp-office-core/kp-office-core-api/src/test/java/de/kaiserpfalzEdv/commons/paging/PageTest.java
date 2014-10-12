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

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEqualsNoOrder;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
@Test
public class PageTest {
    private static final long DEFAULT_START = 0L;
    private static final long DEFAULT_TOTAL = 5L;
    private static final List<Integer> DEFAULT_DATA = new ArrayList<>((int) DEFAULT_TOTAL);

    static {
        for (int i = 0; i < 5; i++) {
            DEFAULT_DATA.add(i);
        }
    }

    private static final long DEFAULT_PAGESIZE = 10L;
    private Page<Integer> service;


    @Test(expectedExceptions = IllegalArgumentException.class)
    public void checkInvalidNumberOfPageElements() {
        new Page<>(DEFAULT_START, 100L, 30L, DEFAULT_DATA);
    }


    public void checkAllData() {
        assertEqualsNoOrder(service.getData().toArray(), DEFAULT_DATA.toArray());
    }


    public void checkSettingData() {
        List<Integer> newData = new ArrayList<>((int) DEFAULT_TOTAL);
        for (int i = 10; i < 15; i++) {
            newData.add(i);
        }

        service.setData(newData);

        assertEqualsNoOrder(service.getData().toArray(), newData.toArray());
    }


    public void checkSettingTooLongData() {
        List<Integer> newData = new ArrayList<>((int) DEFAULT_TOTAL);
        for (int i = 10; i < 16; i++) {
            newData.add(i);
        }
        service.setData(newData);
        newData.remove(5);

        assertEqualsNoOrder(service.getData().toArray(), newData.toArray());
    }


    @BeforeMethod
    protected void createDefaultPage() {
        service = new Page<>(DEFAULT_START, DEFAULT_TOTAL, DEFAULT_PAGESIZE, DEFAULT_DATA);
    }
}
