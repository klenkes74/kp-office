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

package de.kaiserpfalzedv.office.common.commands;

import java.util.EventObject;
import java.util.UUID;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-27
 */
public class BaseReplyImpl extends EventObject implements BaseReply {
    private static final long serialVersionUID = 1L;


    protected UUID commandId;
    protected UUID replyId;


    @SuppressWarnings({"unused", "deprecation"})
    @Deprecated // Only for framework usage
    public BaseReplyImpl() {
        super(UUID.randomUUID());
    }

    public BaseReplyImpl(Object source, final UUID commandId, final UUID replyId) {
        super(source);

        this.commandId = commandId;
        this.replyId = replyId;
    }

    @Override
    public UUID getCommandId() {
        return commandId;
    }

    @Override
    public UUID getReplyId() {
        return replyId;
    }

    @Override
    public String getActionType() {
        return getClass().getCanonicalName();
    }

    @Override
    public UUID getSource() {
        return (UUID) super.getSource();
    }
}
