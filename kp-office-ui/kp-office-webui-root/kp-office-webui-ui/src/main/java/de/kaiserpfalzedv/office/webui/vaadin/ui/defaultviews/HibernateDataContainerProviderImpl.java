/*
 * Copyright 2016 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzEdv.vaadin.ui.defaultviews;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.addon.jpacontainer.LazyLoadingDelegate;
import com.vaadin.spring.annotation.UIScope;
import de.kaiserpfalzEdv.vaadin.backend.db.BaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 16.09.15 06:32
 */
@Named
@UIScope
public class HibernateDataContainerProviderImpl implements DataContainerProvider, Serializable {
    private static final long   serialVersionUID = 6387686442085775124L;
    private static final Logger LOG              = LoggerFactory.getLogger(DataContainerProvider.class);


    private EntityManager       em;
    private LazyLoadingDelegate lazyLoadingDelegate;


    @Inject
    public HibernateDataContainerProviderImpl(
            @SuppressWarnings("SpringJavaAutowiringInspection") final EntityManager em,
            final LazyLoadingDelegate lazyLoadingDelegate
    ) {
        this.em = em;
        this.lazyLoadingDelegate = lazyLoadingDelegate;
    }


    @Override
    public <T extends BaseEntity> JPAContainer<T> getReadOnlyContainer(Class<T> clasz) {
        LOG.debug("Creating JPAContainer for class '{}'.", clasz.getSimpleName());

        JPAContainer<T> result = JPAContainerFactory.makeReadOnly(clasz, em);
        result.getEntityProvider().setLazyLoadingDelegate(lazyLoadingDelegate);

        return result;
    }

    @Override
    public <T extends BaseEntity> JPAContainer<T> getContainer(Class<T> clasz) {
        LOG.debug("Creating JPAContainer for class '{}'.", clasz.getSimpleName());

        JPAContainer<T> result = JPAContainerFactory.make(clasz, em);
        result.getEntityProvider().setLazyLoadingDelegate(lazyLoadingDelegate);

        return result;
    }
}