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

package de.kaiserpfalzEdv.office.tenants.query;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
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
            LOG.info("Updating database definition ...");

            try {
                InitialContext ic = new InitialContext();
                DataSource db = (DataSource) ic.lookup("java:comp/env/db/kpotenant");
                boolean dropFirst = (Boolean) ic.lookup("java:comp/env/dropDatabase");
                ic.close();

                SpringLiquibase liquibase = new SpringLiquibase();
                liquibase.setChangeLog("classpath:/ddl/db-changelog.xml");
                liquibase.setDataSource(db);
                liquibase.setDropFirst(dropFirst);
                liquibase.setShouldRun(true);
                liquibase.afterPropertiesSet();
            } catch (NamingException | LiquibaseException e) {
                LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);
            }
        }

        LOG.info("Starting {}", Application.class);
        SpringApplication.run(Application.class, args);
        LOG.info("Stopped {}", Application.class);

        if (!slf4jBridgeAlreadyInstalled)
            SLF4JBridgeHandler.uninstall();
    }
}

class Parameters {
    @Parameter(names = "-updateDatabase", description = "Updates the database to the current data definition")
    boolean updateDatabase = false;
}

