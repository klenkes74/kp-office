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

package de.kaiserpfalzEdv.office.contacts.commands.country;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author klenkes
 * @since 2014Q
 */
public class DeleteCountryCommand extends CountryBaseCommand {
    private static final long serialVersionUID = 1L;


    @SuppressWarnings({"UnusedDeclaration", "deprecation"})
    @Deprecated // Only for Jackson, JAX-B and JPA!
    public DeleteCountryCommand() {
    }

    public DeleteCountryCommand(@NotNull final UUID id) {
        super(id);
    }
}
