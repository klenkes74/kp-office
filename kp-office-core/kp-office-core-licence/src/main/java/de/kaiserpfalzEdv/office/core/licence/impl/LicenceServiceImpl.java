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

import com.verhas.licensor.License;
import de.kaiserpfalzEdv.commons.util.BuilderException;
import de.kaiserpfalzEdv.office.core.licence.CantLoadKeyException;
import de.kaiserpfalzEdv.office.core.licence.CantLoadLicenceException;
import de.kaiserpfalzEdv.office.core.licence.InvalidKeyDigestException;
import de.kaiserpfalzEdv.office.core.licence.LicenceCheckFailedException;
import de.kaiserpfalzEdv.office.core.licence.LicenceExpiredException;
import de.kaiserpfalzEdv.office.core.licence.LicenceService;
import de.kaiserpfalzEdv.office.core.licence.OfficeLicence;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.bouncycastle.openpgp.PGPException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.io.IOException;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 08.02.15 21:09
 */
@Named
public class LicenceServiceImpl implements LicenceService {
    private static final Logger LOG = LoggerFactory.getLogger(LicenceServiceImpl.class);


    private static final String KEY_RING     = "kpoffice.key";
    private static final String LICENSE_FILE = "kpoffice.lic";


    private static final byte[] DIGEST = new byte[]{
            (byte) 0x66,
            (byte) 0xD6, (byte) 0x36, (byte) 0xE7, (byte) 0x77, (byte) 0x70, (byte) 0x5A, (byte) 0xF8, (byte) 0xD7,
            (byte) 0x9C, (byte) 0x82, (byte) 0x08, (byte) 0x60, (byte) 0x09, (byte) 0x8E, (byte) 0x63, (byte) 0xD2,
            (byte) 0x13, (byte) 0x6C, (byte) 0xA1, (byte) 0xB0, (byte) 0x6D, (byte) 0xFA, (byte) 0xBC, (byte) 0x2B,
            (byte) 0x1E, (byte) 0xCD, (byte) 0xEF, (byte) 0x44, (byte) 0x71, (byte) 0x6D, (byte) 0x26, (byte) 0x84,
            (byte) 0xB9, (byte) 0x92, (byte) 0xC4, (byte) 0x06, (byte) 0x06, (byte) 0xA5, (byte) 0x7B, (byte) 0xCD,
            (byte) 0x6F, (byte) 0x87, (byte) 0xA4, (byte) 0xE4, (byte) 0x67, (byte) 0x5F, (byte) 0x6A, (byte) 0x71,
            (byte) 0xBA, (byte) 0x25, (byte) 0xD5, (byte) 0x1F, (byte) 0xD5, (byte) 0xF6, (byte) 0x6E, (byte) 0x88,
            (byte) 0xF1, (byte) 0xF6, (byte) 0x65, (byte) 0xA8, (byte) 0xE6, (byte) 0xDA, (byte) 0x86,
    };

    private static final byte[] TEST_DIGEST = new byte[]{
            (byte) 0x48,
            (byte) 0x36, (byte) 0x06, (byte) 0x14, (byte) 0x05, (byte) 0xAF, (byte) 0xE2, (byte) 0x78, (byte) 0x37,
            (byte) 0x02, (byte) 0x8E, (byte) 0xD5, (byte) 0xEE, (byte) 0x50, (byte) 0xFD, (byte) 0x7F, (byte) 0x48,
            (byte) 0xFD, (byte) 0xC7, (byte) 0xF4, (byte) 0xAE, (byte) 0x53, (byte) 0x0B, (byte) 0xF6, (byte) 0x91,
            (byte) 0xA2, (byte) 0x43, (byte) 0xB7, (byte) 0x5C, (byte) 0x46, (byte) 0x2F, (byte) 0x26, (byte) 0x3D,
            (byte) 0xE9, (byte) 0xE6, (byte) 0xAD, (byte) 0x1A, (byte) 0x20, (byte) 0x3E, (byte) 0x2B, (byte) 0x34,
            (byte) 0x97, (byte) 0x20, (byte) 0x6C, (byte) 0x23, (byte) 0x60, (byte) 0xF3, (byte) 0xA9, (byte) 0xCA,
            (byte) 0x03, (byte) 0x9E, (byte) 0x62, (byte) 0xAD, (byte) 0x90, (byte) 0xB7, (byte) 0x7E, (byte) 0xE2,
            (byte) 0xDB, (byte) 0xF7, (byte) 0xEA, (byte) 0xD3, (byte) 0x51, (byte) 0x7B, (byte) 0x2B,
    };


