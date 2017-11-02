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

package de.kaiserpfalzedv.commons.webui.converter;

import com.vaadin.data.Result;
import com.vaadin.server.SerializableConsumer;
import com.vaadin.server.SerializableFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * A reimplementation of the {@link com.vaadin.data.SimpleResult} since the Vaadin-guys did not make it public and I
 * need such an implementation.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 1.0.0
 */
public class SimpleResult<T> implements Result<T> {
    private static final Logger LOG = LoggerFactory.getLogger(SimpleResult.class);

    private T value;
    private String message;

    public SimpleResult(@NotNull final T value) {
        this.value = value;
    }

    public SimpleResult(@NotNull final String message) {
        this.message = message;
    }


    @Override
    public <S> Result<S> flatMap(@NotNull final SerializableFunction<T, Result<S>> mapper) {
        //noinspection unchecked
        return isError() ? (Result<S>) this : mapper.apply(value);
    }

    @Override
    public void handle(
            @NotNull final SerializableConsumer<T> ifOk,
            @NotNull final SerializableConsumer<String> ifError
    ) {
        if (isError()) {
            ifError.accept(message);
        } else {
            ifOk.accept(value);
        }
    }

    @Override
    public boolean isError() {
        return message != null;
    }

    @Override
    public Optional<String> getMessage() {
        return Optional.ofNullable(message);
    }

    @Override
    public <X extends Throwable> T getOrThrow(SerializableFunction<String, ? extends X> exceptionProvider) throws X {
        if (isError()) {
            throw exceptionProvider.apply(message);
        }

        return value;
    }
}
