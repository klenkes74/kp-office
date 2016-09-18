/*
 * Copyright 2016 Kaiserpfalz EDV-Service, Roland T. Lichti
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.inject.Inject;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.runtime.arquillian.CukeSpace;
import cucumber.runtime.arquillian.api.Features;
import de.kaiserpfalzedv.office.tenant.impl.TenantBuilder;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Node;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
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
@Features({"src/test/resources/de/kaiserpfalzedv/office/tenant/CreateTenant.feature"})
public class TenantServiceAT {
    private static final Logger LOG = LoggerFactory.getLogger(TenantServiceAT.class);
    @Inject
    private TenantService service;
    private ArrayList<Exception> exceptions = new ArrayList<>();
    private Set<? extends Tenant> tenants = new HashSet<>();
    private Tenant tenant;

    @Deployment
    public static WebArchive createDeployment() {
        File pomFile = new File("pom.xml");
        LOG.info("Loading POM pomFile: {}", pomFile.getAbsolutePath());

        File[] lib = Maven.resolver()
                          .loadPomFromFile(pomFile)
                          .resolve(
                                  "de.kaiserpfalz-edv.office:kp-office-base-api",
                                  "de.kaiserpfalz-edv.office:kp-office-commons-impl"
                          )
                          .withTransitivity()
                          .as(File.class);

        WebArchive war = ShrinkWrap.create(WebArchive.class, "tenant.war");

        LOG.info("Created {}.", war.getName());

        war
                .addAsLibraries(lib)
                .addPackages(true, "de.kaiserpfalzedv.office.tenant");

        LOG.info("Added libraries and packages.");

        Map<String, File> resources = new HashMap<>();
        File metainfResurces = new File("src/test/resources/META-INF");
        addResource(resources, "/", metainfResurces);
        for (Map.Entry<String, File> r : resources.entrySet()) {
            war.addAsManifestResource(r.getValue(), r.getKey());
        }

        LOG.info("Added resources to META-INF/");

        resources = new HashMap<>();
        File webinfClasses = new File("src/test/webapp");
        addResource(resources, "/classes/", webinfClasses);

        for (Map.Entry<String, File> r : resources.entrySet()) {
            war.addAsWebInfResource(r.getValue(), r.getKey());
        }

        LOG.info("Added resources to WEB-INF/");

        listFiles(war.get("/"), 0);

        ZipExporter archive = war.as(ZipExporter.class);
        archive.exportTo(new File("target/tenant.war"), true);

        return war;
    }

    private static void addResource(Map<String, File> resources, String path, File node) {
        if (node.isDirectory()) {
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

        service.deleteTenant(tenant);
    }

    @Given(".*tenant '([a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12})' with name '(.+)' is existing.*")
    public void ensureTenantDoesExist(final String id, final String name) throws TenantExistsException {
        UUID uuid = UUID.fromString(id);

        Tenant tenant = new TenantBuilder()
                .withId(uuid)
                .withDisplayName(name)
                .build();

        service.createTenant(tenant);
    }


    @When(".*tenant '([a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12})' with name '(.+)' should be created.*")
    public void createTenant(final String id, final String name) {
        Tenant tenant = new TenantBuilder()
                .withId(UUID.fromString(id))
                .withFullName(name)
                .build();

        try {
            service.createTenant(tenant);
        } catch (TenantExistsException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);

            exceptions.add(e);
        }
    }

    @When(".*retrieving the set of tenants.*")
    public void retrieveAllTenants() {
        tenants = service.retrieveTenants();
    }


    @When(".*retrieving tenant '([a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12})'.*")
    public void retrieveTenant(final UUID id) {
        try {
            tenant = service.retrieveTenant(id);
        } catch (TenantDoesNotExistException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);

            exceptions.add(e);
        }
    }

    @When("updating tenant '([a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12})' with the name '(.+)'")
    public void updateTenant(final UUID id, final String name) {
        try {
            Tenant orig = service.retrieveTenant(id);

            Tenant tenant = new TenantBuilder()
                    .withTenant(orig)
                    .withDisplayName(name)
                    .build();

            service.updateTenant(tenant);
        } catch (TenantDoesNotExistException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);

            exceptions.add(e);
        }
    }


    @When(".*deleting tenant '([a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12})'.*")
    public void deleteTenant(final String id) {
        UUID uuid = UUID.fromString(id);

        service.deleteTenant(uuid);
    }


    @Then(".+ tenant '([a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12})' should exist.*")
    public void checkForExistingTenant(final String id) throws TenantDoesNotExistException {
        UUID uuid = UUID.fromString(id);

        service.retrieveTenant(uuid);
    }

    @Then(".*should be no tenant '([a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12})' in the system.*")
    public void checkThatTenantDoesNotExist(final String id) {
        UUID uuid = UUID.fromString(id);

        try {
            service.retrieveTenant(uuid);
        } catch (TenantDoesNotExistException e) {
            return;
        }

        fail("The tenant with id '" + id + "' does exist.");
    }


    @Then(".*tenant '([a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12})' should have the name '(.+)'.*")
    public void checkTenantData(final String id, final String name) throws TenantDoesNotExistException {
        UUID uuid = UUID.fromString(id);

        Tenant tenant = service.retrieveTenant(uuid);

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
}
