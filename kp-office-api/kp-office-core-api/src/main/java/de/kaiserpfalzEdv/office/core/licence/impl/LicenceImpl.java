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

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.kaiserpfalzEdv.commons.service.VersionRange;
import de.kaiserpfalzEdv.office.commons.SoftwareVersion;
import de.kaiserpfalzEdv.office.commons.SoftwareVersionRange;
import de.kaiserpfalzEdv.office.core.licence.OfficeLicence;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

/**
 * The loaded licence.
 * 
 * @author klenkes
 * @version 2015Q1
 * @since 14.02.15 18:35
 */
@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class LicenceImpl implements OfficeLicence {
    private static final long serialVersionUID = -3563183495993877255L;

    @JsonIgnore
    private final HashMap<String, Boolean> modules = new HashMap<>(10);
    @JsonProperty("id")
    private UUID                 id;
    @JsonProperty("issued")
    private LocalDate            issued;
    @JsonProperty("issuer")
    private String               issuer;
    @JsonProperty("licensee")
    private String               licensee;
    @JsonProperty("starts")
    private LocalDate            starts;
    @JsonProperty("expires")
    private LocalDate            expires;
    @JsonProperty("software")
    private String               software;
    @JsonProperty("range")
    private SoftwareVersionRange versionRange;


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

        if (range != null) {
            this.versionRange = new SoftwareVersionRange(range.getStart(), range.getEnd());
        } else {
            this.versionRange = new SoftwareVersionRange(new SoftwareVersion("0.0.0"), new SoftwareVersion("999999.999999.999999"));
        }

        if (modules != null) {
            for (String module : modules) {
                this.modules.put(module, true);
            }
        }
    }


    public LicenceImpl(
            @JsonProperty("id") UUID id,
            @JsonProperty("issued") LocalDate issued,
            @JsonProperty("issuer") String issuer,
            @JsonProperty("licensee") String licensee,
            @JsonProperty("starts") LocalDate starts,
            @JsonProperty("expires") LocalDate expires,
            @JsonProperty("software") String software,
            @JsonProperty("range") SoftwareVersionRange range,
            @JsonProperty("modules") String[] modules
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

    @JsonProperty("modules")
    @Override
    public String[] getModules() {
        return modules.keySet().toArray(new String[1]);
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
