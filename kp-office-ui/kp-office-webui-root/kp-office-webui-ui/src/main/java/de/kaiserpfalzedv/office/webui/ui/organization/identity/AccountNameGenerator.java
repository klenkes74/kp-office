/*
 * Copyright 2016 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzEdv.piracc.organization.identity;

import de.kaiserpfalzEdv.piracc.backend.auth.UserService;
import de.kaiserpfalzEdv.piracc.backend.db.master.Identity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 19.09.15 07:45
 */
public class AccountNameGenerator implements Serializable {
    private static final long serialVersionUID = 2439366081999372882L;

    private static final Logger LOG = LoggerFactory.getLogger(AccountNameGenerator.class);


    public static String generateLogin(final Identity identity, final UserService service) {
        String result = preferredAccountName(identity);
        if (!service.loginExists(result))
            return result;

        for (int i = 1; i <= 999; i++) {
            String loginWithNumber = result + String.format("%03d", i);

            if (!service.loginExists(loginWithNumber))
                return loginWithNumber;
        }

        return "";
    }

    private static String preferredAccountName(final Identity identity) {
        String surName = replaceUTF8(identity.getSurName().substring(0, 5).toLowerCase());
        String givenName = replaceUTF8(identity.getGivenName().substring(0, 1).toLowerCase());

        return givenName + surName;
    }

    private static String replaceUTF8(final String orig) {
        return java.text.Normalizer.normalize(
                orig,
                java.text.Normalizer.Form.NFD
        ).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }
}