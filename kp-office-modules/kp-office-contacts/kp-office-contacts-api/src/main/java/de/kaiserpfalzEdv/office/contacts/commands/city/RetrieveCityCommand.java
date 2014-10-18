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

package de.kaiserpfalzEdv.office.contacts.commands.city;

import de.kaiserpfalzEdv.commons.paging.PagingRequest;
import de.kaiserpfalzEdv.office.core.KPOEntityQuery;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;

/**
 * @author klenkes
 * @since 2014Q
 */
public class RetrieveCityCommand extends CityBaseCommand {
    private static final long serialVersionUID = 1L;

    private KPOEntityQuery query;
    private PagingRequest paging;


    @Deprecated // Only for Jackson, JAX-B and JPA!
    public RetrieveCityCommand() {
    }


    @SuppressWarnings("deprecation")
    public RetrieveCityCommand(@NotNull final KPOEntityQuery query, @NotNull final PagingRequest paging) {
        setQuery(query);
        setPaging(paging);
    }


    public KPOEntityQuery getQuery() {
        return query;
    }

    @Deprecated // Only for Jackson, JAX-B and JPA!
    public void setQuery(@NotNull final KPOEntityQuery query) {
        this.query = query;
    }


    public PagingRequest getPaging() {
        return paging;
    }

    @Deprecated // Only for Jackson, JAX-B and JPA!
    public void setPaging(@NotNull final PagingRequest paging) {
        this.paging = paging;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append(query)
                .append(paging)
                .toString();
    }
}
