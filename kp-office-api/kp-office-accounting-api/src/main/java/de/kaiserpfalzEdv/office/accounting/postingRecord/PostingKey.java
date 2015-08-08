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

package de.kaiserpfalzEdv.office.accounting.postingRecord;

import de.kaiserpfalzEdv.office.accounting.automation.FunctionKey;
import de.kaiserpfalzEdv.office.accounting.tax.TaxKey;

/**
 * The posting key is a combined information about the
 * @author klenkes
 * @version 2015Q1
 * @since 07.08.15 18:22
 */
public interface PostingKey {
    /**
     * @return The displayed key. A number of up to 3 digits in length.
     */
    String getKey();

    /**
     * @return The tax information for this posting key.
     */
    TaxKey getTaxKey();

    /**
     * @return The automation information for this posting key.
     */
    FunctionKey getFunctionKey();
}
