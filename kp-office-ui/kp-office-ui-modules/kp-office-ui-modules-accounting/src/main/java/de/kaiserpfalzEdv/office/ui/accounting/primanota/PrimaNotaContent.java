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

package de.kaiserpfalzEdv.office.ui.accounting.primanota;

import de.kaiserpfalzEdv.office.accounting.primaNota.PrimaNotaEntry;
import de.kaiserpfalzEdv.office.ui.api.mvp.View;

/**
 * The definition for the presenter to ship the data to the view.
 *
 * @author klenkes
 * @version 2015Q1
 * @since 16.08.15 21:28
 */
public interface PrimaNotaContent extends View {
    /**
     * Adds a single entry to the displayed prima nota. Is normally called during adding new posting records to the
     * prima nota.
     *
     * @param entry The additional entry to be displayed.
     */
    void addEntry(final PrimaNotaEntry entry);
}
