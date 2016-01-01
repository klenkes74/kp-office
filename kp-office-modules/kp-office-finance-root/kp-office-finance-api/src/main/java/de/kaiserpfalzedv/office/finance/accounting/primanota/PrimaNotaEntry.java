/*
 * Copyright 2016 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzedv.office.finance.accounting.primanota;

import de.kaiserpfalzedv.office.finance.accounting.BasePostingRecord;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 27.12.15 19:51
 */
public interface PrimaNotaEntry extends BasePostingRecord {
    /**
     * @return The PN number.
     */
    long getPN();

    /**
     * @return TRUE if this entry is a STORNO to another prima nota entry.
     */
    boolean isCancelationEntry();

    /**
     * @return The canceled entry.
     */
    PrimaNotaEntry getCanceledEntry() throws PrimanotaEntryNotCanceledException;
}
