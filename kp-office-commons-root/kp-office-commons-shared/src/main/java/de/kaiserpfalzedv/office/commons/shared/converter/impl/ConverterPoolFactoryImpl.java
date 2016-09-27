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

package de.kaiserpfalzedv.office.commons.shared.converter.impl;

import java.util.HashMap;

import de.kaiserpfalzedv.office.commons.shared.converter.Converter;
import de.kaiserpfalzedv.office.commons.shared.converter.ConverterGenerator;
import de.kaiserpfalzedv.office.commons.shared.converter.ConverterPoolFactory;
import de.kaiserpfalzedv.office.commons.shared.converter.NoMatchingConverterFoundException;
import org.apache.commons.pool2.BaseKeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-27
 */
public class ConverterPoolFactoryImpl extends BaseKeyedPooledObjectFactory<String, Converter> implements ConverterPoolFactory {
    private static final Logger LOG = LoggerFactory.getLogger(ConverterPoolFactoryImpl.class);

    private final HashMap<String, ConverterGenerator<? extends Converter>> factories = new HashMap<>();


    @Override
    public Converter create(String key) throws Exception {
        if (!factories.containsKey(key)) {
            throw new NoMatchingConverterFoundException(key);
        }

        Converter result = factories.get(key).createInstance();

        LOG.trace("Created new converter: {}", result);
        return result;
    }

    @Override
    public PooledObject<Converter> wrap(Converter value) {
        return new DefaultPooledObject<>(value);
    }

    @Override
    public synchronized void register(String actionType, ConverterGenerator converter) {
        factories.put(actionType, converter);

        LOG.debug("Converter Pool Factory: registered converter for '{}': {}", actionType, converter);
    }
}
