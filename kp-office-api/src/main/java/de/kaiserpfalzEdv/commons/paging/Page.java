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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * A page generic container with paging information.
 *
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public class Page<T> implements Serializable {
    private PagingInformation paging;

    private final ArrayList<T> data = new ArrayList<>();


    public Page(final long start, final long total, final long pageSize, final Collection<T> data) {
        paging = new PagingInformation(start, pageSize, total);

        checkArgument(paging.getPageElementsCount() <= data.size(),
                "The page would be invalid. This page needs %s elements, but only %s elements are provided.",
                paging.getPage(), data.size());

        setData(data);
    }


    /**
     * @return The paging information of this page.
     */
    public PagingInformation getPaging() {
        return paging;
    }


    /**
     * @param paging The new paging information.
     * @deprecated Only for JAX-B and JPA!
     */
    @Deprecated // Only for JAX-B and JPA!
    public void setPaging(final PagingInformation paging) {
        checkArgument(paging != null, "Can't set page paging to <null>!");

        this.paging = paging;
    }


    /**
     * @return An unmodifiable list of the data of this page.
     */
    public List<T> getData() {
        return Collections.unmodifiableList(data);
    }

    /**
     * Sets the data for the page.
     * Only the first {@link #getPaging()}.{@link PagingInformation#getPageSize()} elements will be copied into
     * this orig page.
     *
     * @param orig The orig to be copied into this page.
     */
    public void setData(Collection<T> orig) {
        clearDataPage();

        long maxElements = getMaximumDataPageElements();

        setDataToPage(orig, maxElements);
    }

    private void clearDataPage() {
        this.data.clear();
    }

    private long getMaximumDataPageElements() {
        return paging.getPageSize() < paging.getTotal() ? paging.getPageSize() : paging.getTotal();
    }

    private void setDataToPage(final Collection<T> orig, final long maxElements) {
        if (orig != null) {
            if (orig.size() <= maxElements) {
                this.data.addAll(orig);
            } else {
                //noinspection Convert2MethodRef
                orig.stream().limit(maxElements).forEach(t -> data.add(t));
            }
        }
    }


    public long getStart() {
        return paging.getStart();
    }

    public long getPageSize() {
        return paging.getPageSize();
    }

    public long getTotal() {
        return paging.getTotal();
    }

    public long getPage() {
        return paging.getPage();
    }

    public long getTotalPages() {
        return paging.getTotalPages();
    }

    public boolean hasPreviousPage() {
        return paging.hasPreviousPage();
    }

    public boolean hasNextPage() {
        return paging.hasNextPage();
    }

    public PagingRequest getCurrentPage() {
        return paging.getCurrentPage();
    }

    public PagingRequest getFirstPage() {
        return paging.getFirstPage();
    }

    public PagingRequest getPreviousPage() {
        return paging.getPreviousPage();
    }

    public PagingRequest getNextPage() {
        return paging.getNextPage();
    }

    public PagingRequest getLastPage() {
        return paging.getLastPage();
    }
}
