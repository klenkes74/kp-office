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

package de.kaiserpfalzEdv.office.accounting.test;

import de.kaiserpfalzEdv.office.accounting.chartsofaccounts.Account;
import de.kaiserpfalzEdv.office.accounting.chartsofaccounts.AccountMapping;
import de.kaiserpfalzEdv.office.accounting.chartsofaccounts.ChartOfAccount;
import de.kaiserpfalzEdv.office.accounting.chartsofaccounts.ChartsOfAccounts;
import de.kaiserpfalzEdv.office.accounting.chartsofaccounts.impl.AccountBuilder;
import de.kaiserpfalzEdv.office.accounting.chartsofaccounts.impl.AccountMappingImpl;
import de.kaiserpfalzEdv.office.accounting.chartsofaccounts.impl.AccountMappingLocator;
import de.kaiserpfalzEdv.office.accounting.chartsofaccounts.impl.ChartsOfAccountsImpl;
import de.kaiserpfalzEdv.office.commons.data.TenantIdHolder;
import org.javamoney.moneta.Money;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.UUID;

import static de.kaiserpfalzEdv.office.accounting.chartsofaccounts.ChartOfAccount.CoA.D03;
import static de.kaiserpfalzEdv.office.accounting.chartsofaccounts.ChartOfAccount.CoA.D04;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 19.02.15 19:58
 */
@Configuration
public class AccountingConfiguration {
    @Bean
    @ChartOfAccount(D03)
    public AccountMapping d03Mapping() {
        AccountMappingImpl result = new AccountMappingImpl(TenantIdHolder.DEFAULT_TENANT_ID, UUID.fromString("6c4a5120-088a-4321-a8e1-5d0c22a4b436"), "D03", "Default Chart of Accounts Type 03");

        result.addTranslation("1600", new String[]{"ddb684b8-501c-47fc-a46c-7990110bf092", "cb488173-b9b0-4f37-adb1-385d2051363e"});
        result.addTranslation("1100", new String[]{"804327a3-50f7-4f2c-8ca0-1c3cd502b1ab"});

        return result;
    }

    @Bean
    @ChartOfAccount(D04)
    public AccountMapping d04Mapping() {
        AccountMappingImpl result = new AccountMappingImpl(TenantIdHolder.DEFAULT_TENANT_ID, UUID.fromString("fbee614d-daf0-43a6-890d-16125be27f75"), "D04", "Default Chart of Accounts Type 04");

        result.addTranslation("1800", new String[]{"cb488173-b9b0-4f37-adb1-385d2051363e", "804327a3-50f7-4f2c-8ca0-1c3cd502b1ab"});
        result.addTranslation("1100", new String[]{"ddb684b8-501c-47fc-a46c-7990110bf092"});

        return result;
    }

    @Bean
    public AccountMappingLocator chartsOfAccountsLocator() {
        AccountMappingLocator result = new AccountMappingLocator();

        result.addMapping(d04Mapping());
        result.addMapping(d03Mapping());

        return result;
    }

    @Bean
    public ChartsOfAccounts accounts() {
        ChartsOfAccountsImpl result = new ChartsOfAccountsImpl(
                TenantIdHolder.DEFAULT_TENANT_ID, UUID.fromString("cf5f01fb-83c7-4352-80bd-681163956786"),
                "KPO",
                "Internal Chart Of Accounts",
                Money.of(0, "EUR").getCurrency()
        );

        ArrayList<Account> accounts = new ArrayList<>(3);
        accounts.add(
                new AccountBuilder()
                        .withTenantId(TenantIdHolder.DEFAULT_TENANT_ID)
                        .withId(UUID.fromString("ddb684b8-501c-47fc-a46c-7990110bf092"))
                        .withName("Bank")
                        .build()
        );
        accounts.add(
                new AccountBuilder()
                        .withTenantId(TenantIdHolder.DEFAULT_TENANT_ID)
                        .withId(UUID.fromString("cb488173-b9b0-4f37-adb1-385d2051363e"))
                        .withName("Privat R. Lichti")
                        .build()
        );
        accounts.add(
                new AccountBuilder()
                        .withTenantId(TenantIdHolder.DEFAULT_TENANT_ID)
                        .withId(UUID.fromString("804327a3-50f7-4f2c-8ca0-1c3cd502b1ab"))
                        .withName("Privat K. Lichti")
                        .build()
        );

        result.setAccounts(accounts);

        return result;
    }
}