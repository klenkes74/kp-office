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

package de.kaiserpfalzEdv.vaadin.ui.elements;

import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.VerticalLayout;
import de.kaiserpfalzEdv.vaadin.backend.db.BaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 21.09.15 10:01
 */
public class MultiSelectList<T extends BaseEntity> extends CustomField<List<T>> {
    private static final Logger LOG = LoggerFactory.getLogger(MultiSelectList.class);

    private Class<List<T>> clasz;

    private VerticalLayout layout;
    private GridLayout     entries;

    public MultiSelectList(Class<List<T>> clasz) {
        this.clasz = clasz;
    }


    @Override
    protected Component initContent() {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzEdv.vaadin.ui.elements.MultiSelectList.initContent
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Class<List<T>> getType() {
        return clasz;
    }
}
