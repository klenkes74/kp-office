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

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.fieldfactory.SingleSelectConverter;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import de.kaiserpfalzEdv.vaadin.backend.db.BaseEntity;
import de.kaiserpfalzEdv.vaadin.ui.converter.MultiSelectListEntityConverter;
import de.kaiserpfalzEdv.vaadin.ui.converter.MultiSelectSetEntityConverter;
import de.kaiserpfalzEdv.vaadin.ui.defaultviews.editor.BaseEditor;
import de.kaiserpfalzEdv.vaadin.ui.defaultviews.editor.BaseEditorView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.vaadin.server.Sizeable.Unit.PERCENTAGE;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 20.09.15 08:59
 */
public abstract class BaseEditorImpl<T extends BaseEntity, V extends BaseEditorView> extends CustomComponent implements BaseEditor<T> {
    private static final long   serialVersionUID = 3263276474505173583L;
    private static final Logger LOG              = LoggerFactory.getLogger(BaseEditorImpl.class);

    protected BaseEditorPresenterImpl<T, V> presenter;
    protected BeanFieldGroup<T>             fieldGroup;

    private Class<T> clasz;

    @SuppressWarnings("unchecked")
    public BaseEditorImpl(BaseEditorPresenterImpl presenter, Class<T> clasz) {
        this.presenter = presenter;

        this.clasz = clasz;
    }


    @Override
    public void discard(Button.ClickEvent event) {
        if (fieldGroup == null)
            LOG.error("Can't discard a non-existing editor!");

        fieldGroup.discard();

        LOG.debug("Reset editor: {}", event);
    }


    @Override
    public void commit(Button.ClickEvent event) throws FieldGroup.CommitException {
        if (fieldGroup == null) {
            LOG.error("Can't commit a non-existing editor!");
        } else {
            fieldGroup.commit();

            LOG.debug("Committed role editor: {}", event);
        }
    }


    @Override
    public T getEditorData() {
        if (fieldGroup == null)
            return null;

        return fieldGroup.getItemDataSource().getBean();
    }

    public void setEditorData(T data) {
        fieldGroup = new BeanFieldGroup<>(clasz);
        fieldGroup.setBuffered(true);
        fieldGroup.setItemDataSource(data);
        fieldGroup.buildAndBindMemberFields(this);

        LOG.debug("Bound data set to editor: {}", data);
    }


    protected TextField createTextField(final String caption, int tabIndex) {
        TextField result = new TextField(presenter.translate(caption));

        result.setWidth(100f, PERCENTAGE);
        result.setTabIndex(tabIndex);
        result.setNullRepresentation("");

        String description = presenter.translate(caption + ".description");
        if (!description.equals(caption + ".description"))
            result.setDescription(description);

        return result;
    }

    protected TextArea createTextArea(final String caption, final int tabIndex, final int rows) {
        TextArea result = new TextArea(presenter.translate(caption));

        result.setTabIndex(tabIndex);
        result.setWidth(100f, PERCENTAGE);
        result.setRows(rows);
        result.setWordwrap(true);
        result.setNullRepresentation("");

        return result;
    }

    protected DateField createDateField(final String caption, final int tabIndex) {
        DateField result = new DateField(presenter.translate(caption));

        result.setTabIndex(tabIndex);
        result.setWidth(100f, PERCENTAGE);
        result.setResolution(Resolution.DAY);
        result.setShowISOWeekNumbers(true);

        return result;
    }

    protected DateField createDateTimeField(final String caption, final int tabIndex) {
        DateField result = createDateField(caption, tabIndex);

        result.setResolution(Resolution.SECOND);

        return result;
    }


    protected ComboBox createComboBox(
            final String i18nBase, final String displayName,
            final int tabIndex, final Class<? extends BaseEntity> clasz
    ) {

        JPAContainer container = presenter.getContainer(clasz);
        container.getEntityProvider().setLazyLoadingDelegate(null);
        container.sort(new Object[]{displayName}, new boolean[]{true});

        ComboBox result = new ComboBox(presenter.translate(i18nBase), container);

        result.setTabIndex(tabIndex);
        result.setWidth(100f, PERCENTAGE);
        result.setItemCaptionPropertyId(displayName);
        result.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
        result.setConverter(new SingleSelectConverter<Long>(result));
        result.setNullSelectionAllowed(false);

        return result;
    }

    protected ComboBox createDropdownSelect(
            final String i18nBase, final String displayName,
            final int tabIndex, final Class<? extends BaseEntity> clasz
    ) {
        ComboBox result = createComboBox(i18nBase, displayName, tabIndex, clasz);

        result.setTextInputAllowed(false);

        return result;
    }

    protected TwinColSelect createTwinColListSelect(
            final String i18nBase, final String displayColumn, final int rows,
            final int tabIndex, final Class<? extends BaseEntity> clasz
    ) {
        JPAContainer container = presenter.getContainer(clasz);
        container.sort(new Object[]{displayColumn}, new boolean[]{true});

        TwinColSelect result = new TwinColSelect(presenter.translate(i18nBase), container);

        result.setTabIndex(tabIndex);
        result.setLeftColumnCaption(presenter.translate(i18nBase + ".available"));
        result.setRightColumnCaption(presenter.translate(i18nBase + ".assigned"));
        result.setWidth(100f, PERCENTAGE);
        result.setBuffered(true);
        result.setMultiSelect(true);
        result.setRows(rows);
        result.setItemCaptionPropertyId(displayColumn);
        result.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
        result.setConverter(new MultiSelectListEntityConverter(result));

        result.addValueChangeListener(
                event -> LOG.trace("'{}' changed: {}", i18nBase, event.getProperty().getValue())
        );

        return result;
    }


    protected TwinColSelect createTwinColSelect(
            final String i18nBase, final String displayColumn, final int rows,
            final int tabIndex, final Class<? extends BaseEntity> clasz
    ) {
        JPAContainer container = presenter.getContainer(clasz);

        TwinColSelect result = new TwinColSelect(presenter.translate(i18nBase), container);

        result.setTabIndex(tabIndex);
        result.setLeftColumnCaption(presenter.translate(i18nBase + ".available"));
        result.setRightColumnCaption(presenter.translate(i18nBase + ".assigned"));
        result.setWidth(100f, PERCENTAGE);
        result.setBuffered(true);
        result.setMultiSelect(true);
        result.setRows(rows);
        result.setItemCaptionPropertyId(displayColumn);
        result.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
        result.setConverter(new MultiSelectSetEntityConverter(result));

        result.addValueChangeListener(
                event -> LOG.trace("'{}' changed: {}", i18nBase, event.getProperty().getValue())
        );

        return result;
    }
}