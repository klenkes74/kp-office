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
import de.kaiserpfalzEdv.vaadin.backend.db.BaseEntity;

/**
 * Generates and sets up the {@link JPAContainer}.
 * Needed for adding the {@link com.vaadin.addon.jpacontainer.LazyLoadingDelegate} to the container.
 *
 * @author klenkes
 * @version 2015Q1
 * @since 16.09.15 06:32
 */
public interface DataContainerProvider {
    <T extends BaseEntity> JPAContainer<T> getReadOnlyContainer(Class<T> clasz);

    <T extends BaseEntity> JPAContainer<T> getContainer(Class<T> clasz);
}
