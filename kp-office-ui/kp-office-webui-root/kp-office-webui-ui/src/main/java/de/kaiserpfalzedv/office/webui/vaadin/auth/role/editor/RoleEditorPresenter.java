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

package de.kaiserpfalzEdv.vaadin.auth.role.editor;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.spring.annotation.UIScope;
import de.kaiserpfalzEdv.piracc.backend.auth.RoleService;
import de.kaiserpfalzEdv.piracc.backend.db.auth.Role;
import de.kaiserpfalzEdv.vaadin.auth.role.list.RoleListViewImpl;
import de.kaiserpfalzEdv.vaadin.ui.defaultviews.DataContainerProvider;
import de.kaiserpfalzEdv.vaadin.ui.defaultviews.editor.impl.BaseEditorPresenterImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Objects;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 12.09.15 22:42
 */
@Named
@UIScope
public class RoleEditorPresenter extends BaseEditorPresenterImpl<Role, RoleEditorView> {
    private static final long   serialVersionUID = -3646962015226960806L;
    private static final Logger LOG              = LoggerFactory.getLogger(RoleEditorPresenter.class);

    @Inject
    public RoleEditorPresenter(
            final RoleService service,
            final DataContainerProvider containerProvider,
            final EventBus bus
    ) {
        super(service, containerProvider, bus);
    }


    @Override
    public Role createNew() {
        return new Role();
    }


    @Override
    public void navigateToTarget(Object source) {
        setData(createNew());

        navigateTo(RoleListViewImpl.VIEW_NAME);
    }


    @Subscribe
    public void loadData(LoadRoleEvent event) {
        LOG.debug("Loading data: {}", event);

        load(event.getPayload());

        if (Objects.equals(getData().getId(), event.getPayload()))
            navigateTo(RoleEditorViewImpl.VIEW_NAME);
    }
}