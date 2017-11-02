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

package de.kaiserpfalzedv.commons.api.metainfo;

import com.github.zafarkhaja.semver.Version;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Information to the database schema of the software. Yes, its the data provided by
 * {@link http://www.liquibase.org Liquibase}.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 1.0.0
 */
public interface DataSchemaChange extends Serializable {
    /**
     * @return the id of the database change.
     */
    String getId();

    /**
     * @return The name of the author of the change.
     */
    String getAuthor();

    /**
     * @return The date of the database change.
     */
    LocalDate getDateExecuted();

    /**
     * @return The order of the execution.
     */
    int getOrderExecuted();

    /**
     * @return The execution type.
     */
    String getExecType();

    /**
     * @return The MD5 sum of the change.
     */
    String getMD5Sum();

    /**
     * @return The description of the change.
     */
    String getDescription();

    /**
     * @return The comments of the change.
     */
    String getComments();

    /**
     * @return The tag of this change.
     */
    String getTag();

    /**
     * @return The liquibase version executing the change.
     */
    Version getLiquibaseVersion();

    /**
     * @return The context of the change.
     */
    String getContexts();

    /**
     * @return The label of the change.
     */
    String getLabel();
}
