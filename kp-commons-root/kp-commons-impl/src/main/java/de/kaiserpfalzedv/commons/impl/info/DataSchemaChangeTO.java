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
import de.kaiserpfalzedv.commons.api.info.DataSchemaChange;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 1.0.0
 */
public class DataSchemaChangeTO implements DataSchemaChange {
    private String id;
    private String author;
    private LocalDate dateExecuted;
    private int orderExecuted;
    private String execType;
    private String md5Sum;
    private String description;
    private String comments;
    private String tag;
    private Version liquibaseVersion;
    private String contexts;
    private String label;

    DataSchemaChangeTO(
            @NotNull final String id,
            @NotNull final String author,
            @NotNull final LocalDate dateExecuted,
            @NotNull final int orderExecuted,
            @NotNull final String execType,
            @NotNull final String md5Sum,
            @NotNull final Version liquibaseVersion
    ) {
        this.id = id;
        this.author = author;
        this.dateExecuted = dateExecuted;
        this.orderExecuted = orderExecuted;
        this.execType = execType;
        this.md5Sum = md5Sum;
        this.liquibaseVersion = liquibaseVersion;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public LocalDate getDateExecuted() {
        return dateExecuted;
    }

    @Override
    public int getOrderExecuted() {
        return orderExecuted;
    }

    @Override
    public String getExecType() {
        return execType;
    }

    @Override
    public String getMD5Sum() {
        return md5Sum;
    }

    @Override
    public String getDescription() {
        return description;
    }

    void setDescription(@NotNull String description) {
        this.description = description;
    }

    @Override
    public String getComments() {
        return comments;
    }

    void setComments(@NotNull String comments) {
        this.comments = comments;
    }

    @Override
    public String getTag() {
        return tag;
    }

    void setTag(@NotNull String tag) {
        this.tag = tag;
    }

    @Override
    public Version getLiquibaseVersion() {
        return liquibaseVersion;
    }

    @Override
    public String getContexts() {
        return contexts;
    }

    void setContexts(@NotNull String contexts) {
        this.contexts = contexts;
    }

    @Override
    public String getLabel() {
        return label;
    }

    void setLabel(@NotNull String label) {
        this.label = label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof DataSchemaChangeTO)) return false;

        DataSchemaChangeTO that = (DataSchemaChangeTO) o;

        return new EqualsBuilder()
                .append(getId(), that.getId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getId())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("author", author)
                .append("dateExecuted", dateExecuted)
                .append("description", description)
                .append("comments", comments)
                .append("tag", tag)
                .append("label", label)
                .toString();
    }
}
