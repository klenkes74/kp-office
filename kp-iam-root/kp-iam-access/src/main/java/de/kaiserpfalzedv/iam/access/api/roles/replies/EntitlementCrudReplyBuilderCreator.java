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

package de.kaiserpfalzedv.iam.access.api.roles.replies;

import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.commons.api.action.replies.CrudReplyBuilderCreator;
import de.kaiserpfalzedv.commons.api.data.paging.PagedListable;
import de.kaiserpfalzedv.iam.access.api.roles.Entitlement;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-11-19
 */
@ApplicationScoped
public class EntitlementCrudReplyBuilderCreator implements CrudReplyBuilderCreator<Entitlement> {
    @Override
    public EntitlementCreateReply create(
            @NotNull final UUID source, @NotNull final UUID commandId, @NotNull final UUID replyId,
            @NotNull final Entitlement data
    ) {
        return new EntitlementCreateReply(source, commandId, replyId, data);
    }

    @Override
    public EntitlementRetrieveReply retrieve(
            @NotNull final UUID source, @NotNull final UUID commandId, @NotNull final UUID replyId,
            @NotNull final PagedListable<Entitlement> data
    ) {
        return new EntitlementRetrieveReply(source, commandId, replyId, data);
    }

    @Override
    public EntitlementUpdateReply update(
            @NotNull final UUID source, @NotNull final UUID commandId, @NotNull final UUID replyId,
            @NotNull final Entitlement data
    ) {
        return new EntitlementUpdateReply(source, commandId, replyId, data);
    }

    @Override
    public EntitlementDeleteReply delete(
            @NotNull final UUID source, @NotNull final UUID commandId, @NotNull final UUID replyId,
            @NotNull final UUID dataId
    ) {
        return new EntitlementDeleteReply(source, commandId, replyId);
    }
}
