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

package de.kaiserpfalzedv.office.tenant;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.runtime.arquillian.CukeSpace;
import cucumber.runtime.arquillian.api.Features;
import de.kaiserpfalzedv.office.common.BaseSystemException;
import de.kaiserpfalzedv.office.common.client.config.ConfigReader;
import de.kaiserpfalzedv.office.common.client.config.impl.ConfigReaderBuilder;
import de.kaiserpfalzedv.office.common.client.messaging.MessagingCore;
import de.kaiserpfalzedv.office.common.client.messaging.impl.ActiveMQMessagingCoreImpl;
import de.kaiserpfalzedv.office.common.init.InitializationException;
import de.kaiserpfalzedv.office.tenant.api.Tenant;
import de.kaiserpfalzedv.office.tenant.api.TenantDoesNotExistException;
import de.kaiserpfalzedv.office.tenant.api.TenantExistsException;
import de.kaiserpfalzedv.office.tenant.api.TenantService;
import de.kaiserpfalzedv.office.tenant.api.impl.TenantBuilder;
import de.kaiserpfalzedv.office.tenant.client.impl.TenantClientImpl;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Node;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.fail;

/**
 * The small test runner for the Cocumber tests.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-04
 */
@RunWith(CukeSpace.class)
@Features({"src/test/resources/de/kaiserpfalzedv/office/tenant"})
public class TenantServiceAT {
    private static final Logger LOG = LoggerFactory.getLogger(TenantServiceAT.class);

    private static ConfigReader config = new ConfigReaderBuilder().build();
    private static MessagingCore core = new ActiveMQMessagingCoreImpl(config);

