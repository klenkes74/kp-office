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

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.jpa.boot.spi.TypeContributorList;
import org.hibernate.metamodel.spi.TypeContributor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author klenkes
 * @since 2014Q
 */
public class CustomHibernateTypes implements TypeContributorList {
    private static final Logger LOG = LoggerFactory.getLogger(CustomHibernateTypes.class);

    private final ArrayList<TypeContributor> contributors = new ArrayList<>();

    @Override
    public List<TypeContributor> getTypeContributors() {
        return contributors;
    }

    public void setTypeContributors(Collection<TypeContributor> contributors) {
        this.contributors.clear();

        if (contributors != null) {
            this.contributors.addAll(contributors);
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("contributors", contributors)
                .toString();
    }
}