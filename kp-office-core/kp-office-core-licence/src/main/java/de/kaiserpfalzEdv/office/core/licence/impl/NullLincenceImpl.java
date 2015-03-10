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

package de.kaiserpfalzEdv.office.core.licence.impl;

import de.kaiserpfalzEdv.commons.service.VersionRange;
import de.kaiserpfalzEdv.commons.service.Versionable;
import de.kaiserpfalzEdv.office.commons.SoftwareVersion;
import de.kaiserpfalzEdv.office.commons.SoftwareVersionRange;
import de.kaiserpfalzEdv.office.core.licence.OfficeLicence;

import java.time.LocalDate;
import java.util.UUID;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 02.03.15 20:17
 */
public class NullLincenceImpl implements OfficeLicence {
    private static final UUID        LICENCE_UUID = UUID.fromString("00000000-0000-0000-0000-000000000000");
    private static final LocalDate   ISSUED       = LocalDate.MIN;
    private static final Versionable VERSION      = new SoftwareVersion("0.0.0");

    @Override
    public UUID getId() {
        return LICENCE_UUID;
    }

    @Override
    public LocalDate getIssueDate() {
        return ISSUED;
    }

    @Override
    public boolean isValid(String software, VersionRange range) {
        return false;
    }

    @Override
    public String getIssuer() {
        return "Kaiserpfalz EDV-Service";
    }

    @Override
    public String getLicensee() {
        return "- no licencee -";
    }

    @Override
    public LocalDate getStart() {
        return ISSUED;
    }

    @Override
    public LocalDate getExpiry() {
        return ISSUED;
    }

    @Override
    public String getSoftware() {
        return "- no software -";
    }

    @Override
    public VersionRange getVersionRange() {
        return new SoftwareVersionRange(VERSION, VERSION);
    }

    @Override
    public boolean containsFeature(String feature) {
        return false;
    }
}
