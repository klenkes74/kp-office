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

package de.kaiserpfalzedv.office.common.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.office.common.api.data.Nameable;

/**
 * This small embeddable class ensures that all tables containing nameables will look the same.
 * <p>
 * <p>For Liquibase the following configuration will be needed:</p>
 * <p>
 * <pre>
 *     &lt;column name="DISPLAY_NAME_" type="VARCHAR(200)"&gt;
 *         &lt;constraints nullable="false"/&gt;
 *     &lt;/column&gt;
 *
 *     &lt;column name="FULL_NAME_" type="VARCHAR(1000)"&gt;
 *         &lt;constraints nullable="false"/&gt;
 *     &lt;/column&gt;
 * </pre>
 * <p>
 * <p>In normal circumstances, the entries should be unique for every tenant:</p>
 * <p>
 * <pre>
 *     &lt;createIndex indexName="&lt;em&gt;TABLE_NAME&lt;/em&gt;_DISPLAY_NAME_UK" unique="true" tableName="&lt;em&gt;TABLE_NAME&lt;/em&gt;"&gt;
 *         &lt;column name="TENANT_"/&gt;
 *         &lt;column name="DISPLAY_NAME_"/&gt;
 *     &lt;/createIndex&gt;
 *
 *     &lt;createIndex indexName="&lt;em&gt;TABLE_NAME&lt;/em&gt;_FULL_NAME_UK" unique="true" tableName="&lt;em&gt;TABLE_NAME&lt;/em&gt;"&gt;
 *         &lt;column name="TENANT_"/&gt;
 *         &lt;column name="FULL_NAME_"/&gt;
 *     &lt;/createIndex&gt;
 * </pre>
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-30
 */
@Embeddable
public class JPANameable implements Nameable, Serializable {
    private static final long serialVersionUID = 10597843536045535L;


    private String displayName;
    private String fullName;


    @Deprecated // Only for JPA
    protected JPANameable() {}

    public JPANameable(
            @NotNull final String displayName,
            @NotNull final String fullName
    ) {
        setDisplayName(displayName);
        setFullName(fullName);
    }


    @Column(name = "DISPLAY_NAME_", length = 200, nullable = false)
    @Override
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }


    @Column(name = "FULL_NAME_", length = 1000, nullable = false)
    @Override
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
