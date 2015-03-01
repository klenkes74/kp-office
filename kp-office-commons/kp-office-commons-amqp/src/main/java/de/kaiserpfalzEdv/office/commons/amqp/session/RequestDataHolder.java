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

package de.kaiserpfalzEdv.office.commons.amqp.session;

import java.util.HashMap;
import java.util.Map;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 01.03.15 14:43
 */
public class RequestDataHolder extends ThreadLocal<Map<String, Object>> {
    @Override
    protected HashMap<String, Object> initialValue() {
        return new HashMap<>();
    }


    public Object get(final String key) throws AmqpSessionException {
        if (!get().containsKey(key))
            throw new AmqpSessionException("AMQP request contains no value for key '" + key + "'.");

        return get().get(key);
    }

    public Object get(final String key, final Object defaultValue) {
        try {
            return get(key);
        } catch (AmqpSessionException e) {
            return defaultValue;
        }
    }

    public void set(final String key, final Object value) {
        get().put(key, value);
    }

    public void unset(final String key) {
        get().remove(key);
    }

    public boolean contains(final String key) {
        return get().containsKey(key);
    }
}
