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

package de.kaiserpfalzEdv.office.accounting.chartsofaccounts;

import de.kaiserpfalzEdv.office.commons.DisplayNameHolder;
import de.kaiserpfalzEdv.office.commons.DisplayNumberHolder;
import de.kaiserpfalzEdv.office.commons.IdentityHolder;
import de.kaiserpfalzEdv.office.commons.TenantIdHolder;

import java.io.Serializable;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 18.02.15 16:36
 */
public interface Account extends IdentityHolder, TenantIdHolder, DisplayNumberHolder, DisplayNameHolder, Serializable {
    /**
     * @return The display number of the current mapping (if any)
     */
    String getCurrentMapping();
}
