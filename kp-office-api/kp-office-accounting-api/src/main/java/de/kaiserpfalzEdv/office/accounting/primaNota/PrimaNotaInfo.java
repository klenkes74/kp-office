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

import de.kaiserpfalzEdv.office.commons.data.DisplayNameHolder;
import de.kaiserpfalzEdv.office.commons.data.DisplayNumberHolder;
import de.kaiserpfalzEdv.office.commons.data.IdentityHolder;
import de.kaiserpfalzEdv.office.commons.data.TenantIdHolder;

import java.io.Serializable;

/**
 * The base information about a prima nota.
 * only the size of the nota (in entries) is given. To retrieve the entries, the {@link PrimaNotaPage} is needed.
 *
 * @author klenkes
 * @version 2015Q1
 * @since 19.02.15 06:43
 */
public interface PrimaNotaInfo extends IdentityHolder, TenantIdHolder, DisplayNumberHolder, DisplayNameHolder, Serializable {
    int size();
}