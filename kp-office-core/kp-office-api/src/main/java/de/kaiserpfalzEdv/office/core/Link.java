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

package de.kaiserpfalzEdv.office.core;

import java.net.URI;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public class Link {
    private final String href;
    private final String title;

    public Link(final String href, final String title) {
        checkArgument(href != null, "A link depends on having an URI. Can't create an empty link!");

        this.href = href;
        this.title = title;
    }

    public Link(final URI href, final String title) {
        checkArgument(href != null, "A link depends on having an URI. Can't create an empty link!");

        this.href = href.getQuery();
        this.title = title;
    }

    public String getURI() {
        return href;
    }

    public String getDisplayName() {
        return title;
    }
}
