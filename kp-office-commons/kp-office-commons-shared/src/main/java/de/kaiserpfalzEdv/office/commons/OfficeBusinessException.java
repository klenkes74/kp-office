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

package de.kaiserpfalzEdv.office.commons;

import de.kaiserpfalzEdv.office.commons.i18n.ErrorMessage;
import de.kaiserpfalzEdv.office.commons.i18n.MessageBuilder;
import de.kaiserpfalzEdv.office.commons.i18n.MessageContainable;
import de.kaiserpfalzEdv.office.commons.i18n.MessageKey;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
@SuppressWarnings("UnusedDeclaration")
public class OfficeBusinessException extends Exception implements MessageContainable {
    private MessageKey i18n;

    public OfficeBusinessException(@NotNull final ErrorMessage msg) {
        super(msg.getMessage());

        i18n = new MessageBuilder().withErrorMessage(msg).build();
    }

    public OfficeBusinessException(@NotNull final ErrorMessage msg, @NotNull final Throwable cause) {
        super(msg.getMessage(), cause);

        i18n = new MessageBuilder().withErrorMessage(msg).build();
    }


    public OfficeBusinessException(@NotNull final ErrorMessage msg, @NotNull final String message) {
        super(message);

        i18n = new MessageBuilder().withErrorMessage(msg).withMessage(message).build();
    }

    public OfficeBusinessException(@NotNull final ErrorMessage msg, @NotNull final String message, @NotNull final Throwable cause) {
        super(message, cause);

        i18n = new MessageBuilder().withErrorMessage(msg).withMessage(message).build();
    }


    public OfficeBusinessException(@NotNull final ErrorMessage msg, @NotNull final List<Serializable> data) {
        super(msg.getMessage());

        i18n = new MessageBuilder().withErrorMessage(msg).withData(data).build();
    }

    public OfficeBusinessException(@NotNull final ErrorMessage msg, @NotNull final List<Serializable> data, @NotNull final Throwable cause) {
        super(msg.getMessage(), cause);

        i18n = new MessageBuilder().withErrorMessage(msg).withData(data).build();
    }


    public OfficeBusinessException(@NotNull final ErrorMessage msg, @NotNull final String message, @NotNull final List<Serializable> data) {
        super(message);

        i18n = new MessageBuilder().withErrorMessage(msg).withMessage(message).withData(data).build();
    }

    public OfficeBusinessException(@NotNull final ErrorMessage msg, @NotNull final String message, @NotNull final List<Serializable> data, @NotNull final Throwable cause) {
        super(message, cause);

        i18n = new MessageBuilder().withErrorMessage(msg).withMessage(message).withData(data).build();
    }


    /**
     * @return The message msg for i18n usage.
     */
    public MessageKey getI18n() {
        return i18n;
    }
}
