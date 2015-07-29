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

import net.itconcepts.specifications.backend.User;

import javax.inject.Named;

/**
 * Default mock implementation of {@link AccessControl}. This implementation
 * accepts any string as a password, and considers the user "admin" as the only
 * administrator.
 */
@Named
public class BasicAccessControl implements AccessControl {

    @Override
    public boolean signIn(String username, String password) {
        if (username == null || username.isEmpty())
            return false;

        User user = new User();
        user.setUser(username);
        user.setPassword(password);
        user.setFullName(username);
        user.setOrganization("---");
        user.setCostCenter("---");
        CurrentUser.set(user);
        return true;
    }

    @Override
    public boolean isUserSignedIn() {
        return !CurrentUser.get().isEmpty();
    }

    @Override
    public boolean isUserInRole(String role) {
        if ("admin".equals(role)) {
            // Only the "admin" user is in the "admin" role
            return getPrincipalName().equals("admin");
        }

        // All users are in all non-admin roles
        return true;
    }

    @Override
    public String getPrincipalName() {
        return CurrentUser.get();
    }

}