    static {
        try {
            core.init();
        } catch (InitializationException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);

            throw new BaseSystemException(e);
        }
    }

    private TenantService service;

    private ArrayList<Exception> exceptions = new ArrayList<>();
    private Collection<Tenant> tenants = new HashSet<>();

    @Deployment
    public static EnterpriseArchive createDeployment() {
        File pomFile = new File("pom.xml");
        LOG.info("Loading POM pomFile: {}", pomFile.getAbsolutePath());

        File[] lib = Maven.resolver()
                          .loadPomFromFile(pomFile)
                          .resolve(
                                  "de.kaiserpfalz-edv.office:kp-office-base-api",
                                  "de.kaiserpfalz-edv.office:kp-office-commons-impl",
                                  "ch.qos.logback:logback-classic"
                          )
                          .withTransitivity()
                          .as(File.class);

        EnterpriseArchive war = ShrinkWrap
                .create(EnterpriseArchive.class, "tenant.ear")
                .addManifest();

        LOG.info("Created {}.", war.getName());

        war
                .addAsLibraries(lib);
        // .addPackages(true, "de.kaiserpfalzedv.office.tenant");

        LOG.info("Added libraries and packages.");

        Map<String, File> resources = new HashMap<>();
        File metainfResurces = new File("src/test/resources/META-INF");
        addResource(resources, "/", metainfResurces);
        for (Map.Entry<String, File> r : resources.entrySet()) {
            war.addAsManifestResource(r.getValue(), r.getKey());
        }

        LOG.info("Added resources to META-INF/");

        resources = new HashMap<>();
        File webinfClasses = new File("src/test/resources");
        addResource(resources, "/classes/", webinfClasses);

        for (Map.Entry<String, File> r : resources.entrySet()) {
            // war.addAsWebInfResource(r.getValue(), r.getKey());
        }

        LOG.info("Added resources to WEB-INF/");

        listFiles(war.get("/"), 0);

        ZipExporter archive = war.as(ZipExporter.class);
        archive.exportTo(new File("target/tenant.war"), true);

        return war;
    }

    private static void addResource(Map<String, File> resources, String path, File node) {
        if (node.isDirectory()) {
            //noinspection ConstantConditions
            for (File n : node.listFiles()) {
                addResource(resources, path + n.getName() + (n.isDirectory() ? "/" : ""), n);
            }

        }

        if (node.isFile()) {
            LOG.info("Adding Resource to {}: {}", path, node.getAbsolutePath());

            resources.put(path, node);
        }
    }

    private static void listFiles(Node node, int level) {
        if (node.getAsset() != null) {
            LOG.info("{} {}: {}", pathdepth(level), node.getPath(), node.getAsset());
        } else {
            LOG.info("{} {}: Directory", pathdepth(level), node.getPath());
        }

        for (Node n : node.getChildren()) {
            listFiles(n, level + 1);
        }
    }

    private static String pathdepth(int level) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < level - 1; i++) {
            result.append("|");
        }

        result.append("+->");

        return result.toString();
    }

    @Given(".*tenant '([a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12})' does not exist in the system.*")
    public void ensureTenantDoesNotExist(final String id) {
        UUID tenant = UUID.fromString(id);

        service.delete(tenant);
    }

    @Given(".*tenant '([a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12})' with name '(.+)' is existing.*")
    public void ensureTenantDoesExist(final String id, final String name) throws TenantExistsException {
        UUID uuid = UUID.fromString(id);

        Tenant tenant = new TenantBuilder()
                .withId(uuid)
                .withDisplayName(name)
                .build();

        service.create(tenant);
    }

    @When(".*tenant '([a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12})' with name '(.+)' should be created.*")
    public void createTenant(final String id, final String name) {
        Tenant tenant = new TenantBuilder()
                .withId(UUID.fromString(id))
                .withFullName(name)
                .build();

        try {
            service.create(tenant);
        } catch (TenantExistsException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);

            exceptions.add(e);
        }
    }

    @When(".*retrieving the set of tenants.*")
    public void retrieveAllTenants() {
        tenants = service.retrieve();
    }

    @When(".*retrieving tenant '([a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12})'.*")
    public void retrieveTenant(final UUID id) {
        try {
            service.retrieve(id);
        } catch (TenantDoesNotExistException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);

            exceptions.add(e);
        }
    }

    @When("updating tenant '([a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12})' with the name '(.+)'")
    public void updateTenant(final UUID id, final String name) {
        try {
            Tenant orig = service.retrieve(id);

            Tenant tenant = new TenantBuilder()
                    .withTenant(orig)
                    .withDisplayName(name)
                    .build();

            service.update(tenant);
        } catch (TenantDoesNotExistException | TenantExistsException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);

            exceptions.add(e);
        }
    }

    @When(".*deleting tenant '([a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12})'.*")
    public void deleteTenant(final String id) {
        UUID uuid = UUID.fromString(id);

        service.delete(uuid);
    }

    @Then(".+ tenant '([a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12})' should exist.*")
    public void checkForExistingTenant(final String id) throws TenantDoesNotExistException {
        UUID uuid = UUID.fromString(id);

        service.retrieve(uuid);
    }

    @Then(".*should be no tenant '([a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12})' in the system.*")
    public void checkThatTenantDoesNotExist(final String id) {
        UUID uuid = UUID.fromString(id);

        try {
            service.retrieve(uuid);
        } catch (TenantDoesNotExistException e) {
            return;
        }

        fail("The tenant with id '" + id + "' does exist.");
    }

    @Then(".*tenant '([a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12})' should have the name '(.+)'.*")
    public void checkTenantData(final String id, final String name) throws TenantDoesNotExistException {
        UUID uuid = UUID.fromString(id);

        Tenant tenant = service.retrieve(uuid);

        if (!name.equals(tenant.getDisplayName())) {
            fail("The name '" + tenant.getDisplayName() + "' does not match the wanted name '" + name + "'.");
        }
    }

    @Then(".*failure should be generated pointing to tenant '([a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12})'.*")
    public void checkForException(final String id) {
        UUID uuid = UUID.fromString(id);

        TenantExistsException tee = null;
        for (Exception e : exceptions) {
            if (TenantExistsException.class.isAssignableFrom(e.getClass())) {
                tee = (TenantExistsException) e;

                if (tee.getObjectId().equals(uuid)) {
                    break;
                } else {
                    tee = null;
                }
            }
        }

        if (tee != null) {
            exceptions.remove(tee);
            return;
        }

        fail("There should have been an exception for tenant with id '" + id + "'.");
    }

    @Then(".*'(\\d+)' tenants should be returned.*")
    public void checkNumberOfResults(final long count) {
        if (tenants.size() != count) {
            fail("There should have been " + count + " results instead of the current " + tenants.size()
                         + " results in the result set!");
        }
    }

    @Then(".*tenant '([a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12})' should be in the result set.*")
    public void checkResultSet(final UUID id) {
        for (Tenant t : tenants) {
            if (t.getId().equals(id))
                return;
        }

        fail("The tenant with id '" + id.toString() + "' has not been found in result set!");
    }

    @Before
    public void setupService() throws InitializationException {
        service = new TenantClientImpl(config, core);
    }

    @After
    public void teardownService() {
        if (service != null) {
            service.close();
        }
    }
}
