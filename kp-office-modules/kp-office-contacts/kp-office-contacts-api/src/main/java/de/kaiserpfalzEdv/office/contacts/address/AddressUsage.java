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

package de.kaiserpfalzEdv.office.contacts.address;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public enum AddressUsage {
    /**
     * The default usage. Most of the addresses will be flagged default.
     */
    DEFAULT,
    /**
     * Contract (sales) communication address.
     */
    CONTRACT,
    /**
     * The billing address. Financial contact address.
     */
    BILLING,
    /**
     * The address to which shippings should be addressed to.
     */
    SHIPPING,
    /**
     * No defined usage.
     */
    NONE
}
