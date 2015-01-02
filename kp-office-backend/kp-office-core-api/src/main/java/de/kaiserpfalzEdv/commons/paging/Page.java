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

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @version 0.1.0
 * @since 0.1.0
 */
public interface Page<T> extends Serializable {
    int getTotalPages();

    long getTotalElements();

    int getSize();

    Iterator<T> iterator();

    Pageable previousPageable();

    boolean hasContent();

    int getNumber();

    Sort getSort();

    Spliterator<T> spliterator();

    boolean hasNext();

    Pageable nextPageable();

    int getNumberOfElements();

    List<T> getContent();

    boolean isLast();

    boolean hasPrevious();

    void forEach(Consumer<? super T> action);

    boolean isFirst();
}
