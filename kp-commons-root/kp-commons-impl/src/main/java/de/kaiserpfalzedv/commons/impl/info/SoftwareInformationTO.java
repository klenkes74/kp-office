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

package de.kaiserpfalzedv.commons.impl.info;

import de.kaiserpfalzedv.commons.api.info.DataSchemaChange;
import de.kaiserpfalzedv.commons.api.info.SoftwareInformation;
import de.kaiserpfalzedv.commons.api.info.SoftwareLibrary;
import de.kaiserpfalzedv.commons.api.info.SoftwareLicense;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 1.0.0
 */
public class SoftwareInformationTO implements SoftwareInformation {
    private final HashSet<SoftwareLibrary> libraries;
    private final ArrayList<DataSchemaChange> changes;
    private String title;
    private String description;
    private SoftwareLicense license;

    SoftwareInformationTO(
            @NotNull final String title,
            @NotNull final String description,
            @NotNull final SoftwareLicense license,
            @NotNull final HashSet<SoftwareLibrary> libraries,
            @NotNull final ArrayList<DataSchemaChange> changes
    ) {
        this.title = title;
        this.description = description;
        this.license = license;

        this.libraries = libraries;
        this.changes = changes;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public SoftwareLicense getSoftwareLicense() {
        return license;
    }

    @Override
    public Collection<SoftwareLibrary> getLibraries() {
        return Collections.unmodifiableCollection(libraries);
    }

    @Override
    public List<DataSchemaChange> getChanges() {
        return Collections.unmodifiableList(changes);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof SoftwareInformationTO)) return false;

        SoftwareInformationTO that = (SoftwareInformationTO) o;

        return new EqualsBuilder()
                .append(getTitle(), that.getTitle())
                .append(getDescription(), that.getDescription())
                .append(getSoftwareLicense(), that.getSoftwareLicense())
                .append(getLibraries(), that.getLibraries())
                .append(getChanges(), that.getChanges())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getTitle())
                .append(getDescription())
                .append(getSoftwareLicense())
                .append(getLibraries())
                .append(getChanges())
                .toHashCode();
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("title", title)
                .append("description", description)
                .toString();
    }
}
