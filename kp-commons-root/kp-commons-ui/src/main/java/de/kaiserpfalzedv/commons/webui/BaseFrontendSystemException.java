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

package de.kaiserpfalzedv.commons.webui;

import de.kaiserpfalzedv.commons.api.BaseSystemException;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-11-01
 */
public class BaseFrontendSystemException extends BaseSystemException {
    private static final long serialVersionUID = -2583781501873699257L;

    private String captionKey;
    private String descriptionKey;
    private Object[] data;

    public BaseFrontendSystemException(
            final String captionKey, final String descriptionKey, final String message,
            final Object... data
    ) {
        super(message);

        this.captionKey = captionKey;
        this.descriptionKey = descriptionKey;
        this.data = data;
    }

    public BaseFrontendSystemException(
            final String captionKey, final String descriptionKey, final String message,
            final Throwable cause, final Object... data
    ) {
        super(message, cause);

        this.captionKey = captionKey;
        this.descriptionKey = descriptionKey;
        this.data = data;
    }

    public BaseFrontendSystemException(
            final String captionKey, final String descriptionKey, final String message,
            final Throwable cause
    ) {
        super(message, cause);

        this.captionKey = captionKey;
        this.descriptionKey = descriptionKey;
        this.data = new Object[]{};
    }

    /**
     * @return the key for the caption
     */
    public String getCaptionKey() {
        return captionKey;
    }

    /**
     * @return the key for the description
     */
    public String getDescriptionKey() {
        return descriptionKey;
    }

    /**
     * @return the data for message display
     */
    public Object[] getData() {
        return data;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("captionKey", captionKey)
                .append("descriptionKey", descriptionKey)
                .append("data", data)
                .toString();
    }
}
