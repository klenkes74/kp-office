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

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @version 0.1.0
 * @since 0.1.0
 */
public class PageableDO implements Pageable {
    private org.springframework.data.domain.Pageable pageable;

    public PageableDO(org.springframework.data.domain.Pageable pageable) {
        this.pageable = pageable;
    }


    public org.springframework.data.domain.Pageable getPageable() {
        return pageable;
    }


    public int getPageNumber() {
        return pageable.getPageNumber();
    }

    @Override
    public boolean hasPrevious() {
        return pageable.hasPrevious();
    }

    @Override
    public int getPageSize() {
        return pageable.getPageSize();
    }

    @Override
    public Sort getSort() {
        return new SortDO(pageable.getSort());
    }

    @Override
    public int getOffset() {
        return pageable.getOffset();
    }

    @Override
    public Pageable first() {
        return new PageableDO(pageable.first());
    }

    @Override
    public Pageable previousOrFirst() {
        return new PageableDO(pageable.previousOrFirst());
    }

    @Override
    public Pageable next() {
        return new PageableDO(pageable.next());
    }
}
