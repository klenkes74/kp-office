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

import de.kaiserpfalzedv.commons.api.info.SoftwareLibrary;
import de.kaiserpfalzedv.commons.api.info.SoftwareLicense;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 1.0.0
 */
public class SoftwareLibraryTO implements SoftwareLibrary {
    /**
     * The name of the library
     */
    private String libraryName;

    /**
     * The copyright notice of the author/owner of the software.
     */
    private String copyrightNotice;

    /**
     * The license of this library.
     */
    private SoftwareLicense license;


    SoftwareLibraryTO(
            @NotNull final String libraryName,
            @NotNull final String copyrightNotice,
            @NotNull final SoftwareLicense license
    ) {
        this.libraryName = libraryName;
        this.copyrightNotice = copyrightNotice;
        this.license = license;
    }

    @Override
    public String getLibraryName() {
        return libraryName;
    }

    @Override
    public String getCopyrightNotice() {
        return copyrightNotice;
    }

    @Override
    public SoftwareLicense getLicense() {
        return license;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof SoftwareLibraryTO)) return false;

        SoftwareLibraryTO that = (SoftwareLibraryTO) o;

        return new EqualsBuilder()
                .append(getLibraryName(), that.getLibraryName())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getLibraryName())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("libraryName", libraryName)
                .append("license", license.getTitle())
                .toString();
    }
}
