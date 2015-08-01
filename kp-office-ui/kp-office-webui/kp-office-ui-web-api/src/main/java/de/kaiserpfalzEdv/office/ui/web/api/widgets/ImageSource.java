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

package de.kaiserpfalzEdv.office.ui.web.api.widgets;

import com.vaadin.server.StreamResource;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.net.URISyntaxException;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 18.02.15 12:58
 */
public class ImageSource implements StreamResource.StreamSource {
    private static final Logger LOG = LoggerFactory.getLogger(ImageSource.class);

    private final String      fileName;
    private final InputStream image;

    public ImageSource(final String fileName) {
        this.fileName = fileName;
        image = getClass().getClassLoader().getResourceAsStream(fileName);

        LOG.trace("Created: {}", this);
        try {
            LOG.trace("  from file: {}", getClass().getClassLoader().getResource(fileName).toURI());
        } catch (URISyntaxException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);
        }
    }

    @Override
    public InputStream getStream() {
        return image;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("fileName", fileName)
                .toString();
    }
}
