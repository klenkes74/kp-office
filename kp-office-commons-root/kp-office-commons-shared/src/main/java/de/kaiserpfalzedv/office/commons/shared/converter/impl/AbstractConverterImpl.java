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

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.kaiserpfalzedv.office.commons.shared.converter.Converter;
import de.kaiserpfalzedv.office.commons.shared.converter.ConverterSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-27
 */
public abstract class AbstractConverterImpl<T> implements Converter<T> {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractConverterImpl.class);

    private ObjectMapper mapper;

    @Override
    public T unmarshal(String data) {
        LOG.trace("Converting: {}", data);
        try {
            Map<String, Object> map = mapper.readValue(data, new TypeReference<Map<String, Object>>() {});

            T result = createConversionResult(map);
            LOG.trace("Converted: {}", result);
            return result;
        } catch (IOException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage());

            throw new ConverterSystemException(null, e);
        }
    }

    public abstract T createConversionResult(final Map<String, Object> params);

    @Override
    public String marshal(T data) {
        LOG.trace("Converting: {}", data);

        try {
            String result = mapper.writeValueAsString(data);
            LOG.trace("Converted: {}", result);
            return result;
        } catch (JsonProcessingException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage());

            throw new ConverterSystemException(data.getClass(), e);
        }
    }

    @Override
    public void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }
}
