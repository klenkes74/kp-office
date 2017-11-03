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

package de.kaiserpfalz.licensing.impl;

import com.github.zafarkhaja.semver.Version;
import de.kaiserpfalzedv.commons.api.BuilderException;
import de.kaiserpfalzedv.commons.api.Logging;
import de.kaiserpfalzedv.commons.api.licensing.ApplicationLicense;
import de.kaiserpfalzedv.commons.api.licensing.LicenseService;
import de.kaiserpfalzedv.commons.impl.info.ApplicationLicenseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.PostActivate;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * The singleton service loading the license at the system startup and provides it where it is needed. It's single
 * getter {@link #getLicense()} is also a CDI producer (annotated with {@link Produces}).
 * <p>
 * If the license loaded is invalid (either technically due to broken encryption or for other versions or time spans)
 * the service throws an {@link IllegalStateException} during initialization.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-10-30
 */
@Singleton
@Startup
public class LicenseServiceImpl implements LicenseService, Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(LicenseServiceImpl.class);

    /**
     * Default filename used for loading the license. Will be used if no filename is specified by java property
     * {@link #LICENSE_PROPERTY_KEY} or system environment variable {@link #LICENSE_ENVIRONMENT_KEY}.
     */
    private static final String LICENSE_DEFAULT_FILE_NAME = "/etc/kpoffice/kpoffice.license";

    /**
     * The name of the environment variable containing the filename of the license file. Can be overwritten by the java
     * property {@link #LICENSE_PROPERTY_KEY}. If neither java property nor system environment is set, the default
     * filename {@value #LICENSE_DEFAULT_FILE_NAME} specified by constant {@link #LICENSE_DEFAULT_FILE_NAME} will be
     * used.
     */
    private static final String LICENSE_ENVIRONMENT_KEY = "KPO_LICENSE";

    /**
     * The name of the java property containing the filename of the license file ('{@value #LICENSE_PROPERTY_KEY}'). If
     * this property is not set, the default value is taken from {@link #LICENSE_ENVIRONMENT_KEY}
     * ('{@value #LICENSE_ENVIRONMENT_KEY}') or as last fall back a staticly defined {@value #LICENSE_DEFAULT_FILE_NAME}
     * (as defined in {@link #LICENSE_DEFAULT_FILE_NAME}).
     */
    private static final String LICENSE_PROPERTY_KEY = "kpo.license";

    private ApplicationLicense license;
    private Version version;


    /**
     * @deprecated Only needed to fulfill the specification for EJBs. Will hopefully never be called!
     */
    @Deprecated
    public LicenseServiceImpl() {
        throw new UnsupportedOperationException("Invalid constructor called!");
    }

    @Inject
    public LicenseServiceImpl(
            @NotNull final Version version
    ) {
        this.version = version;
    }


    @PostActivate
    public void init() {
        String fileName = getLicenseFileName();

        try {
            license = new OfficeLicenseBuilder()
                    .withLicenseFile(fileName)
                    .build();
        } catch (BuilderException e) {
            LOG.error("Licensing service can't load the license: {}", e.getMessage());

            throw new IllegalStateException(e);
        }

        if (!license.isValid(version)) {
            Logging.OPLOG.error("License is not valid: {}", license.getId());

            throw new IllegalStateException("License is not valid: " + license.getId());
        }

        LOG.info("System loaded license: {}", license.getId());
    }


    /**
     * Retrieves the filename containing the license file for the system.
     * <p>
     * It will return the content of (in declining priority):
     * <p>
     * <ol>
     * <li>Java system property {@value #LICENSE_PROPERTY_KEY},</li>
     * <li>system environment variable {@value #LICENSE_ENVIRONMENT_KEY}, or</li>
     * <li>the default value {@value #LICENSE_DEFAULT_FILE_NAME}.</li>
     * </ol>
     *
     * @return the name of the license file to load.
     */
    private String getLicenseFileName() {
        String fileName = System.getenv(LICENSE_ENVIRONMENT_KEY);

        fileName = System.getProperty(LICENSE_PROPERTY_KEY, fileName);

        if (fileName == null) {
            fileName = LICENSE_DEFAULT_FILE_NAME;
        }

        LOG.debug("Loading system license from: {}", fileName);
        return fileName;
    }


    @Override
    @Produces
    public ApplicationLicense getLicense() {
        return new ApplicationLicenseBuilder()
                .withLicense(license)
                .build();
    }

}
