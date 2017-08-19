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

package de.kaiserpfalzedv.office.license.impl;

import java.sql.SQLException;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.sql.DataSource;

import de.kaiserpfalzedv.office.common.ejb.config.LiquibaseConfigurationProvider;
import liquibase.integration.cdi.annotations.LiquibaseType;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-08-17
 */
@ApplicationScoped
public class LiquibaseConfiguration extends LiquibaseConfigurationProvider {
    @Resource(lookup = "java:comp/env/jdbc/datasource")
    private DataSource dataSource;

    @Produces
    @LiquibaseType
    public DataSource createDataSource() throws SQLException {
        return dataSource;
    }


}
