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

package de.kaiserpfalzedv.office.webui.ui;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.HashSet;

import javax.annotation.PostConstruct;
import javax.persistence.PreRemove;
import javax.persistence.Transient;

import com.google.common.base.MoreObjects;
import com.google.common.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * A sierializable decorator for the {@link EventBus} from Google Guava.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-07-03
 */
public class SerializableEventBus implements Serializable {
    private static final long serialVersionUID = 6044659478111891857L;
    private static final Logger LOG = LoggerFactory.getLogger(SerializableEventBus.class);

    private final HashSet<Object> subscribers = new HashSet<>();
    private final String name;

    @Transient
    private final EventBus bus;


    public SerializableEventBus() {
        bus = new EventBus();

        name = bus.identifier();
    }


    public SerializableEventBus(final String name) {
        bus = new EventBus(name);

        this.name = name;
    }


    @PostConstruct
    public void init() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Created bus: {}", this);
        }
    }

    @PreRemove
    public void close() {
        if (LOG.isDebugEnabled() && !LOG.isTraceEnabled()) {
            LOG.debug("Unregister objects while closing bus {}: {}", this, subscribers);
        }

        for (Object o : subscribers) {
            if (LOG.isTraceEnabled()) {
                LOG.trace("Unregister object while closing bus {}: {}", this, o);
            }

            bus.unregister(o);
        }
    }


    /**
     * Registers a {@link Serializable} object to the underlying event bus.
     *
     * @param object The object to be registered as receiver for notifications.
     *
     * @throws IllegalArgumentException If the object is not {@link Serializable}.
     */
    public void register(Object object) {
        checkArgument(Serializable.class.isAssignableFrom(object.getClass()),
                      "Sorry, only serializable objects may register ('{}' is not serializable)!", object
        );

        if (LOG.isDebugEnabled()) {
            LOG.debug("Registering object to bus {}: {}", this, object);
        }

        if (Serializable.class.isAssignableFrom(object.getClass())) {
            subscribers.add(object);
        } else {
            LOG.info("Object {} will be registered to bus {} but not serialized.", object, this);
        }

        bus.register(object);
    }


    /**
     * Unregisters a {@link Serializable} object from the underlying event bus.
     *
     * @param object The object to be unregistered as receiver for notifications.
     *
     * @throws IllegalArgumentException If the object is not {@link Serializable}.
     */
    public void unregister(Object object) {
        checkArgument(Serializable.class.isAssignableFrom(object.getClass()),
                      "Sorry, only serializable objects may register ('{}' is not serializable)!", object
        );

        if (LOG.isDebugEnabled()) {
            LOG.debug("Unregistering object from bus {}: {}", this, object);
        }

        subscribers.remove(object);
        bus.unregister(object);
    }


    /**
     * Posts an event to the serializable event bus.
     *
     * @param event The event to be posted.
     */
    public void post(Serializable event) {
        if (LOG.isTraceEnabled()) {
            LOG.trace("Posting event: {}", event);
        }

        bus.post(event);

        if (LOG.isTraceEnabled()) {
            LOG.trace("Posted event: {}", event);
        }
    }


    /**
     * Serializes the object. Before all subscribers are unregistered from the underlying {@link EventBus}.
     *
     * @param out The output stream to write the object data to.
     *
     * @throws IOException If the object can not be serialized
     */
    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        if (LOG.isDebugEnabled() && !LOG.isTraceEnabled()) {
            LOG.debug("Unregistering before serialization objects from bus {}: {}", this, subscribers);
        }

        for (Object o : subscribers) {
            if (LOG.isTraceEnabled()) {
                LOG.trace("Unregistering before serialization from bus {}: {}", this, o);
            }

            bus.unregister(o);
        }

        out.defaultWriteObject();
    }


    /**
     * Deserializes the object. All subscribers are reregistered to the underlying {@link EventBus}.
     *
     * @param in The input stream to read the object data from.
     *
     * @throws IOException If the object can not be deserialized
     */
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();

        if (LOG.isDebugEnabled() && !LOG.isTraceEnabled()) {
            LOG.debug("Re-Registering after deserialization objects to bus {}: {}", this, subscribers);
        }

        for (Object o : subscribers) {
            if (LOG.isTraceEnabled()) {
                LOG.trace("Re-Registering after deserialization to bus {}: {}", this, o);
            }

            bus.register(o);
        }
    }


    /**
     * Deserializes an object without any data.
     *
     * @throws ObjectStreamException If the object can not be deserialized.
     */
    private void readObjectNoData() throws ObjectStreamException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Deserializing a bus without any subscribers: {}", this);
        }
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).toString();
    }
}
