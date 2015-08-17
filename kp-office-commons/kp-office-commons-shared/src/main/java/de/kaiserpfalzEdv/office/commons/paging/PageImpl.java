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

import de.kaiserpfalzEdv.commons.jee.paging.Page;
import de.kaiserpfalzEdv.commons.jee.paging.Pageable;
import de.kaiserpfalzEdv.commons.jee.paging.Sort;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 16.08.15 09:46
 */
public class PageImpl<T> implements Page<T> {
    private static final long         serialVersionUID = -5029241771426240417L;
    private final        ArrayList<T> data             = new ArrayList<>();
    private Pageable page;
    private long     totalElements;


    public PageImpl(Pageable page, long totalElements, List<? extends T> data) {
        this.page = page;
        this.totalElements = totalElements;

        data.forEach(d -> this.data.add((T) d));
    }


    @Override
    public int getTotalPages() {
        return (int) (totalElements / page.getPageSize()) + ((totalElements % page.getPageSize() > 0) ? 1 : 0);
    }

    @Override
    public long getTotalElements() {
        return totalElements;
    }

    @Override
    public int getSize() {
        return data.size();
    }

    @Override
    public Iterator<T> iterator() {
        return data.iterator();
    }

    @Override
    public Pageable previousPageable() {
        return page.previousOrFirst();
    }

    @Override
    public boolean hasContent() {
        return !data.isEmpty();
    }

    @Override
    public int getNumber() {
        return page.getPageNumber();
    }

    @Override
    public Sort getSort() {
        return page.getSort();
    }

    @Override
    public Spliterator<T> spliterator() {
        return data.spliterator();
    }

    @Override
    public boolean hasNext() {
        return totalElements > page.getOffset() + page.getPageSize();
    }

    @Override
    public Pageable nextPageable() {
        return page.next();
    }

    @Override
    public int getNumberOfElements() {
        return data.size();
    }

    @Override
    public List<T> getContent() {
        return data;
    }

    @Override
    public boolean isLast() {
        return !hasNext();
    }

    @Override
    public boolean hasPrevious() {
        return page.getOffset() != 0;
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        data.forEach(action);
    }

    @Override
    public boolean isFirst() {
        return !hasPrevious();
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
                .append(this.page, rhs.page)
                .append(this.totalElements, rhs.totalElements)
                .append(this.data, rhs.data)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(page)
                .append(totalElements)
                .append(data)
                .toHashCode();
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("page", page)
                .append("totalElements", totalElements)
                .append("data(size)", data.size())
                .toString();
    }
}
