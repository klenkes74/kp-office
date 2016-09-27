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

package de.kaiserpfalzedv.office.common;

import java.io.Serializable;
import java.time.Instant;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;

import org.slf4j.MDC;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-27
 */
public class MessageInfo implements Map<String, String>, Serializable {
    private static final long serialVersionUID = 5250386996604777645L;

    private final HashMap<String, String> properties = new HashMap<>(20);

    private Destination replyTo;
    private Instant deliveryTime;


    public MessageInfo(final Message message) {
        try {
            replyTo = message.getJMSReplyTo();
            deliveryTime = Instant.ofEpochMilli(message.getJMSDeliveryTime());

            properties.put("message-id", message.getJMSMessageID());
            properties.put("correlation-id", message.getJMSCorrelationID());

            Enumeration<String> props = message.getPropertyNames();

            while (props.hasMoreElements()) {
                String key = props.nextElement();
                properties.put(key, message.getStringProperty(key));
            }
        } catch (JMSException e) {
            // No properties will be read.
        }
    }

    public Destination getReplyTo() {
        return replyTo;
    }

    public Instant getDeliveryTime() {
        return deliveryTime;
    }

    public String getActionType() {
        return properties.get("action-type");
    }


    public void setMDC() {
        properties.forEach(MDC::put);
    }

    public void removeMDC() {
        properties.forEach((k, v) -> MDC.remove(k));
    }


    public int size() {
        return properties.size();
    }

    public boolean isEmpty() {
        return properties.isEmpty();
    }

    public boolean containsKey(Object key) {
        return properties.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return properties.containsValue(value);
    }

    public String get(Object key) {
        return properties.get(key);
    }

    public String put(String key, String value) {
        return properties.put(key, value);
    }

    public String remove(Object key) {
        return properties.remove(key);
    }

    public void putAll(Map<? extends String, ? extends String> m) {
        properties.putAll(m);
    }

    public void clear() {
        properties.clear();
    }

    public Set<String> keySet() {
        return properties.keySet();
    }

    public Collection<String> values() {
        return properties.values();
    }

    public Set<Entry<String, String>> entrySet() {
        return properties.entrySet();
    }

    public String getOrDefault(Object key, String defaultValue) {
        return properties.getOrDefault(key, defaultValue);
    }

    public void forEach(BiConsumer<? super String, ? super String> action) {
        properties.forEach(action);
    }

    public void replaceAll(BiFunction<? super String, ? super String, ? extends String> function) {
        properties.replaceAll(function);
    }

    public String putIfAbsent(String key, String value) {
        return properties.putIfAbsent(key, value);
    }

    public boolean remove(Object key, Object value) {
        return properties.remove(key, value);
    }

    public boolean replace(String key, String oldValue, String newValue) {
        return properties.replace(key, oldValue, newValue);
    }

    public String replace(String key, String value) {
        return properties.replace(key, value);
    }

    public String computeIfAbsent(String key, Function<? super String, ? extends String> mappingFunction) {
        return properties.computeIfAbsent(key, mappingFunction);
    }

    public String computeIfPresent(String key, BiFunction<? super String, ? super String, ? extends String> remappingFunction) {
        return properties.computeIfPresent(key, remappingFunction);
    }

    public String compute(String key, BiFunction<? super String, ? super String, ? extends String> remappingFunction) {
        return properties.compute(key, remappingFunction);
    }

    public String merge(String key, String value, BiFunction<? super String, ? super String, ? extends String> remappingFunction) {
        return properties.merge(key, value, remappingFunction);
    }
}
