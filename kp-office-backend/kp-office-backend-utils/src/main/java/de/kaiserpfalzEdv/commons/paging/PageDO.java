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

package de.kaiserpfalzEdv.commons.paging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @version 0.1.0
 * @since 0.1.0
 */
public class PageDO<T,I extends T> implements Page<T> {
    private static final Logger LOG = LoggerFactory.getLogger(PageDO.class);

    private org.springframework.data.domain.Page<I> page;


    public PageDO(org.springframework.data.domain.Page<I> page) {
        this.page = page;
    }


    public org.springframework.data.domain.Page<I> getPage() {
        return page;
    }


    @Override
    public int getTotalPages() {
        return page.getTotalPages();
    }

    @Override
    public long getTotalElements() {
        return page.getTotalElements();
    }

    @Override
    public int getSize() {
        return page.getSize();
    }

    @Override
    public Iterator<T> iterator() {
        return getContent().iterator();
    }

    @Override
    public Pageable previousPageable() {
        return new PageableDO(page.previousPageable());
    }

    @Override
    public boolean hasContent() {
        return page.hasContent();
    }

    @Override
    public int getNumber() {
        return page.getNumber();
    }

    @Override
    public Sort getSort() {
        return new SortDO(page.getSort());
    }

    @Override
    public Spliterator<T> spliterator() {
        return getContent().spliterator();
    }

    @Override
    public boolean hasNext() {
        return page.hasNext();
    }

    @Override
    public Pageable nextPageable() {
        return new PageableDO(page.nextPageable());
    }

    @Override
    public int getNumberOfElements() {
        return page.getNumberOfElements();
    }

    @Override
    public List<T> getContent() {
        ArrayList<T> result = new ArrayList<>(page.getSize());
        
        page.getContent().forEach(t -> result.add(t));
        
        return result;
    }

    @Override
    public boolean isLast() {
        return page.isLast();
    }

    @Override
    public boolean hasPrevious() {
        return page.hasPrevious();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        page.forEach(action);
    }

    @Override
    public boolean isFirst() {
        return page.isFirst();
    }
}
