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
import de.kaiserpfalzedv.office.commons.shared.converter.ConverterRegistry;
import de.kaiserpfalzedv.office.commons.shared.converter.NoMatchingConverterFoundException;
import de.kaiserpfalzedv.office.commons.shared.converter.WrongPayloadForConverterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-27
 */
public class Marshaller<T> {
    private static final Logger LOG = LoggerFactory.getLogger(Marshaller.class);

    private ConverterRegistry registry;

    private String actionType;
    private Object payload;

    public Marshaller(ConverterRegistry converterRegistry) {
        this.registry = converterRegistry;
    }


    public Marshaller setActionType(String actionType) {
        this.actionType = actionType;
        return this;
    }

    public Marshaller setPayload(Object payload) {
        this.payload = payload;
        return this;
    }


    public String marshal() throws NoMatchingConverterFoundException {
        Converter converter = registry.borrowConverter(actionType);

        String result = converter.marshal(payload);

        registry.returnConverter(actionType, converter);
        return result;
    }

    public T unmarshal() throws NoMatchingConverterFoundException, WrongPayloadForConverterException {
        Converter converter = registry.borrowConverter(actionType);

        String data;
        try {
            data = (String) payload;
        } catch (ClassCastException e) {
            throw new WrongPayloadForConverterException(String.class, payload, e);
        }

        T result;
        try {
            result = (T) converter.unmarshal(data);
        } catch (ClassCastException e) {
            throw new WrongPayloadForConverterException(null, data, e);
        }

        return result;
    }
}
