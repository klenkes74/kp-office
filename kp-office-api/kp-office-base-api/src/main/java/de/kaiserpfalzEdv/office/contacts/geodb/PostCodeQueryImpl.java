/*
 * Copyright 2015 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzEdv.office.contacts.geodb;

import de.kaiserpfalzEdv.office.contacts.geodb.spi.PostCodeQuery;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 25.02.15 22:37
 */
public class PostCodeQueryImpl implements PostCodeQuery {
    private static final long serialVersionUID = 807509222158848645L;


    private final LinkedHashSet<QueryParameter> parameters = new LinkedHashSet<>(5);


    @Deprecated // Only for Jackson, JAX-B, JPA, ...
    protected PostCodeQueryImpl() {}

    @SuppressWarnings("deprecation")
    PostCodeQueryImpl(@NotNull final Collection<? extends QueryParameter> parameters) {
        this.parameters.addAll(parameters);
    }


    @Override
    public Set<QueryParameter> getParameters() {
        return parameters;
    }

    @Deprecated // Only for Jackson, JAX-B, JPA, ...
    public void setParameters(Set<? extends QueryParameter> parameters) {
        this.parameters.clear();

        this.parameters.addAll(parameters);
    }
}
