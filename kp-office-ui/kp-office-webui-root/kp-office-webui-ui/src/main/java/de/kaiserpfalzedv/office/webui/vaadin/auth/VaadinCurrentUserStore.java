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

package de.kaiserpfalzEdv.vaadin.auth;

import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.WrappedSession;
import de.kaiserpfalzEdv.piracc.backend.db.auth.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 09.09.15 18:12
 */
@Service
public class VaadinCurrentUserStore implements de.kaiserpfalzEdv.vaadin.backend.auth.CurrentUserStore {
    /**
     * The attribute key used to store the username in the session.
     */
    public static final String  CURRENT_USER_SESSION_ATTRIBUTE_KEY = VaadinCurrentUserStore.class.getCanonicalName();
    private static final Logger LOG                                = LoggerFactory.getLogger(VaadinCurrentUserStore.class);

    /**
     * Gets the name of the current user from the current session. A {@code null} result is given if the user is not
     * stored in the current session.
     *
     * @return The current loged-in user or {@code null}.
     *
     * @throws IllegalStateException if the current session cannot be accessed.
     */
    public static User get() {
        return (User) getCurrentSession().getAttribute(CURRENT_USER_SESSION_ATTRIBUTE_KEY);
    }

    /**
     * Sets the current user and stores it in the current session.
     * Using a {@code null} user will remove the user from the session.
     *
     * @throws IllegalStateException if the current session cannot be accessed.
     */
    public static void set(User currentUser) {
        LOG.debug(
                "Changing user: {} -> {}",
                getCurrentSession().getAttribute(CURRENT_USER_SESSION_ATTRIBUTE_KEY), currentUser
        );

        if (currentUser == null) {
            getCurrentSession().removeAttribute(CURRENT_USER_SESSION_ATTRIBUTE_KEY);
        } else {
            getCurrentSession().setAttribute(CURRENT_USER_SESSION_ATTRIBUTE_KEY, currentUser);
        }
    }

    public static void unset() {
        set(null);
    }

    private static WrappedSession getCurrentSession() {
        VaadinRequest request = VaadinService.getCurrentRequest();
        if (request == null) {
            throw new IllegalStateException("No request bound to current thread");
        }
        return request.getWrappedSession();
    }


    @Override
    public User getCurrentUser() {
        return get();
    }

    @Override
    public void setCurrentUser(User user) {
        set(user);
    }

    @Override
    public void unsetCurrentUser() {
        unset();
    }

}
