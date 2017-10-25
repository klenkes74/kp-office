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

package de.kaiserpfalzedv.office.finance.chartofaccounts.impl;

import java.util.UUID;

import javax.money.CurrencyUnit;

import de.kaiserpfalzedv.office.finance.chartofaccounts.api.CostCenter;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 28.12.15 07:16
 */
public class CostCenterImpl extends AccountImpl implements CostCenter {
    private static final long serialVersionUID = 1917827542548830501L;

    CostCenterImpl(
            final UUID tenantId,
            final UUID id,
            final String displayName,
            final String fullName,
            final CurrencyUnit currency
    ) {
        super(tenantId, id, displayName, fullName, currency);
    }
}
