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

package de.kaiserpfalzedv.commons.webui.components;

import java.io.Serializable;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;

import de.kaiserpfalzedv.commons.webui.events.SerializableEventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-07-02
 */
@SessionScoped
public class SessionConfiguration implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(SessionConfiguration.class);

    @Produces
    @SessionScoped
    private UUID ui = UUID.randomUUID();

    @Produces
    @SessionScoped
    private SerializableEventBus bus = new SerializableEventBus("kpo-session-" + ui.toString());


    @PostConstruct
    public void init() {
        LOG.info("Created {}: ui={}, bus={}", getClass().getSimpleName(), ui, bus);
    }
}
