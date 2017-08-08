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

package de.kaiserpfalzedv.office.tenant.api.commands;

import de.kaiserpfalzedv.office.common.commands.CommandExecutionException;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-27
 */
public class TenantCommandExecutionException extends CommandExecutionException {
    private static final long serialVersionUID = 5808136307408905001L;

    public TenantCommandExecutionException(final TenantBaseCommand command, final String message) {
        super(command, message);
    }

    public TenantCommandExecutionException(final TenantBaseCommand command, final String message, final Throwable cause) {
        super(command, message, cause);
    }

    public TenantCommandExecutionException(final TenantBaseCommand command, final Throwable cause) {
        super(command, cause);
    }
}
