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

import de.kaiserpfalzedv.office.commons.shared.converter.Converter;
import de.kaiserpfalzedv.office.commons.shared.converter.ConverterGenerator;
import de.kaiserpfalzedv.office.commons.shared.converter.ConverterPoolFactory;
import de.kaiserpfalzedv.office.commons.shared.converter.ConverterRegistry;
import de.kaiserpfalzedv.office.commons.shared.converter.NoMatchingConverterFoundException;
import org.apache.commons.pool2.KeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-27
 */
public class ConverterRegistryImpl implements ConverterRegistry {
    private static final Logger LOG = LoggerFactory.getLogger(ConverterRegistryImpl.class);
    KeyedObjectPool<String, Converter> converters;
    private ConverterPoolFactory factory;


    public ConverterRegistryImpl() {
        factory = new ConverterPoolFactoryImpl();
        converters = new GenericKeyedObjectPool<>(factory);
    }


    @Override
    public void registerConverter(String actionType, ConverterGenerator<? extends Converter> converterGenerator) {
        factory.register(actionType, converterGenerator);
    }


    @Override
    public <T extends Converter> T borrowConverter(String actionType) throws NoMatchingConverterFoundException {
        try {
            //noinspection unchecked
            return (T) converters.borrowObject(actionType);
        } catch (Exception e) {
            throw new NoMatchingConverterFoundException(actionType, e);
        }
    }

    @Override
    public <T extends Converter> void returnConverter(String actionType, T converter) {
        try {
            converters.returnObject(actionType, converter);
        } catch (Exception e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);
        }
    }

    @Override
    public <T extends Converter> void invalidateConverter(String actionType, T converter) {
        try {
            converters.invalidateObject(actionType, converter);
        } catch (Exception e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);
        }
    }
}
