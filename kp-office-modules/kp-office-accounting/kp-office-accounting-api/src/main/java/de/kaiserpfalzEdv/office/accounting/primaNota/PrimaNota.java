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

package de.kaiserpfalzEdv.office.accounting.primaNota;

import de.kaiserpfalzEdv.office.accounting.postingRecord.PostingRecord;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 18.02.15 20:02
 */
public interface Primanota extends PrimaNotaInfo {
    /**
     * @return All entries of this primaNota.
     */
    List<? extends PostingRecord> getEntries();

    /**
     * @param entry The primaNota entry to add to this primaNota.
     */
    void addEntry(@NotNull PostingRecord entry);
}
