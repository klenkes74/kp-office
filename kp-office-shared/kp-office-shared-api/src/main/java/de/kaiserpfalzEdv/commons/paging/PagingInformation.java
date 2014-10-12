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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * This is the paging information for the {@link de.kaiserpfalzEdv.commons.paging.Page}.
 *
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public class PagingInformation implements Serializable {
    private long start = 0L;
    private long total = 0L;
    private long pageSize = 10L;

    public PagingInformation(final long start, final long pageSize, final long total) {
        setTotal(total);
        setPageSize(pageSize);
        setStart(start);
    }


    public long getStart() {
        return start;
    }

    public void setStart(final long start) {
        checkArgument(start >= 0L, "You have to give a positive start row! (%s is no valid start row)", start);
        checkArgument(pageSize == 0L || start % pageSize == 0L,
                "Sorry, the start does not match the pageSize. start has to be a multiple of pageSize!");
        checkArgument(start <= total, "Sorry, the start has to be less than the total");

        this.start = start;
    }


    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(final long pageSize) {
        checkArgument(pageSize > 0L,
                "You have to give a positive page size with more than 0 entries per page! (%s is no valid page size)",
                pageSize);
        checkArgument(pageSize == 0L || start % pageSize == 0L,
                "Sorry, the start does not match the pageSize. start has to be a multiple of pageSize!");

        this.pageSize = pageSize;
    }


    public long getTotal() {
        return total;
    }

    public void setTotal(final long total) {
        checkArgument(start <= total, "Sorry, the start has to be less than the total");

        this.total = total;
    }

    public long getPageElementsCount() {
        return start + pageSize <= total ? pageSize : total - start;
    }


    public long getPage() {
        return (start - (start % pageSize)) / pageSize;
    }

    public long getTotalPages() {
        return total / pageSize + (total % pageSize != 0 ? 1 : 0);
    }


    public boolean hasPreviousPage() {
        return start > 0;
    }

    public boolean hasNextPage() {
        return start + pageSize < total;
    }

    public PagingRequest getCurrentPage() {
        return new PagingRequest(start, pageSize);
    }

    public PagingRequest getFirstPage() {
        return new PagingRequest(0, pageSize);
    }

    public PagingRequest getPreviousPage() {
        return new PagingRequest(start - pageSize > 0 ? (start - pageSize) : 0, pageSize);
    }

    public PagingRequest getNextPage() {
        return new PagingRequest(start + pageSize < total ? (start + pageSize) : start, pageSize);
    }

    public PagingRequest getLastPage() {
        long lastPageStart = total - (total % pageSize);

        if (lastPageStart == total)
            lastPageStart -= pageSize;

        return new PagingRequest(lastPageStart, pageSize);
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
        PagingInformation rhs = (PagingInformation) obj;
        return new EqualsBuilder()
                .append(this.start, rhs.start)
                .append(this.total, rhs.total)
                .append(this.pageSize, rhs.pageSize)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(start)
                .append(total)
                .append(pageSize)
                .toHashCode();
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("start", start)
                .append("total", total)
                .append("pageSize", pageSize)
                .toString();
    }
}
