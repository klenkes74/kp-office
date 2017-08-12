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

package de.kaiserpfalzedv.office.common.api.data;

import java.io.Serializable;
import java.util.List;

/**
 * A base class for pageable result sets.
 *
 * It contains the page definition ad the list entries.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 0.3.0
 * @since 2015-12-27
 */
public interface PagedListable<T> extends Serializable {
    /**
     * @return The page definition of this result page.
     */
    Pageable getPage();

    /**
     * @return The data contained in this list.
     */
    List<T> getEntries();
}
