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

package de.kaiserpfalzEdv.office.cli.executor.events;

import java.util.UUID;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 03.03.15 00:22
 */
public abstract class AbstractBaseCommand extends AbstractEventingBase implements CliCommand {
    private static final long serialVersionUID = -5652116297424621860L;

    public AbstractBaseCommand(final Object source, final UUID id) {
        super(source, id);

    }

    public AbstractBaseCommand(final Object source) {
        this(source, UUID.randomUUID());
    }
}
