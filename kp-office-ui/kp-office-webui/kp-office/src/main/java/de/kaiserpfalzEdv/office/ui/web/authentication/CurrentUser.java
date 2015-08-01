/*
 * Copyright 2015 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzEdv.office.ui.web.authentication;

import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import de.kaiserpfalzEdv.office.core.security.OfficeLoginTicket;

/**
 * Class for retrieving and setting the name of the current user of the current
 * session (without using JAAS). All methods of this class require that a
 * {@link VaadinRequest} is bound to the current thread.
 *
 * @see VaadinService#getCurrentRequest()
 */
public final class CurrentUser {

    /**
     * The attribute key used to store the username in the session.
     */
    public static final String CURRENT_USER_SESSION_ATTRIBUTE_KEY = CurrentUser.class.getCanonicalName();

    private CurrentUser() {
    }

    public static OfficeLoginTicket getUser() {
        OfficeLoginTicket result = (OfficeLoginTicket) getCurrentRequest().getWrappedSession()
                                                                          .getAttribute(CURRENT_USER_SESSION_ATTRIBUTE_KEY);

        return result;
    }

    /**
     * Returns the name of the current user stored in the current session, or an
     * empty string if no user name is stored.
     *
     * @return The account name of the current user.
     * @throws IllegalStateException if the current session cannot be accessed.
     */
    public static String get() {
        OfficeLoginTicket currentUser = getUser();

        if (currentUser == null) {
            return "";
        } else {
            return currentUser.getAccountName();
        }
    }

    /**
     * Sets the name of the current user and stores it in the current session.
     * Using a {@code null} username will remove the username from the session.
     *
     * @param currentUser The user to be saved into the session.
     *
     * @throws IllegalStateException if the current session cannot be accessed.
     */
    public static void set(OfficeLoginTicket currentUser) {
        if (currentUser == null) {
            getCurrentRequest().getWrappedSession().removeAttribute(
                    CURRENT_USER_SESSION_ATTRIBUTE_KEY
            );
        } else {
            getCurrentRequest().getWrappedSession().setAttribute(
                    CURRENT_USER_SESSION_ATTRIBUTE_KEY, currentUser
            );
        }
    }

    private static VaadinRequest getCurrentRequest() {
        VaadinRequest request = VaadinService.getCurrentRequest();
        if (request == null) {
            throw new IllegalStateException("No request bound to current thread");
        }
        return request;
    }
}
