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

package de.kaiserpfalzEdv.office.commons.paging;

import de.kaiserpfalzEdv.commons.jee.paging.Pageable;
import de.kaiserpfalzEdv.commons.jee.paging.Sort;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 16.08.15 10:02
 */
public class PageableImpl implements Pageable {
    private static final long serialVersionUID = -6523184341309396607L;


    private int offset;
    private int size;

    private Sort sort;


    public PageableImpl(final int offset, final int size, final Sort sort) {
        this(offset, size);

        this.sort = sort;
    }

    public PageableImpl(final int offset, final int size) {
        this.offset = offset;
        this.size = size;
    }


    @Override
    public int getPageNumber() {
        return offset / size;
    }

    @Override
    public boolean hasPrevious() {
        return offset != 0;
    }

    @Override
    public int getPageSize() {
        return size;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public int getOffset() {
        return offset;
    }

    @Override
    public Pageable first() {
        return new PageableImpl(0, size, sort);
    }

    @Override
    public Pageable previousOrFirst() {
        return new PageableImpl(Integer.max(0, offset - size), size, sort);
    }

    @Override
    public Pageable next() {
        return new PageableImpl(offset + size, size, sort);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof PageableImpl)) return false;

        PageableImpl pageable = (PageableImpl) o;

        return new EqualsBuilder()
                .append(getOffset(), pageable.getOffset())
                .append(size, pageable.size)
                .append(getSort(), pageable.getSort())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getOffset())
                .append(size)
                .append(getSort())
                .toHashCode();
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("offset", offset)
                .append("size", size)
                .append("sort", sort)
                .toString();
    }
}
