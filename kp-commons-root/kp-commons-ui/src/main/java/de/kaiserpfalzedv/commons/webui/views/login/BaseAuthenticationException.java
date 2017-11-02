/*
 * Copyright 2017 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzedv.commons.webui.views.login;


import de.kaiserpfalzedv.commons.webui.BaseFrontendException;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 06.09.15 07:26
 */
public class BaseAuthenticationException extends BaseFrontendException {
    private static final long serialVersionUID = 4640415293508215511L;

    private String login;

    public BaseAuthenticationException(final String login) {
        super(
                "login.failed.caption",
                "login.failed.description",
                "Login failed for user '" + login + "'.",
                login
        );

        this.login = login;
    }

    public BaseAuthenticationException(final String login, final Throwable cause) {
        super(
                "login.failed.caption",
                "login.failed.description",
                "Login failed for user '" + login + "'.",
                cause,
                login
        );

        this.login = login;
    }

    public BaseAuthenticationException(final Throwable cause) {
        super(
                "login.failed.caption",
                "login.failed.description",
                "Login failed: " + cause.getMessage(),
                cause
        );
    }

    public String getLogin() {
        return login;
    }
}
