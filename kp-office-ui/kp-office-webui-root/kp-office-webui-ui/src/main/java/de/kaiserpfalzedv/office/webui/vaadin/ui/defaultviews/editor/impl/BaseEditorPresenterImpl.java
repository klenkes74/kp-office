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

package de.kaiserpfalzEdv.vaadin.ui.defaultviews.editor.impl;

import com.google.common.eventbus.EventBus;
import com.vaadin.addon.jpacontainer.JPAContainer;
import de.kaiserpfalzEdv.vaadin.backend.db.BaseEntity;
import de.kaiserpfalzEdv.vaadin.backend.db.EntityNotFoundException;
import de.kaiserpfalzEdv.vaadin.backend.db.EntityNotRemovedException;
import de.kaiserpfalzEdv.vaadin.backend.db.EntityNotSavedException;
import de.kaiserpfalzEdv.vaadin.backend.db.EntityService;
import de.kaiserpfalzEdv.vaadin.ui.defaultviews.DataContainerProvider;
import de.kaiserpfalzEdv.vaadin.ui.defaultviews.editor.BaseEditorView;
import de.kaiserpfalzEdv.vaadin.ui.mvp.impl.BasePresenter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.jpa.JpaSystemException;

import javax.annotation.PreDestroy;
import java.util.UUID;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 12.09.15 22:51
 */
public abstract class BaseEditorPresenterImpl<T extends BaseEntity, V extends BaseEditorView> extends BasePresenter<V> {
    private static final Logger LOG = LoggerFactory.getLogger(BaseEditorPresenterImpl.class);


    protected EntityService<T> service;
    protected DataContainerProvider containerProvider;
    protected EventBus bus;

    private T data;


    public BaseEditorPresenterImpl(
            final EntityService<T> service,
            final DataContainerProvider containerProvider,
            final EventBus bus
    ) {
        this.service = service;
        this.containerProvider = containerProvider;

        this.bus = bus;
        this.bus.register(this);
    }

    @PreDestroy
    public void close() {
        bus.unregister(this);
    }


    /**
     * Creates a new editor object.
     */
    public abstract T createNew();


    /**
     * Notify the user about successful saving of data.
     */
    public void saveNotification() {
        notate(
                "data.save.ok.caption", "data.save.ok.description",
                getData().getEntityDisplayName(), getData().getId(), getData().getCommonName()
        );
    }

    /**
     * Notify the user about successful loading the data from the database.
     */
    public void loadNotification() {
        notate(
                "data.load.ok.caption", "data.load.ok.description",
                getData().getEntityDisplayName(), getData().getId(), getData().getCommonName()
        );
    }

    /**
     * Notify the user about the removal of this data set from the database.
     */
    public void removeNotification() {
        notate(
                "data.remove.ok.caption", "data.remove.ok.description",
                getData().getEntityDisplayName(), getData().getId(), getData().getCommonName()
        );
    }

    /**
     * Notify the user about his aborting the current edits.
     */
    public void cancelNotification() {
        notate(
                "data.cancel.ok.caption", "data.cancel.ok.description",
                getData().getEntityDisplayName(), getData().getId(), getData().getCommonName()
        );
    }


    /**
     * Navigate to the default target of the view.
     *
     * @param source The source object of the request to navigate to the new view.
     */
    public abstract void navigateToTarget(Object source);


    public T getData() {
        if (data == null)
            data = createNew();

        return data;
    }

    public void setData(T data) {
        LOG.info("Editing data: {}", data);

        this.data = data;
    }


    public void save() {
        try {
            service.save(data);

            saveNotification();
            navigateToTarget(this);
        } catch (EntityNotSavedException e) {
            LOG.error(e.getMessage(), e);

            error(
                    e.getCaptionI18Nkey(),
                    e.getDescriptionI18Nkey(),

                    e.getId(),

                    data.getId(),
                    data.getCreatedDate(),
                    data.getLastModifiedDate()
            );
        }
    }

    public void load(long id) {
        try {
            data = service.load(id);

            loadNotification();
        } catch (EntityNotFoundException e) {
            LOG.error(e.getMessage(), e);

            error(
                    e.getCaptionI18Nkey(),
                    e.getDescriptionI18Nkey(),

                    e.getId(),

                    data.getId(),
                    data.getCreatedDate(),
                    data.getLastModifiedDate()
            );
        }
    }

    public void remove(Object source) {
        try {
            service.remove(data.getId());

            removeNotification();
            navigateToTarget(source);
        } catch (JpaSystemException e) {
            UUID id = UUID.randomUUID();
            LOG.error(e.getClass().getSimpleName() + " caught (" + id + "): " + e.getMessage(), e);

            error(
                    "data.remove.failed.caption",
                    "data.remove.failed.description",

                    id,

                    data.getId(),
                    data.getCreatedDate(),
                    data.getLastModifiedDate()
            );
        } catch (EntityNotRemovedException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);


            error(
                    e.getCaptionI18Nkey(),
                    e.getDescriptionI18Nkey(),

                    e.getId(),

                    data.getId(),
                    data.getCreatedDate(),
                    data.getLastModifiedDate()
            );
        }
    }

    public void cancel(Object source) {
        LOG.info("Editing canceled: {}", data);
        cancelNotification();
        navigateToTarget(source);
    }


    public <T extends BaseEntity> JPAContainer<T> getReadOnlyContainer(Class<T> clasz) {
        return containerProvider.getReadOnlyContainer(clasz);
    }

    public <T extends BaseEntity> JPAContainer<T> getContainer(Class<T> clasz) {
        return containerProvider.getContainer(clasz);
    }
}
