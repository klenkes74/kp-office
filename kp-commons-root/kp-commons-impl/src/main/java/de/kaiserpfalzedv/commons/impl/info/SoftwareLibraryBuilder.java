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
import de.kaiserpfalzedv.commons.api.info.SoftwareLibrary;
import de.kaiserpfalzedv.commons.api.info.SoftwareLicense;
import de.kaiserpfalzedv.commons.impl.license.LicenseBuilder;
import de.kaiserpfalzedv.commons.impl.license.OpenSourceLicense;
import org.apache.commons.lang3.builder.Builder;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 1.0.0
 */
public class SoftwareLibraryBuilder implements Builder<SoftwareLibrary> {
    /**
     * The name of the library
     */
    private String libraryName;

    /**
     * The copyright notice of the owner.
     */
    private String copyrightNotice;

    /**
     * The license of this library.
     */
    private SoftwareLicense license;


    @Override
    public SoftwareLibrary build() {
        validate();

        return new SoftwareLibraryTO(libraryName, copyrightNotice, license);
    }

    private void validate() {
        ArrayList<String> failures = new ArrayList<>();

        if (isBlank(libraryName)) failures.add("The library needs a name!");
        if (isBlank(copyrightNotice)) failures.add("There is no copyright notice!");
        if (license == null) failures.add("The library needs a license!");

        if (failures.size() > 0) {
            throw new BuilderException(SoftwareLibraryTO.class, failures);
        }
    }


    public SoftwareLibraryBuilder withLibrary(@NotNull final SoftwareLibrary library) {
        withLibraryName(library.getLibraryName());
        withCopyrightNotice(library.getCopyrightNotice());
        withLicense(library.getLicense());

        return this;
    }


    public SoftwareLibraryBuilder withLibraryName(@NotNull String libraryName) {
        this.libraryName = libraryName;
        return this;
    }

    public SoftwareLibraryBuilder withCopyrightNotice(@NotNull String copyrightNotice) {
        this.copyrightNotice = copyrightNotice;
        return this;
    }

    public SoftwareLibraryBuilder withLicense(@NotNull SoftwareLicense license) {
        this.license = license;
        return this;
    }

    public SoftwareLibraryBuilder withLicense(@NotNull OpenSourceLicense license) {
        this.license = new LicenseBuilder().withBaseLicense(license).build();
        return this;
    }

    public SoftwareLibraryBuilder apache2_0() {
        return withLicense(OpenSourceLicense.APACHEv2_0);
    }

    public SoftwareLibraryBuilder bsd3() {
        return withLicense(OpenSourceLicense.BSD_3);
    }

    public SoftwareLibraryBuilder gpl2() {
        return withLicense(OpenSourceLicense.GPLv2);
    }

    public SoftwareLibraryBuilder lgpl2_1() {
        return withLicense(OpenSourceLicense.LGPLv2_1);
    }
}
