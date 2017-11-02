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

package de.kaiserpfalzedv.office.finance.accounting.api;

import de.kaiserpfalzedv.commons.api.data.PagedListable;

/**
 * A paged list of posting records. Every paged list of any subtype of posting records may be use this as base
 * interface.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 0.3.0
 * @since 2015-12-27
 */
public interface PagedPostingRecordList<T extends BasePostingRecord> extends PagedListable<T> {
    /**
     * @return The period this paged list is generated for.
     */
    FiscalPeriod getPeriod();
}
