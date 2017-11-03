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

package de.kaiserpfalzedv.commons.impl.info;

import com.github.zafarkhaja.semver.Version;
import de.kaiserpfalzedv.commons.api.BuilderException;
import de.kaiserpfalzedv.commons.api.info.DataSchemaChange;
import org.apache.commons.lang3.builder.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 1.0.0
 */
public class DataSchemaChangeBuilder implements Builder<DataSchemaChange> {
    private static final Logger LOG = LoggerFactory.getLogger(DataSchemaChangeBuilder.class);

    private String id;
    private String author;
    private LocalDate dateExecuted;
    private int orderExecuted = -1;
    private String execType;
    private String md5Sum;
    private String description;
    private String comments;
    private String tag;
    private Version liquibaseVersion;
    private String contexts;
    private String label;


    @Override
    public DataSchemaChange build() {
        validate();

        DataSchemaChangeTO result = new DataSchemaChangeTO(id, author, dateExecuted, orderExecuted, execType, md5Sum,
                liquibaseVersion);

        if (isNotBlank(description)) result.setDescription(description);
        if (isNotBlank(comments)) result.setComments(comments);
        if (isNotBlank(tag)) result.setTag(tag);
        if (isNotBlank(contexts)) result.setContexts(contexts);
        if (isNotBlank(label)) result.setLabel(label);

        return result;
    }


    private void validate() {
        ArrayList<String> failures = new ArrayList<>();

        if (isBlank(id)) failures.add("DataSchemaChange needs an id.");
        if (isBlank(author)) failures.add("DataSchemaChange needs an author.");
        if (dateExecuted == null) failures.add("DataSchemaChange needs an execution date.");
        if (orderExecuted == -1) failures.add("DataSchemaChange needs an execution order.");
        if (isBlank(execType)) failures.add("DataSchemaChange needs an execution type.");
        if (isBlank(md5Sum)) failures.add("DataSchemaChange needs an MD5 sum.");
        if (liquibaseVersion == null) failures.add("DataSchemaChange needs an liquibase execution version.");

        if (failures.size() > 0) {
            throw new BuilderException(DataSchemaChangeTO.class, failures);
        }
    }


    public DataSchemaChangeBuilder withChange(@NotNull final DataSchemaChange change) {
        withId(change.getId());
        withAuthor(change.getAuthor());
        withDateExecuted(change.getDateExecuted());
        withOrderExecuted(change.getOrderExecuted());
        withExecType(change.getExecType());
        withMd5Sum(change.getMD5Sum());
        withDescription(change.getDescription());
        withComments(change.getComments());
        withTag(change.getTag());
        withLiquibaseVersion(change.getLiquibaseVersion());
        withContexts(change.getContexts());
        withLabel(change.getLabel());

        return this;
    }


    public DataSchemaChangeBuilder withId(@NotNull String id) {
        this.id = id;
        return this;
    }

    public DataSchemaChangeBuilder withAuthor(@NotNull String author) {
        this.author = author;
        return this;
    }

    public DataSchemaChangeBuilder withDateExecuted(@NotNull LocalDate dateExecuted) {
        this.dateExecuted = dateExecuted;
        return this;
    }

    public DataSchemaChangeBuilder withOrderExecuted(@NotNull int orderExecuted) {
        this.orderExecuted = orderExecuted;
        return this;
    }

    public DataSchemaChangeBuilder withExecType(@NotNull String execType) {
        this.execType = execType;
        return this;
    }

    public DataSchemaChangeBuilder withMd5Sum(@NotNull String md5Sum) {
        this.md5Sum = md5Sum;
        return this;
    }

    public DataSchemaChangeBuilder withDescription(@NotNull String description) {
        this.description = description;
        return this;
    }

    public DataSchemaChangeBuilder withComments(@NotNull String comments) {
        this.comments = comments;
        return this;
    }

    public DataSchemaChangeBuilder withTag(@NotNull String tag) {
        this.tag = tag;
        return this;
    }

    public DataSchemaChangeBuilder withLiquibaseVersion(@NotNull Version liquibaseVersion) {
        this.liquibaseVersion = liquibaseVersion;
        return this;
    }

    public DataSchemaChangeBuilder withContexts(@NotNull String contexts) {
        this.contexts = contexts;
        return this;
    }

    public DataSchemaChangeBuilder withLabel(@NotNull String label) {
        this.label = label;
        return this;
    }
}
