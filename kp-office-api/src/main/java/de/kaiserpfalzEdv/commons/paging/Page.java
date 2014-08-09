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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public class Page<T> {
    private long start = 0;
    private long total = 0;
    private long pageSize = 0;

    private final ArrayList<T> data = new ArrayList<>();


    public Page(final long start, final long total, final long pageSize, final List<T> data) {
        setTotal(total);
        setPageSize(pageSize);
        setStart(start);

        setData(data);
    }


    public long getStart() {
        return start;
    }

    public void setStart(final long start) {
        checkArgument(start % pageSize == 0,
                "Sorry, the start does not match the pageSize. start has to be a multiple of pageSize!");
        checkArgument(start <= total, "Sorry, the start has to be less than the total");

        this.start = start;
    }


    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(final long pageSize) {
        checkArgument(start % pageSize == 0,
                "Sorry, the start does not match the pageSize. start has to be a multiple of pageSize!");

        this.pageSize = pageSize;
    }


    public long getPage() {
        return (start - (start%pageSize)) / pageSize;
    }


    public long getTotal() {
        return total;
    }

    public void setTotal(final long total) {
        checkArgument(start <= total, "Sorry, the start has to be less than the total");

        this.total = total;
    }


    public List<T> getData() {
        return Collections.unmodifiableList(data);
    }

    public void setData(Collection<T> data) {
        this.data.clear();

        if (data != null) {
            this.data.addAll(data);
        }
    }
}
