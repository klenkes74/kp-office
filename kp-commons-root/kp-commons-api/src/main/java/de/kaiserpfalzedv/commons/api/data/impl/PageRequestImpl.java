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
public class PageRequestImpl implements Pageable {
    private static final long serialVersionUID = -6816585501293408061L;


    private long page;
    private long size;

    PageRequestImpl(long page, long size) {
        this.page = page;
        this.size = size;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(page)
                .append(size)
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
        PageRequestImpl rhs = (PageRequestImpl) obj;
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
        return 0;
    }

    @Override
    public long getTotalPages() {
        return 0;
    }

    @Override
    public boolean hasNextPage() {
        return false;
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
        return true;
    }

    @Override
    public Pageable getNextPage() {
        return this;
    }

    @Override
    public Pageable getPrevPage() {
        return this;
    }

    @Override
    public Pageable getFirstPage() {
        return this;
    }

    @Override
    public Pageable getLastPage() {
        return this;
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
                .toString();
    }
}
