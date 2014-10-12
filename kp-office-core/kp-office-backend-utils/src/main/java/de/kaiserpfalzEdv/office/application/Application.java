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

package de.kaiserpfalzEdv.office.application;

import com.beust.jcommander.JCommander;
import de.kaiserpfalzEdv.commons.db.LiquibaseDatabaseInitializer;
import de.kaiserpfalzEdv.office.OfficeSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Generic application for all services using the spring boot mechanism.
 *
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
@Configuration
@ImportResource("/beans.xml")
public class Application {
    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        Parameters parameters = new Parameters();
        new JCommander(parameters, args);

        boolean slf4jBridgeAlreadyInstalled = SLF4JBridgeHandler.isInstalled();
        if (!slf4jBridgeAlreadyInstalled)
            SLF4JBridgeHandler.install();

        if (parameters.updateDatabase) {
            try {
                InitialContext ic = new InitialContext();
                DataSource dataSource = (DataSource) ic.lookup("java:comp/env/db");
                String changelog = "classpath:/ddl/db-changelog.xml";
                boolean dropFirst = (Boolean) ic.lookup("java:comp/env/dropDatabase");
                ic.close();

                LiquibaseDatabaseInitializer initializer = new LiquibaseDatabaseInitializer(dataSource, changelog, dropFirst);

                initializer.initializeDatabase();
            } catch (NamingException | OfficeSystemException e) {
                LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);

                System.err.println("Database could not be initialized: " + e.getMessage());
                System.exit(1);
            }
        }

        LOG.info("Starting {} ...");
        SpringApplication.run(Application.class, args);
        LOG.info("Stopped {}.", Application.class);

        if (!slf4jBridgeAlreadyInstalled)
            SLF4JBridgeHandler.uninstall();
    }
}

