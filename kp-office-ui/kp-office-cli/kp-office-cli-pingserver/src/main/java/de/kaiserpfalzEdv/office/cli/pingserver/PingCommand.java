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

package de.kaiserpfalzEdv.office.cli.pingserver;

import de.kaiserpfalzEdv.office.cli.executor.events.ExecutionCommand;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 05.03.15 10:12
 */
public class PingCommand extends ExecutionCommand {
    private static final long serialVersionUID = -250757093687692019L;


    public PingCommand(final Object source) {
        super(source);
    }
}
