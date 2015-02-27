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

package de.kaiserpfalzEdv.office.contacts.geodb.impl;

import com.mysema.query.types.Predicate;
import de.kaiserpfalzEdv.commons.jee.paging.Page;
import de.kaiserpfalzEdv.commons.jee.paging.PageDO;
import de.kaiserpfalzEdv.commons.jee.paging.Pageable;
import de.kaiserpfalzEdv.commons.jee.paging.PageableDO;
import de.kaiserpfalzEdv.office.commons.KPO;
import de.kaiserpfalzEdv.office.contacts.geodb.PostCode;
import de.kaiserpfalzEdv.office.contacts.geodb.spi.InvalidQueryException;
import de.kaiserpfalzEdv.office.contacts.geodb.spi.NoSuchPostCodeException;
import de.kaiserpfalzEdv.office.contacts.geodb.spi.PostCodeProvider;
import de.kaiserpfalzEdv.office.contacts.geodb.spi.PostCodeQuery;
import org.springframework.cache.annotation.Cacheable;

import javax.inject.Inject;
import javax.inject.Named;

import static de.kaiserpfalzEdv.office.commons.KPO.Type.Implementation;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 25.02.15 14:04
 */
@Named
@KPO(Implementation)
public class KPOInternalPostCodeProvider implements PostCodeProvider {
    @Inject
    private PostCodeRepository repository;


    @Cacheable("postCode-single")
    @Override
    public PostCode findOne(PostCodeQuery query) throws InvalidQueryException, NoSuchPostCodeException {
        Predicate condition = PostCodeQueryParser.withQuery(query);

        Iterable<KPOInternalPostCodeImpl> result = repository.findAll(condition);

        if (!result.iterator().hasNext()) {
            throw new NoSuchPostCodeException(query);
        }

        return result.iterator().next();
    }

    @Cacheable("postcode-multiple")
    @Override
    public Page<PostCode> findAll(PostCodeQuery query, Pageable pageable) throws InvalidQueryException {
        Predicate condition = PostCodeQueryParser.withQuery(query);
        org.springframework.data.domain.Pageable paging = ((PageableDO) pageable).getPageable();

        return new PageDO<>(repository.findAll(condition, paging));
    }

    @Cacheable("postcode-multiple")
    @Override
    public Page<PostCode> findAll(Pageable pageable) {
        org.springframework.data.domain.Pageable paging = ((PageableDO) pageable).getPageable();

        return new PageDO<>(repository.findAll(paging));
    }

    @Override
    public String getDisplayName() {
        return KPOInternalPostCodeProvider.class.getSimpleName();
    }
}
