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

package de.kaiserpfalzedv.iam.tenant.api.replies;

import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.commons.api.action.replies.CrudReplyBuilderCreator;
import de.kaiserpfalzedv.commons.api.data.paging.PagedListable;
import de.kaiserpfalzedv.iam.tenant.api.Tenant;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-11-19
 */
@ApplicationScoped
public class TenantCrudReplyBuilderCreator implements CrudReplyBuilderCreator<Tenant> {
    @Override
    public TenantCreateReply create(
            @NotNull final UUID source, @NotNull final UUID commandId, @NotNull final UUID replyId,
            @NotNull final Tenant data
    ) {
        return new TenantCreateReply(source, commandId, replyId, data);
    }

    @Override
    public TenantRetrieveReply retrieve(
            @NotNull final UUID source, @NotNull final UUID commandId, @NotNull final UUID replyId,
            @NotNull final PagedListable<Tenant> data
    ) {
        return new TenantRetrieveReply(source, commandId, replyId, data);
    }

    @Override
    public TenantUpdateReply update(
            @NotNull final UUID source, @NotNull final UUID commandId, @NotNull final UUID replyId,
            @NotNull final Tenant data
    ) {
        return new TenantUpdateReply(source, commandId, replyId, data);
    }

    @Override
    public TenantDeleteReply delete(
            @NotNull final UUID source, @NotNull final UUID commandId, @NotNull final UUID replyId,
            @NotNull final UUID dataId
    ) {
        return new TenantDeleteReply(source, commandId, replyId);
    }
}