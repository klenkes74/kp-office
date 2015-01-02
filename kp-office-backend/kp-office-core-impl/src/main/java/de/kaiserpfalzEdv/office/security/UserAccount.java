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

package de.kaiserpfalzEdv.office.security;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @version 0.1.0
 * @since 0.1.0
 */
@Entity
@DiscriminatorValue("USER")
public class UserAccount extends Account {
    @SuppressWarnings("deprecation")
    @Deprecated
    public UserAccount() {
        super();
    }

    public UserAccount(@NotNull final UUID id,
                       @NotNull final Identity identity,
                       final String account,
                       final String name,
                       final String password) {
        super(id, identity, account, name, password);
    }
}
