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

package de.kaiserpfalzedv.commons.ejb.config;

import liquibase.integration.cdi.CDILiquibaseConfig;
import liquibase.integration.cdi.annotations.LiquibaseType;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.ResourceAccessor;

import javax.enterprise.inject.Produces;

/**
 * The configuration provider for the liquibase database generator/updater.
 *
 * Needs to be included like this:
 *
 * <code><pre>
 * &lt;ejb-jar xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 *     xmlns="http://xmlns.jcp.org/xml/ns/javaee"
 *     version="3.2"
 *     xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/ejb-jar_3_2.xsd"&gt;
 *
 *     &lt;module-name&gt;office-licenses&lt;/module-name&gt;
 *     &lt;description&gt;${project.description}&lt;/description&gt;
 *     &lt;display-name&gt;${project.name}&lt;/display-name&gt;
 *
 *     &lt;enterprise-beans&gt;
 *         &lt;session&gt;
 *             &lt;ejb-name&gt;LiquibaseConfigurationProvider&lt;/ejb-name&gt;
 *             &lt;ejb-class&gt;LiquibaseConfigurationProvider&lt;/ejb-class&gt;
 *             &lt;session-type&gt;Singleton&lt;/session-type&gt;
 *             &lt;init-on-startup&gt;true&lt;/init-on-startup&gt;
 *
 *             &lt;resource-env-ref&gt;
 *                 &lt;resource-env-ref-name&gt;jdbc/datasource&lt;/resource-env-ref-name&gt;
 *                 &lt;resource-env-ref-type&gt;javax.sql.DataSource&lt;/resource-env-ref-type&gt;
 *                 &lt;lookup-name&gt;java:jboss/datasources/ExampleDS&lt;/lookup-name&gt;
 *             &lt;/resource-env-ref&gt;
 *         &lt;/session&gt;
 *     &lt;/enterprise-beans&gt;
 * &lt;/ejb-jar&gt;
 * </pre></code>
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-08-17
 */
public class LiquibaseConfigurationProvider {

    @Produces
    @LiquibaseType
    public CDILiquibaseConfig createConfig() {
        CDILiquibaseConfig config = new CDILiquibaseConfig();
        config.setChangeLog("ddl/changelog-master.xml");
        return config;
    }

    @Produces
    @LiquibaseType
    public ResourceAccessor create() {
        return new ClassLoaderResourceAccessor(getClass().getClassLoader());
    }
}
