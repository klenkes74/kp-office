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

package de.kaiserpfalzEdv.office.core.license;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 08.02.15 22:46
 */
public class CantLoadLicenseException extends LicensingException {
    private static final long         serialVersionUID  = 7759838646958028634L;
    private static final ErrorMessage CANT_LOAD_LICENSE = ErrorMessage.LICENSE_3001;

    private final String fileName;

    public CantLoadLicenseException(final String licenseFile) {
        super(CANT_LOAD_LICENSE, CANT_LOAD_LICENSE.getMessage() + ": " + licenseFile);

        this.fileName = licenseFile;
    }

    public CantLoadLicenseException(final String licenseFile, final Throwable cause) {
        super(CANT_LOAD_LICENSE, CANT_LOAD_LICENSE.getMessage() + ": " + licenseFile, cause);

        this.fileName = licenseFile;
    }


    public String getFileName() {
        return fileName;
    }
}
