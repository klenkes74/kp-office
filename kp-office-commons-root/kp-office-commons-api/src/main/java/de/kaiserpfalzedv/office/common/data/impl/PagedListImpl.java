/*
 * Copyright 2016 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzedv.office.common.data.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.kaiserpfalzedv.office.common.data.Pageable;
import de.kaiserpfalzedv.office.common.data.PagedListable;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 30.12.15 14:05
 */
public class PagedListImpl<T> implements PagedListable<T> {
    private static final long serialVersionUID = 5317263811195205630L;


    private final ArrayList<T> data = new ArrayList<>();
    private PageImpl paging;


    PagedListImpl(Collection<T> data, Pageable pageable) {
        if (data != null) this.data.addAll(data);

        paging = new PageableBuilder().withPaging(pageable).build();
    }


    @Override
    public Pageable getPage() {
        return paging;
    }

    @Override
    public List<T> getEntries() {
        return data;
    }
}
