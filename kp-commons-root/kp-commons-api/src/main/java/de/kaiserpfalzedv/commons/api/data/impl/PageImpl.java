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

package de.kaiserpfalzedv.commons.api.data.impl;

import de.kaiserpfalzedv.commons.api.data.Pageable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Implementation of the pageable interface to contain paging data and some base functions for calculating pages ...
 *
 * @author klenkes
 * @version 2015Q1
 * @since 30.12.15 13:58
 */
public class PageImpl implements Pageable {
    private static final long serialVersionUID = -6816585501293408061L;


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
    public int hashCode() {
        return new HashCodeBuilder()
                .append(page)
                .append(size)
                .append(totalPages)
                .append(totalCount)
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        PageImpl rhs = (PageImpl) obj;
        return new EqualsBuilder()
                .append(this.getPage(), rhs.getPage())
                .append(this.getSize(), rhs.getSize())
                .append(this.getTotalPages(), rhs.getTotalPages())
                .append(this.getTotalCount(), rhs.getTotalCount())
                .isEquals();
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

    @Override
    public int getFirstResult() {
        return (int) (page * size);
    }

    @Override
    public int getMaxResults() {
        return (int) size;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append(System.identityHashCode(this))
                .append("page", page)
                .append("size", size)
                .append("totalPages", totalPages)
                .append("totalCount", totalCount)
                .toString();
    }
}
