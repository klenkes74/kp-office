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

package de.kaiserpfalzEdv.vaadin.auth.user.editor;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.spring.annotation.UIScope;
import de.kaiserpfalzEdv.piracc.backend.auth.UserService;
import de.kaiserpfalzEdv.piracc.backend.db.auth.User;
import de.kaiserpfalzEdv.piracc.backend.db.master.Identity;
import de.kaiserpfalzEdv.piracc.backend.organization.identity.IdentityService;
import de.kaiserpfalzEdv.piracc.organization.identity.AccountNameGenerator;
import de.kaiserpfalzEdv.vaadin.auth.user.list.UserListViewImpl;
import de.kaiserpfalzEdv.vaadin.backend.db.EntityNotFoundException;
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
 * @since 12.09.15 22:41
 */
@Named
@UIScope
public class UserEditorPresenter extends BaseEditorPresenterImpl<User, UserEditorView> {
    private static final long serialVersionUID = 9106699966289466948L;
    private static final Logger LOG            = LoggerFactory.getLogger(UserEditorPresenter.class);

    private IdentityService identityService;

    @Inject
    public UserEditorPresenter(
            final UserService service,
            final IdentityService identityService,
            final DataContainerProvider containerProvider,
            final EventBus bus
    ) {
        super(service, containerProvider, bus);

        this.identityService = identityService;
    }

    @Override
    public User createNew() {
        return new User();
    }

    @Override
    public void navigateToTarget(Object source) {
        setData(createNew());

        navigateTo(UserListViewImpl.VIEW_NAME);

    }


    @Subscribe
    public void loadData(LoadUserEvent event) {
        LOG.debug("Loading data: {}", event);

        load(event.getPayload());

        if (Objects.equals(getData().getId(), event.getPayload()))
            navigateTo(UserEditorViewImpl.VIEW_NAME);
    }


    public String calculateAccountName(final Long id) {
        if (id == null) {
            return "";
        }

        try {
            Identity identity = identityService.load(id);

            return AccountNameGenerator.generateLogin(identity, (UserService) service);
        } catch (EntityNotFoundException e) {
            LOG.error(e.getClass().getSimpleName() + " caught (" + e.getId() + "): " + e.getMessage(), e);

            error(
                    "error.developer-failure.caption",
                    "error.developer-failure.description",
                    e.getId(), e.getMessage()
            );
        }

        return "";
    }
}
