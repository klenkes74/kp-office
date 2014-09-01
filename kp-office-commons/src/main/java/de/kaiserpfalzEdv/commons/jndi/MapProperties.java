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

package de.kaiserpfalzEdv.commons.jndi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.Serializable;
import java.util.Map;
import java.util.Properties;

/**
 * A small mapper to support Spring in generating a {@link java.util.Properties} object from a map.
 *
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public class MapProperties implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(MapProperties.class);
    private static final long serialVersionUID = 8914256514684718924L;

    private final Properties properties = new Properties();

    public MapProperties() {}

    public MapProperties(Map<Object, Object> orig) {
        orig.forEach(properties::put);
    }

    @PostConstruct
    public void init() {
        LOG.trace("***** Created: {}", this);
    }

    @PreDestroy
    public void close() {
        LOG.trace("***** Destroyed: {}", this);
    }


    public Properties getProperties() {
        LOG.debug("Generated properties: {}", properties);

        return new Properties(properties); // only return a copy of the properties ...
    }


    public void put(Object key, Object value) {
        properties.put(key, value);
    }

    public void remove(Object key) {
        properties.remove(key);
    }

    public void clear() {
        properties.clear();
    }
}
