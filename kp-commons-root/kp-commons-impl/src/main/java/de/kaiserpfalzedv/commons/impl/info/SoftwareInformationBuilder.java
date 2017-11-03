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

import de.kaiserpfalzedv.commons.api.BuilderException;
import de.kaiserpfalzedv.commons.api.info.DataSchemaChange;
import de.kaiserpfalzedv.commons.api.info.SoftwareInformation;
import de.kaiserpfalzedv.commons.api.info.SoftwareLibrary;
import de.kaiserpfalzedv.commons.api.info.SoftwareLicense;
import org.apache.commons.lang3.builder.Builder;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import static org.apache.commons.lang3.StringUtils.isBlank;


/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 1.0.0
 */
public class SoftwareInformationBuilder implements Builder<SoftwareInformation> {
    private final HashSet<SoftwareLibrary> libraries = new HashSet<>();
    private final ArrayList<DataSchemaChange> changes = new ArrayList<>();
    private String title;
    private String description;
    private SoftwareLicense license;

    @Override
    public SoftwareInformation build() {
        validate();

        return new SoftwareInformationTO(title, description, license, libraries, changes);
    }

    private void validate() {
        ArrayList<String> failures = new ArrayList<>();

        if (isBlank(title)) failures.add("Software Information needs a title!");
        if (isBlank(description)) failures.add("Software Information needs a description!");
        if (license == null) failures.add("Software Information needs a license!");

        if (failures.size() > 0) {
            throw new BuilderException(SoftwareInformationTO.class, failures);
        }
    }


    public SoftwareInformationBuilder withTitle(@NotNull final String title) {
        this.title = title;
        return this;
    }

    public SoftwareInformationBuilder withDescription(@NotNull final String description) {
        this.description = description;
        return this;
    }

    public SoftwareInformationBuilder withLicense(@NotNull final SoftwareLicense license) {
        this.license = license;
        return this;
    }


    public SoftwareInformationBuilder withLibraries(@NotNull final Collection<SoftwareLibrary> libraries) {
        this.libraries.clear();
        this.libraries.addAll(libraries);
        return this;
    }

    public SoftwareInformationBuilder addLibraries(@NotNull final Collection<SoftwareLibrary> libraries) {
        this.libraries.addAll(libraries);
        return this;
    }

    public SoftwareInformationBuilder addLibrary(@NotNull final SoftwareLibrary library) {
        this.libraries.add(library);
        return this;
    }

    public SoftwareInformationBuilder clearLibraries() {
        this.libraries.clear();
        return this;
    }

    public SoftwareInformationBuilder removeLibraries(@NotNull final Collection<SoftwareLibrary> libraries) {
        this.libraries.removeAll(libraries);
        return this;
    }

    public SoftwareInformationBuilder removeLibrary(@NotNull final SoftwareLibrary library) {
        this.libraries.remove(library);
        return this;
    }


    public SoftwareInformationBuilder withChanges(@NotNull final Collection<DataSchemaChange> changes) {
        this.changes.clear();
        this.changes.addAll(changes);
        return this;
    }

    public SoftwareInformationBuilder addChanges(@NotNull final Collection<DataSchemaChange> changes) {
        this.changes.addAll(changes);
        return this;
    }

    public SoftwareInformationBuilder addChange(@NotNull final DataSchemaChange change) {
        this.changes.add(change);
        return this;
    }

    public SoftwareInformationBuilder clearChanges() {
        this.changes.clear();
        return this;
    }

    public SoftwareInformationBuilder removeChanges(@NotNull final Collection<DataSchemaChange> changes) {
        this.changes.removeAll(changes);
        return this;
    }

    public SoftwareInformationBuilder removeChange(@NotNull final DataSchemaChange change) {
        this.changes.remove(change);
        return this;
    }
}
