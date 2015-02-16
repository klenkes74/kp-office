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

package de.kaiserpfalzEdv.office.core.data;

import de.kaiserpfalzEdv.office.commons.Entity;
import de.kaiserpfalzEdv.office.commons.OfficeBusinessException;

/**
 * @author klenkes
 * @since 2014Q
 */
public class NoSuchEntityException extends OfficeBusinessException {
    private static final long serialVersionUID = 6742559730560171459L;


    public NoSuchEntityException(final Class<? extends Entity> clasz, final String message) {
        super(ErrorMessage.NO_SUCH_ENTITY, createMessage(clasz, message));
    }

    public NoSuchEntityException(final Class<? extends Entity> clasz, final Throwable cause) {
        super(ErrorMessage.NO_SUCH_ENTITY, createMessage(clasz, cause.getMessage()), cause);
    }

    public NoSuchEntityException(final Class<? extends Entity> clasz, final String message, final Throwable cause) {
        super(ErrorMessage.NO_SUCH_ENTITY, createMessage(clasz, message), cause);
    }

    private static String createMessage(final Class<? extends Entity> entity, final String message) {
        return new StringBuilder("Entity of type '").append(entity.getSimpleName()).append("' not found: ").append(message).toString();
    }
}
