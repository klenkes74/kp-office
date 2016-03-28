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

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import de.kaiserpfalzEdv.vaadin.backend.db.BaseEntity;
import de.kaiserpfalzEdv.vaadin.ui.defaultviews.DataContainerProvider;
import de.kaiserpfalzEdv.vaadin.ui.defaultviews.editor.BaseEditor;
import de.kaiserpfalzEdv.vaadin.ui.defaultviews.editor.BaseEditorView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.vaadin.server.Sizeable.Unit.PERCENTAGE;
import static com.vaadin.server.Sizeable.Unit.PIXELS;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 12.09.15 22:53
 */
public abstract class BaseEditorViewImpl<T extends BaseEntity, V extends BaseEditorView<T>>
        extends CustomComponent
        implements BaseEditorView<T>, View {
    private static final Logger LOG = LoggerFactory.getLogger(BaseEditorViewImpl.class);

    private static final String COMMIT_ERROR_CAPTION     = "data.save.ok.caption";
    private static final String COMMIT_ERROR_DESCRIPTION = "data.save.ok.description";


    protected BaseEditorPresenterImpl<T, V> presenter;
    private   DataContainerProvider         containerProvider;


    private HorizontalLayout layout;
    private VerticalLayout   editorLayout;
    private VerticalLayout   buttonLayout;

    private Button saveButton, discardButton, cancelButton, deleteButton;
    private int buttonTabIndex = 0;

    private String commitErrorCaption;
    private String commitErrorDescription;


    public BaseEditorViewImpl(
            final BaseEditorPresenterImpl<T, V> presenter,
            DataContainerProvider containerProvider,
            int buttonTabIndex
    ) {
        this.presenter = presenter;
        this.containerProvider = containerProvider;
        this.buttonTabIndex = buttonTabIndex;

        setSizeFull();
        setResponsive(true);
    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        LOG.debug("Opening: {}", this);

        if (layout == null) {
            initializeLayout();
        }

        if (buttonLayout == null) {
            initializeButtons();
        }

        getEditor().setEditorData(presenter.getData());
    }


    private void initializeLayout() {
        layout = new HorizontalLayout();
        layout.setWidth(100f, PERCENTAGE);
        layout.setHeight(97f, PERCENTAGE);
        layout.setMargin(true);
        layout.setSpacing(true);
        layout.setResponsive(true);

        editorLayout = new VerticalLayout();
        editorLayout.setResponsive(true);
        layout.addComponent(editorLayout);

        Component data = getEditor(presenter.getData());
        data.setWidth(87f, PERCENTAGE);
        data.setHeight(100f, PERCENTAGE);

        Label fillSpace = new Label();
        fillSpace.setSizeFull();

        editorLayout.addComponent(data);
        editorLayout.addComponent(fillSpace);

        editorLayout.setExpandRatio(data, 10f);
        editorLayout.setExpandRatio(fillSpace, 90f);

        layout.setExpandRatio(editorLayout, 90f);
        setCompositionRoot(layout);
    }

    public abstract BaseEditor<T> getEditor(T data);

    public abstract BaseEditor<T> getEditor();


    private void initializeButtons() {
        buttonLayout = new VerticalLayout();
        buttonLayout.setWidth(125f, PIXELS);
        buttonLayout.setSpacing(true);
        buttonLayout.setResponsive(true);
        layout.addComponent(buttonLayout);
        layout.setExpandRatio(buttonLayout, 10f);

        saveButton = initializeButton("save", buttonTabIndex);
        saveButton.addClickListener(
                event -> {
                    try {
                        getEditor().commit(event);

                        presenter.setData(getEditor().getEditorData());
                        presenter.save();
                    } catch (FieldGroup.CommitException e) {
                        LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);

                        presenter.error(
                                getCommitErrorCaption(),
                                getCommitErrorDescription(),
                                getCommitErrorParameters()
                        );
                    }

                }
        );
        saveButton.setClickShortcut(KeyCode.ENTER);
        saveButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
        buttonLayout.addComponent(saveButton);

        discardButton = initializeButton("discard", buttonTabIndex + 1);
        discardButton.addClickListener(event -> getEditor().discard(event));
        buttonLayout.addComponent(discardButton);

        cancelButton = initializeButton("cancel", buttonTabIndex + 2);
        cancelButton.addClickListener(event -> presenter.cancel(event.getSource()));
        buttonLayout.addComponent(cancelButton);

        deleteButton = initializeButton("remove", buttonTabIndex + 3);
        deleteButton.addClickListener(event -> presenter.remove(event.getSource()));
        deleteButton.setStyleName(ValoTheme.BUTTON_DANGER);
        buttonLayout.addComponent(deleteButton);

        layout.addComponent(buttonLayout);
        LOG.debug("Added {} buttons to the editor.", buttonLayout.getComponentCount());
    }

    private Button initializeButton(final String buttonKey, int tabIndex) {
        Button result = new Button(presenter.translate("button." + buttonKey + ".caption"));
        result.setDescription(presenter.translate("button." + buttonKey + ".description"));
        result.setIcon(FontAwesome.valueOf(presenter.translate("button." + buttonKey + ".icon")));
        result.setWidth(100f, PERCENTAGE);
        result.setTabIndex(tabIndex);

        return result;
    }

    protected void setCommitError(final String caption, final String description) {
        commitErrorCaption = caption;
        commitErrorDescription = description;
    }

    public String getCommitErrorCaption() {
        return commitErrorCaption;
    }

    public String getCommitErrorDescription() {
        return commitErrorDescription;
    }

    /**
     * Returns the data array for internationalization for the commit error case. The first three items need to be the
     * simple class name of the object, the database id and the created date.
     *
     * @return The parameters for internationalization of the commit error message.
     */
    public Object[] getCommitErrorParameters() {
        return new Object[]{
                presenter.getData().getClass().getSimpleName(),
                presenter.getData().getId(),
                presenter.getData().getCreatedDate()
        };
    }


    public DataContainerProvider getContainerProvider() {
        return containerProvider;
    }
}
