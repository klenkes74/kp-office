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

package de.kaiserpfalzedv.office.metainfo.impl;

import java.io.Serializable;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.constraints.NotNull;

import com.github.zafarkhaja.semver.Version;
import de.kaiserpfalzedv.office.metainfo.api.ManifestReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This small tool provides the Software version to the system. In the current implementation it reads the
 * value of "{@value #VERSION_KEY}" (defined in {@link #VERSION_KEY}) from the MANIFEST.MF file by using the service
 * {@link ManifestReader}.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-10-30
 */
@Singleton
public class VersionProvider implements Serializable {
    private static final long serialVersionUID = -1078005772736667443L;
    private static final Logger LOG = LoggerFactory.getLogger(VersionProvider.class);

    /**
     * Default key used to read the version from the MANIFEST.MF.
     */
    private static final String VERSION_KEY = "KPO-Version";


    /**
     * The service to read the manifest files with.
     */
    private ManifestReader manifestReader;

    /**
     * The version read from the MANFEST.MF. The default MANIFEST-entry to read is
     * "{@value #VERSION_KEY}" (defined in {@link #VERSION_KEY}).
     */
    private Version version;


    @Inject
    public VersionProvider(@NotNull final ManifestReader manifestReader) {
        this.manifestReader = manifestReader;
    }

    @PostConstruct
    public void init() {
        Optional<String> versionString = manifestReader.read(VERSION_KEY);

        if (versionString.isPresent()) {
            version = Version.valueOf(versionString.get());
        } else {
            LOG.error("Could not load version of software (using key: '{}')!", VERSION_KEY);
        }
    }

    @Produces
    public Version getSystemVersion() {
        return version;
    }
}