    private final String        licenseFile;
    private       License       rawLicense;
    private       OfficeLicence license;


    @Inject
    public LicenceServiceImpl(@Value("${licence.file}") final String licenseFile) {
        this.licenseFile = isNotBlank(licenseFile) ? licenseFile : LICENSE_FILE;

        LOG.trace("Created: {}", this);
    }


    @PostConstruct
    public void init() throws CantLoadKeyException, CantLoadLicenceException, LicenceCheckFailedException, LicenceExpiredException {
        rawLicense = new License();

        loadLicenseKeyRing();
        loadLicenseFile();
        verifyLicenseIsUntouched();

        try {
            license = new LicenseBuilder().withLicense(rawLicense).build();
        } catch (BuilderException e) {
            LOG.error("Failure reading the licence: {}", e.getFailures());

            throw new LicenceCheckFailedException(licenseFile, e);
        } catch (IllegalArgumentException | IllegalStateException e) {
            throw new LicenceCheckFailedException(licenseFile, e);
        }

        LOG.trace("Initialized: {}", this);
    }

    @PreDestroy
    public void close() {
        LOG.trace("Destroyed: {}", this);
    }


    @Override
    public OfficeLicence getLicence() {
        return license;
    }


    private void loadLicenseKeyRing() throws CantLoadKeyException {
        try {
            loadKeyringOrTestkeyring();
        } catch (NullPointerException | IOException e) {
            throw new CantLoadKeyException(KEY_RING, e);
        } catch (IllegalArgumentException e) {
            dumpKeyDigest();

            throw new InvalidKeyDigestException(KEY_RING, e);
        }
    }

    private void loadKeyringOrTestkeyring() throws IOException {
        ClassLoader loader = getClass().getClassLoader();

        LOG.debug("Loading key: {}", loader.getResource(KEY_RING));

        try {
            rawLicense.loadKeyRingFromResource(KEY_RING, DIGEST);
        } catch (IllegalArgumentException e) {
            LOG.warn("Loading test keyring instead of production ring!");
            rawLicense.loadKeyRingFromResource(KEY_RING, TEST_DIGEST);
        }
    }

    private void dumpKeyDigest() {
        if (LOG.isDebugEnabled()) {
            try {
                License license = new License();
                license.loadKeyRingFromResource(KEY_RING, null);
                LOG.info("Keyring digest is: {}", license.dumpPublicKeyRingDigest());
            } catch (IOException e1) {
                LOG.error(e1.getClass().getSimpleName() + " caught: " + e1.getMessage(), e1);
            }
        }
    }

    private void loadLicenseFile() throws CantLoadLicenceException, LicenceCheckFailedException {
        File f = new File(licenseFile);

        if (f.exists()) {
            LOG.info("Loading licence: {}", f.toURI());
        } else {
            LOG.warn("License file does not exist: {}", f.toURI());
        }

        try {
            rawLicense.setLicenseEncodedFromFile(f.getCanonicalPath());
        } catch (IOException | PGPException e) {
            throw new CantLoadLicenceException(f.getAbsolutePath(), e);
        }
    }


    private void verifyLicenseIsUntouched() throws LicenceCheckFailedException {
        if (! rawLicense.isVerified()) {
            throw new LicenceCheckFailedException(licenseFile);
        }
    }



    @Override
    public String toString() {
        if (rawLicense != null) {
                return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                        .append("licence", license)
                        .build();
        }

        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("licenseFile", licenseFile)
                .build();
    }
}
