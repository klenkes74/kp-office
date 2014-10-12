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
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public class PagingRequest implements Serializable {
    private long start;
    private long pageSize;


    public PagingRequest(final long start, final long pageSize) {
        this.start = start;
        this.pageSize = pageSize;
    }

    public PagingRequest(final PagingInformation information) {
        this.start = information.getStart();
        this.pageSize = information.getPageSize();
    }


    public long getStart() {
        return start;
    }

    /**
     * @param start The new start of the page.
     * @deprecated Only for JAX-B and JPA!
     */
    @Deprecated // Only for JAX-B and JPA!
    public void setStart(final long start) {
        checkArgument(start >= 0, "You have to give a positive start row! (%s is no valid start row)", start);
        checkArgument(start % pageSize == 0,
                "Sorry, the start does not match the pageSize. start has to be a multiple of pageSize!");

        this.start = start;
    }


    public long getPageSize() {
        return pageSize;
    }

    /**
     * @param pageSize The new page size of this request.
     * @deprecated Only for JAX-B and JPA!
     */
    @Deprecated // Only for JAX-B and JPA!
    public void setPageSize(final long pageSize) {
        checkArgument(pageSize > 0,
                "You have to give a positive page size with more than 0 entries per page! (%s is no valid page size)",
                pageSize);
        checkArgument(start % pageSize == 0,
                "Sorry, the start does not match the pageSize. start has to be a multiple of pageSize!");

        this.pageSize = pageSize;
    }


    /**
     * Changes the pagesize in a manner, that the current start row is still visible on the new page.
     *
     * @param pageSize The new page size of the request.
     */
    public void changePageSize(final long pageSize) {
        checkArgument(pageSize > 0,
                "You have to give a positive page size with more than 0 entries per page! (%s is no valid page size)",
                pageSize);

        start = start - (start % pageSize);
        this.pageSize = pageSize;
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
        PagingRequest rhs = (PagingRequest) obj;
        return new EqualsBuilder()
                .append(this.start, rhs.start)
                .append(this.pageSize, rhs.pageSize)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(start)
                .append(pageSize)
                .toHashCode();
    }


    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("start", start)
                .append("pageSize", pageSize)
                .build();
    }
}
