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

package de.kaiserpfalzEdv.commons.db;

import de.kaiserpfalzEdv.office.OfficeSystemException;
import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.sql.DataSource;

/**
 * Initializes the database with liquibase.
 *
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public class LiquibaseDatabaseInitializer implements DatabaseInitializer {
    private static final Logger LOG = LoggerFactory.getLogger(LiquibaseDatabaseInitializer.class);

    private DataSource dataSource;
    private String changeLog;
    private boolean dropFirst;

    @Inject
    public LiquibaseDatabaseInitializer(
            final DataSource dataSource,
            @Value("${db.changelog}") final String changeLog,
            @Value("${db.dropFirst}") final boolean dropFirst
    ) {
        this.dataSource = dataSource;
        this.changeLog = changeLog;
        this.dropFirst = dropFirst;

        LOG.trace("Created: {}", this);
    }


    @PreDestroy
    public void close() {
        LOG.trace("Destroyed: {}", this);
    }


    @Override
    public void initializeDatabase() {
        LOG.info("Updating database definition ...");

        try {
            SpringLiquibase liquibase = new SpringLiquibase();
            liquibase.setChangeLog(changeLog);
            liquibase.setDataSource(dataSource);
            liquibase.setDropFirst(dropFirst);
            liquibase.setShouldRun(true);

            liquibase.afterPropertiesSet();
        } catch (LiquibaseException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);

            throw new OfficeSystemException("Can't initialize database!");
        }

    }


    @Override
    public DataSource getDataSource() {
        return dataSource;
    }

    @Override
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public String getChangeLog() {
        return changeLog;
    }

    public void setChangeLog(String changeLog) {
        this.changeLog = changeLog;
    }

    public boolean isDropFirst() {
        return dropFirst;
    }

    public void setDropFirst(boolean dropFirst) {
        this.dropFirst = dropFirst;
    }
}
