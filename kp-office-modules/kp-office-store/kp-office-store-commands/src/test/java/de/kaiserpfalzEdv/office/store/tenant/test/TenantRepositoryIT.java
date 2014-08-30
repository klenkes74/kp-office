/*
 * Copyright (c) 2014 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzEdv.office.store.tenant.test;

import de.kaiserpfalzEdv.office.store.DatabaseUpdater;
import de.kaiserpfalzEdv.office.store.tenant.TenantRepository;
import de.kaiserpfalzEdv.office.tenant.Tenant;
import de.kaiserpfalzEdv.office.tenant.TenantDTO;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ExplodedImporter;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.File;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author klenkes
 * @since 2014Q
 */
@RunWith(Arquillian.class)
public class TenantRepositoryIT {
    private static final Logger LOG = LoggerFactory.getLogger(TenantRepositoryIT.class);


    @Deployment
    public static Archive<?> createDeployment() {
        try {
            final File[] assertJ = Maven.resolver().loadPomFromFile("pom.xml")
                    .resolve("de.kaiserpfalz-edv.office:kp-office-backend-utils",
                            "org.assertj:assertj-guava",
                            "org.apache.commons:commons-lang3",
                            "commons-collections:commons-collections")
                    .withTransitivity()
                    .asFile();
            LOG.error("Libraries ({}): {}", assertJ.length, assertJ);

            final JavaArchive archive = ShrinkWrap.create(JavaArchive.class, "tenant-repository-test.jar")
                    .addPackage(Tenant.class.getPackage())
                    .addPackage(TenantDTO.class.getPackage())
                    .addPackage(TenantRepository.class.getPackage())
                    .addClass(DatabaseUpdater.class)
                    .addAsResource("test-persistence.xml", ArchivePaths.create("persistence.xml"))
                    .addAsResource("test-orm.xml", ArchivePaths.create("orm.xml"))
                    .merge(metaInfFolder("/ddl"), Filters.include(".*\\.xml"))
                    .merge(metaInfFolder("/data"), Filters.include(".*\\.csv"));
            mergeDependencies(archive, assertJ);
            return archive;
        } catch (Exception e) {
            LOG.error(e.getClass().getSimpleName() + ": " + e.getMessage(), e);

            throw e;
        }
    }


    @Inject
    private TenantRepository repository;


    @Test
    public void should_return_all_employee_records() throws Exception {
// when
        final List<? extends Tenant> employees = repository.all();
// then
        assertThat(employees).hasSize(1);
    }



    // -- Test utility methods
    private static void mergeDependencies(JavaArchive archive, File ... dependencies) {
        for (File file : dependencies) {
            archive.merge(ShrinkWrap.createFromZipFile(JavaArchive.class, file));
        }
    }

    private static GenericArchive metaInfFolder(final String path) {
        return ShrinkWrap.create(GenericArchive.class)
                .as(ExplodedImporter.class)
                .importDirectory("target/classes" + path)
                .as(GenericArchive.class);
    }
}
