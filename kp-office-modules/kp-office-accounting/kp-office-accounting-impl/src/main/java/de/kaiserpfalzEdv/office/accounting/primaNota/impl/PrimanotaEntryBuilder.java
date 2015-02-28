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

package de.kaiserpfalzEdv.office.accounting.primaNota.impl;

import de.kaiserpfalzEdv.office.accounting.postingRecord.PostingRecord;
import org.apache.commons.lang3.builder.Builder;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 27.02.15 11:28
 */
public class PrimaNotaEntryBuilder implements Builder<PrimaNotaEntryImpl> {

    private PostingRecord postingRecord;

    @Override
    public PrimaNotaEntryImpl build() {
        return new PrimaNotaEntryImpl(
                postingRecord.getId(),
                postingRecord.getDisplayNumber(),
                postingRecord.getEntryDate(),
                postingRecord.getDocumentNumber(),
                postingRecord.getDocumentDate(),
                postingRecord.getDocumentAmount(),
                postingRecord.getAccountDebitted(),
                postingRecord.getAccountCreditted(),
                postingRecord.getDocumentAmount()
        );
    }


    public PrimaNotaEntryBuilder addPostingRecord(final PostingRecord record) {
        this.postingRecord = record;

        return this;
    }
}
