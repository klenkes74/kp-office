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

package de.kaiserpfalzEdv.vaadin.ui.defaultviews.editor;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import de.kaiserpfalzEdv.vaadin.backend.db.BaseEntity;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 16.09.15 07:44
 */
public interface BaseEditor<T extends BaseEntity> extends Component {
    T getEditorData();

    void setEditorData(T data);

    void discard(Button.ClickEvent event);

    void commit(Button.ClickEvent event) throws FieldGroup.CommitException;
}
