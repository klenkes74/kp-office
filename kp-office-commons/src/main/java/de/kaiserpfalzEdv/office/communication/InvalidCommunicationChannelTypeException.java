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

package de.kaiserpfalzEdv.office.communication;

import javax.validation.constraints.NotNull;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @version 0.1.0
 * @since 0.1.0
 */
public class InvalidCommunicationChannelTypeException extends CommunicationException {
    private static final long serialVersionUID = 4262329548681743540L;


    public InvalidCommunicationChannelTypeException(@NotNull final Class<? extends CommunicationChannel> current, @NotNull final Class<? extends CommunicationChannel> expected) {
        super("Can't send message to communication channel of type '" + current.getSimpleName() + "'. A communication channel of type '" + expected.getSimpleName() + "' is needed!");
    }
}
