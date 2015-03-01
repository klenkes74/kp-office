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
import de.kaiserpfalzEdv.office.core.licence.OfficeLicence;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.UUID;

/**
 * The loaded licence.
 * 
 * @author klenkes
 * @version 2015Q1
 * @since 14.02.15 18:35
 */
class LicenceImpl implements OfficeLicence {
    private static final long serialVersionUID = -7238073081541326721L;
    private final HashMap<String, Boolean> modules = new HashMap<>(10);
    private UUID      id;
    private LocalDate issued;
    private String    issuer;
    private String    licensee;
    private LocalDate starts;
    private LocalDate expires;
    private String      software;
    private VersionRange versionRange;


    public LicenceImpl(
            UUID id,
            LocalDate issued,
            String issuer,
            String licensee,
            LocalDate starts,
            LocalDate expires,
            String software,
            VersionRange range,
            String[] modules
    ) {
        this.id = id;
        this.issued = issued;
        this.issuer = issuer;
        this.licensee = licensee;

        this.starts = starts;
        this.expires = expires;

        this.software = software;
        this.versionRange = range;

        for (String module : modules) {
            this.modules.put(module, true);
        }
    }


    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public LocalDate getIssueDate() {
        return issued;
    }

    @Override
    public boolean isValid(final String software, final VersionRange range) {
        return this.software.equals(software) 
                && (this.versionRange.overlapsWith(range))
                && isWithinValidityPeriod();
    }

    @Override
    public String getIssuer() {
        return issuer;
    }

    @Override
    public String getLicensee() {
        return licensee;
    }

    @Override
    public LocalDate getStart() {
        return starts;
    }

    @Override
    public LocalDate getExpiry() {
        return expires;
    }

    private boolean isWithinValidityPeriod() {
        LocalDate now = LocalDate.now();

        return (starts.isBefore(now) || starts.isEqual(now))
                && (expires.isAfter(now) || expires.isEqual(now));
    }

    @Override
    public String getSoftware() {                              
        return software;
    }

    @Override
    public VersionRange getVersionRange() {
        return versionRange;
    }

    @Override
    public boolean containsFeature(String feature) {
        return modules.containsKey(feature);
    }
    
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null)                    return false;
        if (obj == this)                    return true;
        if (obj.getClass() != getClass())   return false;

        LicenceImpl rhs = (LicenceImpl) obj;
        return new EqualsBuilder()
                .append(this.id, rhs.id)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .toHashCode();
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append(issuer)
                .append(software)
                .append(id)
                .build();
    }
}
