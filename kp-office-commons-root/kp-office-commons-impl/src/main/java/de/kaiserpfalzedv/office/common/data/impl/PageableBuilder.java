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

import de.kaiserpfalzedv.office.common.BuilderException;
import de.kaiserpfalzedv.office.common.data.Pageable;
import org.apache.commons.lang3.builder.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Builds a page definition object.
 *
 * @author klenkes
 * @version 2015Q1
 * @since 30.12.15 14:08
 */
public class PageableBuilder implements Builder<Pageable> {
    private static final Logger LOG = LoggerFactory.getLogger(PageableBuilder.class);

    private static final int DEFAULT_PAGE_SIZE = 10;

    private long page;
    private long size;
    private long totalPages;
    private long totalCount;

    @Override
    public PageImpl build() {
        setDefaultValues();
        validate();

        return new PageImpl(page, size, totalPages, totalCount);
    }

    private void setDefaultValues() {
        if (size == 0) size = DEFAULT_PAGE_SIZE;
        if (totalPages == 0) totalPages = page + 1;
    }

    private void validate() {
        ArrayList<String> failures = new ArrayList<>(5);

        if (page < 0) failures.add("Page 0 is the first page!");
        if (page >= totalPages) failures.add("Page is bigger than the last page!");
        if (size <= 0) failures.add("Can't build a page with page size 0 or less!");
        if (totalPages < 0) failures.add("Can't build a page with no pages!");
        if (totalCount < 0) failures.add("A list can be empty but not contain less than 0 entries!");

        if (failures.size() > 0)
            throw new BuilderException(Pageable.class, failures.toArray(new String[1]));
    }


    public PageableBuilder withPaging(final Pageable pageable) {
        withPage(pageable.getPage());
        withSize(pageable.getSize());
        withTotalPages(pageable.getTotalPages());
        withTotalCount(pageable.getTotalCount());

        return this;
    }


    public PageableBuilder withPage(long page) {
        this.page = page;
        return this;
    }

    public PageableBuilder withSize(long size) {
        this.size = size;
        return this;
    }

    public PageableBuilder withTotalPages(long totalPages) {
        this.totalPages = totalPages;
        return this;
    }

    public PageableBuilder withTotalCount(long totalCount) {
        this.totalCount = totalCount;
        return this;
    }
}
