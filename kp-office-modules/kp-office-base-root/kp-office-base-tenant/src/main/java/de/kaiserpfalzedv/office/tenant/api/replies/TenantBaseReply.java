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

package de.kaiserpfalzedv.office.tenant.api.replies;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.office.common.commands.BaseSuccess;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-25
 */
public abstract class TenantBaseReply extends BaseSuccess {
    private static final long serialVersionUID = -3065183286439515955L;

    TenantBaseReply(
            @NotNull final UUID source,
            @NotNull final UUID commandId,
            @NotNull final UUID replyId
    ) {
        super(source, commandId, replyId);
    }
}
