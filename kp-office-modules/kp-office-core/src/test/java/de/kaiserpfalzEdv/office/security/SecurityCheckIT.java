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

package de.kaiserpfalzEdv.office.security;

import de.kaiserpfalzEdv.office.tenant.TenantDTO;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.IOException;
import java.util.UUID;


/**
 * @author klenkes
 * @since 2014Q
 */
@RunWith(Arquillian.class)
public class SecurityCheckIT {
    private static final Logger LOG = LoggerFactory.getLogger(SecurityCheckIT.class);

    @Inject SecurityCheckManager scm;
    @Inject Subject subject;

    @Deployment
    public static Archive<?> createTestArchive() throws IOException {
        Archive<EnterpriseArchive> ear = ShrinkWrap.create(EnterpriseArchive.class, "kp-office.ear")
                .addAsModule(
                        ShrinkWrap.create(JavaArchive.class, "kp-office-core-ejb.jar")
                                .addPackage("de.kaiserpfalzEdv.office.security")
                                .addClass("de.kaiserpfalzEdv.office.security.SecurityCheckManager")
                                .addManifest()
                                .addAsManifestResource("META-INF/beans.xml", ArchivePaths.create("beans.xml"))
                )
                .addAsModule(
                        ShrinkWrap.create(WebArchive.class, "kp-office-test.war")
                                .addPackage("de.kaiserpfalzEdv.office.security")
                                .addClass("de.kaiserpfalzEdv.office.security.SecurityCheckManager")
                                .addClass("de.kaiserpfalzEdv.office.security.SecurityCheckTest")
                                .addAsLibraries(
                                        Maven.configureResolver().resolve(
                                                "org.apache.shiro:shiro-core:1.2.3",
                                                "org.apache.shiro:shiro-web:1.2.3",
                                                "de.kaiserpfalz-edv.office:kp-office-api:0.1.0-SNAPSHOT",
                                                "de.kaiserpfalz-edv.office:kp-office-commons:0.1.0-SNAPSHOT"
                                        )
                                                .withTransitivity().asFile()
                                )
                                .addAsWebInfResource("web.xml")
                                .addAsWebInfResource("META-INF/beans.xml", ArchivePaths.create("beans.xml"))
                                .addAsWebInfResource("shiro.ini")
                                .addManifest()
                )
                .addAsLibraries(
                        Maven.configureResolver().resolve(
                                "org.apache.shiro:shiro-core:1.2.3",
                                "de.kaiserpfalz-edv.office:kp-office-api:0.1.0-SNAPSHOT",
                                "de.kaiserpfalz-edv.office:kp-office-commons:0.1.0-SNAPSHOT"
                        )
                                .withTransitivity().asFile()
                )
                .addAsManifestResource("META-INF/jboss-deployment-structure.xml", ArchivePaths.create("jboss-deployment-structure.xml"))
                .addManifest();
//        Archive<WebArchive> web = ShrinkWrap.create(WebArchive.class, "test.war")
//                .addPackage("de.kaiserpfalzEdv.office.security")
//                .addAsWebInfResource(EmptyAsset.INSTANCE, ArchivePaths.create("classes/META-INF/beans.xml"))
//                .addAsWebInfResource("META-INF/persistence.xml", ArchivePaths.create("classes/META-INF/persistence.xml"))
//                .addAsWebInfResource("META-INF/orm.xml", ArchivePaths.create("classes/META-INF/orm.xml"))
//                .addAsLibraries(
//                        Maven.configureResolver().resolve(
//                                "org.apache.commons:commons-lang3:3.1",
//                                "de.kaiserpfalzEdv.commons:kp-commons-api:2013Q4",
//                                "de.kaiserpfalz-edv.iam:kp-iam-commons:2014Q2-SNAPSHOT"
//                        ).withTransitivity().asFile()
//                );

        LOG.info("EAR contains: {}", ear.toString(true));

        return ear;
    }

    @Test
    public void guestCanView() {
        subject.login(new UsernamePasswordToken("guest", "guest"));
        Assert.assertEquals("info", scm.getInfo());
    }

    @Test(expected=AuthorizationException.class)
    public void guestCantWrite() {
        LOG.info("Check writing via guests ...");

        subject.login(new UsernamePasswordToken("guest", "guest"));
        scm.setInfo("test");
    }

    @Test
    public void hendyCanWrite() {
        subject.login(new UsernamePasswordToken("hendy", "hendy"));
        scm.setInfo("hendy");
    }

    @Test(expected=AuthorizationException.class)
    public void hendyCantWriteTenant1() {
        subject.login(new UsernamePasswordToken("hendy", "hendy"));

        scm.setInfo(new TenantDTO(UUID.randomUUID(), "1", "Test-Tenant"), "tenant1");
    }

    @Test
    public void rtlCanWriteTenant1() {
        subject.login(new UsernamePasswordToken("rtl", "secret2"));

        scm.setInfo(new TenantDTO(UUID.randomUUID(), "1", "Test-Tenant"), "tenant1");
    }

    @Test
    public void adminCanWrite() {
        subject.login(new UsernamePasswordToken("root", "secret"));
        scm.setInfo("test");

        Assert.assertEquals("test", scm.getInfo());
    }
}
