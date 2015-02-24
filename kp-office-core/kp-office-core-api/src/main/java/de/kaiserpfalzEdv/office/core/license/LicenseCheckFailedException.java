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
 * @since 08.02.15 22:05
 */
public class LicenseCheckFailedException extends LicensingException {
    private static final long         serialVersionUID = -9156150315466925458L;
    private static final ErrorMessage LICENSE_INVALID  = ErrorMessage.LICENSE_3002;

    private String fileName;

    public LicenseCheckFailedException(final String licenseFile) {
        super(LICENSE_INVALID, LICENSE_INVALID.getMessage() + ": " + licenseFile);

        this.fileName = licenseFile;
    }

    public LicenseCheckFailedException(final String licenseFile, final Throwable cause) {
        super(LICENSE_INVALID, LICENSE_INVALID.getMessage() + ": " + licenseFile, cause);

        this.fileName = licenseFile;
    }


    public String getFileName() {
        return fileName;
    }
}
