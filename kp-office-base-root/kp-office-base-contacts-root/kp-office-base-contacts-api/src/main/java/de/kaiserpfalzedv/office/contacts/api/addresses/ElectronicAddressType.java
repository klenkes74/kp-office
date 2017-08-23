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

package de.kaiserpfalzedv.office.contacts.api.addresses;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-08-10
 */
public enum ElectronicAddressType {
    PHONE("Tel", "%s"),
    FAX("Fax", "%s"),
    EMAIL("EmailImpl", "%s"),
    HTTP("Web", "http://%s"),
    HTTPS("Secure Web", "https://%s"),
    SIP("SIP", "sip://%s");

    private String type;
    private String format;


    ElectronicAddressType(final String type, final String format) {
        this.type = type;
        this.format = format;
    }


    String getName() {
        return type;
    }

    String format(final ElectronicAddress address) {
        return String.format(format, address);
    }
}
