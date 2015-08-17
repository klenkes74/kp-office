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

import de.kaiserpfalzEdv.commons.jee.paging.Pageable;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 08.08.15 22:00
 */
public interface PrimaNotaService {
    /**
     * @return The complete set of prima notae.
     */
    Set<PrimaNota> listAllPrimaNota();

    PrimaNotaPage loadPrimaNota(final PrimaNota info, final Pageable pageRequest);

    PrimaNota save(PrimaNota created);


    /**
     * @param primaNota The prima nota to add this entry to.
     * @param entry     The prima nota entry to add.
     */
    void addEntry(@NotNull PrimaNota primaNota, @NotNull PrimaNotaEntry entry);

    /**
     * @param primaNota The prima nota to remove this entry entry from.
     * @param entry     The prima nota entry to remove.
     */
    void removeEntry(@NotNull PrimaNota primaNota, @NotNull PrimaNotaEntry entry);


    /**
     * Closes the prima nota for further bookings and closes the period. For the given tenant no posting record may be
     * added to the given period.
     *
     * @param info The prima nota to be closed.
     */
    void closePrimaNota(final PrimaNota info);
}