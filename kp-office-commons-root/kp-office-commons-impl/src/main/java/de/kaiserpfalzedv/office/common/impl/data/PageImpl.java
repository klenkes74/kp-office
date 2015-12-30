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

package de.kaiserpfalzedv.office.common.impl.data;

import de.kaiserpfalzedv.office.common.data.Pageable;

/**
 * Implementation of the pageable interface to contain paging data and some base functions for calculating pages ...
 *
 * @author klenkes
 * @version 2015Q1
 * @since 30.12.15 13:58
 */
public class PageImpl implements Pageable {
    private long page;
    private long size;

    private long totalPages;
    private long totalCount;

    PageImpl(long page, long size, long totalPages, long totalCount) {
        this.page = page;
        this.size = size;
        this.totalPages = totalPages;
        this.totalCount = totalCount;
    }

    @Override
    public long getPage() {
        return page;
    }

    @Override
    public long getSize() {
        return size;
    }

    @Override
    public long getTotalCount() {
        return totalCount;
    }

    @Override
    public long getTotalPages() {
        return totalPages;
    }

    @Override
    public boolean hasNextPage() {
        return (totalPages - 1) > page;
    }

    @Override
    public boolean hasPrevPage() {
        return page != 0;
    }

    @Override
    public boolean isFirstPage() {
        return page == 0;
    }

    @Override
    public boolean isLastPage() {
        return page == (totalPages - 1);
    }

    @Override
    public Pageable getNextPage() {
        return isLastPage() ? this : new PageImpl(page + 1, size, totalPages, totalCount);
    }

    @Override
    public Pageable getPrevPage() {
        return isFirstPage() ? this : new PageImpl(page - 1, size, totalPages, totalCount);
    }

    @Override
    public Pageable getFirstPage() {
        return new PageImpl(0, size, totalPages, totalCount);
    }

    @Override
    public Pageable getLastPage() {
        return new PageImpl(totalPages - 1, size, totalPages, totalCount);
    }
}
