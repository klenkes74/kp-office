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
 * The license key file can't be loaded.
 *
 * @author klenkes
 * @version 2015Q1
 * @since 08.02.15 22:50
 */
public class CantLoadKeyException extends LicensingException {
    private static final long         serialVersionUID = 5304160454819460543L;
    private static final ErrorMessage CANT_LOAD_FILE   = ErrorMessage.LICENSE_2001;

    private final String fileName;


    public CantLoadKeyException(final String fileName) {
        super(CANT_LOAD_FILE, CANT_LOAD_FILE.getMessage() + ": " + fileName);

        this.fileName = fileName;
    }

    public CantLoadKeyException(final String fileName, final Throwable cause) {
        super(CANT_LOAD_FILE, CANT_LOAD_FILE.getMessage() + ": " + fileName, cause);

        this.fileName = fileName;
    }

    /**
     * A package-private method for subclasses. Should not be called directly.
     *
     * @param msg      The message to be returned.
     * @param fileName The name of the file that could not be loaded.
     */
    CantLoadKeyException(final ErrorMessage msg, final String fileName) {
        super(msg, msg.getMessage() + ": " + fileName);

        this.fileName = fileName;
    }

    CantLoadKeyException(final ErrorMessage msg, final String fileName, final Throwable cause) {
        super(msg, msg.getMessage() + ": " + fileName, cause);

        this.fileName = fileName;
    }


    public String getFileName() {
        return fileName;
    }
}
