/*
 * Copyright 2016 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzedv.office.tenant.replies;

import java.util.UUID;

import de.kaiserpfalzedv.office.tenant.Tenant;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-25
 */
public abstract class TenantContainingBaseReply extends TenantBaseReply {
    private static final long serialVersionUID = 1L;

    private Tenant tenant;


    @SuppressWarnings({"unused", "deprecation", "WeakerAccess"})
    @Deprecated // Only for framework usage
    protected TenantContainingBaseReply() {}

    TenantContainingBaseReply(final UUID source, final UUID commandId, final UUID replyId, final Tenant tenant) {
        super(source, commandId, replyId);

        this.tenant = tenant;
    }

    public Tenant getTenant() {
        return tenant;
    }
}
