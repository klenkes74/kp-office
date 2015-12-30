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

import de.kaiserpfalzedv.office.common.data.BuilderException;
import de.kaiserpfalzedv.office.common.data.Pageable;
import de.kaiserpfalzedv.office.common.data.PagedListable;
import org.apache.commons.lang3.builder.Builder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 30.12.15 14:16
 */
public class PagedListBuilder<T> implements Builder<PagedListable<T>> {
    private List<T>  data;
    private Pageable pageable;

    @Override
    public PagedListable<T> build() {
        setDefaultData();
        validate();

        return new PagedListImpl<>(data, pageable);
    }

    private void setDefaultData() {
        if (pageable == null && data != null) {
            pageable = new PageImpl(0, data.size(), 1, data.size());
        }
    }

    private void validate() {
        ArrayList<String> failures = new ArrayList<>(2);

        if (data == null) failures.add("No data given for data page!");
        if (pageable == null) failures.add("No page definition given for data page!");

        if (pageable != null && data != null) {
            if (pageable.getSize() < data.size()) failures.add("The data set is bigger than the page size!");
            if (data.size() + pageable.getSize() * pageable.getPage() > pageable.getTotalCount())
                failures.add("The data set exceeds the total count of the base data set!");
        }


        if (failures.size() > 0)
            throw new BuilderException(PagedListable.class, failures.toArray(new String[1]));
    }


    public PagedListBuilder<T> withData(List<T> data) {
        this.data = data;
        return this;
    }

    public PagedListBuilder<T> withPageable(Pageable pageable) {
        this.pageable = pageable;
        return this;
    }
}
