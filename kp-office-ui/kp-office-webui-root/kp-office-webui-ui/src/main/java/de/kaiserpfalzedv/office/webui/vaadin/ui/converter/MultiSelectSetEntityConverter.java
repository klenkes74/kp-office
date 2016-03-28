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

package de.kaiserpfalzEdv.vaadin.ui.converter;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.ui.AbstractSelect;
import de.kaiserpfalzEdv.vaadin.backend.db.BaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 19.09.15 04:38
 */
public class MultiSelectSetEntityConverter implements Converter<Object, Set<? extends BaseEntity>> {
    private static final long serialVersionUID = -1845939521543877636L;

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private final static Set<BaseEntity> MODEL_TYPE_INSTANCE = new HashSet<>(0);

    private static final Logger LOG = LoggerFactory.getLogger(MultiSelectSetEntityConverter.class);

    private JPAContainer<? extends BaseEntity> container;


    @SuppressWarnings("unchecked")
    public MultiSelectSetEntityConverter(AbstractSelect field) {
        container = (JPAContainer<? extends BaseEntity>) field.getContainerDataSource();
    }


    @SuppressWarnings("unchecked")
    @Override
    public Set<? extends BaseEntity> convertToModel(Object value, Class<? extends Set<? extends BaseEntity>> targetType, Locale locale) throws ConversionException {
        HashSet<BaseEntity> result = new HashSet<>();

        if (container.size() > 0) {
            result.addAll(
                    ((HashSet<Object>) value).stream()
                                             .filter(container::containsId)
                                             .map(itemId -> container.getItem(itemId).getEntity())
                                             .collect(Collectors.toList())
            );
        }

        return result;
    }

    @Override
    public Object convertToPresentation(Set<? extends BaseEntity> value, Class<?> targetType, Locale locale) throws ConversionException {
        HashSet<Object> ids = new HashSet<>();

        if (value == null) value = new HashSet<>();

        for (Object itemId : container.getItemIds()) {
            BaseEntity bean = container.getItem(itemId).getEntity();

            ids.addAll(
                    value.stream()
                         .filter(model -> model.equals(bean))
                         .map(model -> itemId)
                         .collect(Collectors.toList())
            );
        }

        return ids;
    }


    @SuppressWarnings("unchecked")
    @Override
    public Class<Set<? extends BaseEntity>> getModelType() {
        return (Class<Set<? extends BaseEntity>>) MODEL_TYPE_INSTANCE.getClass();
    }

    @Override
    public Class<Object> getPresentationType() {
        return Object.class;
    }
}
