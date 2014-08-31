/*
 * Copyright (c) 2014 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzEdv.office.commands;

import de.kaiserpfalzEdv.office.OfficeBusinessException;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public class OfficeCommandException extends OfficeBusinessException {
    private static final long serialVersionUID = -1469401084204457032L;


    private OfficeCommand command;


    public OfficeCommandException(final OfficeCommand command, final String message) {
        super(message);

        setCommand(command);
    }

    public OfficeCommandException(final OfficeCommand command, final Throwable cause) {
        super(cause);

        setCommand(command);
    }

    public OfficeCommandException(final OfficeCommand command, final String message, final Throwable cause) {
        super(message, cause);

        setCommand(command);
    }


    public OfficeCommand getCommand() {
        return command;
    }

    private void setCommand(OfficeCommand command) {
        this.command = command;
    }
}
