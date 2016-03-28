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

package de.kaiserpfalzEdv.piracc.organization.dsv.editor;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import de.kaiserpfalzEdv.piracc.backend.db.dynamic.DataProtectionContract;
import de.kaiserpfalzEdv.vaadin.auth.Access;
import de.kaiserpfalzEdv.vaadin.ui.defaultviews.DataContainerProvider;
import de.kaiserpfalzEdv.vaadin.ui.defaultviews.editor.BaseEditor;
import de.kaiserpfalzEdv.vaadin.ui.defaultviews.editor.impl.BaseEditorViewImpl;
import de.kaiserpfalzEdv.vaadin.ui.menu.MenuEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 17.09.15 07:48
 */
@Named
@UIScope
@Access({"dsb", "office", "board", "admin"})
@MenuEntry(
        name = DataProtectionContractEditorViewImpl.VIEW_NAME,
        i18nKey = DataProtectionContractEditorViewImpl.VIEW_NAME,
        access = {"dsb", "office", "board", "admin"},
        order = 55,
        separator = false
)
@SpringView(name = DataProtectionContractEditorViewImpl.VIEW_NAME)
public class DataProtectionContractEditorViewImpl extends BaseEditorViewImpl<DataProtectionContract, DataProtectionContractEditorView> implements DataProtectionContractEditorView, View {
    public static final  String VIEW_NAME        = "dsvs.editor";
    private static final long   serialVersionUID = 5892353230382706640L;
    private static final int    BUTTON_TAB_INDEX = DataProtectionContractEditor.EDITOR_FIELDS + 1;

    private static final Logger LOG = LoggerFactory.getLogger(DataProtectionContractEditorViewImpl.class);


    private DataProtectionContractEditor editor;

    @Inject
    public DataProtectionContractEditorViewImpl(
            DataProtectionContractEditorPresenter presenter,
            DataContainerProvider containerProvider
    ) {
        super(presenter, containerProvider, BUTTON_TAB_INDEX);
    }


    @Override
    public void setData(DataProtectionContract data) {
        getEditor().setEditorData(data);
    }


    @Override
    public BaseEditor<DataProtectionContract> getEditor(DataProtectionContract data) {
        if (editor == null) {
            editor = new DataProtectionContractEditor((DataProtectionContractEditorPresenter) presenter);
            editor.setEditorData(data);
        }

        return editor;
    }

    public BaseEditor<DataProtectionContract> getEditor() {
        if (editor == null) {
            editor = new DataProtectionContractEditor((DataProtectionContractEditorPresenter) presenter);
            editor.setEditorData(presenter.getData());
        }

        return editor;
    }
}
