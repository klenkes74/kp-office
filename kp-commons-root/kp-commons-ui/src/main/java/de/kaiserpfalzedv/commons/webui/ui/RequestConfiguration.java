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

package de.kaiserpfalzedv.commons.webui.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import java.io.Serializable;
import java.util.UUID;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-07-02
 */
@RequestScoped
public class RequestConfiguration implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(RequestConfiguration.class);

    @Produces
    @RequestScoped
    private UUID ui = UUID.randomUUID();

    @Produces
    @RequestScoped
    private SerializableEventBus bus = new SerializableEventBus("kpo-request-" + ui.toString());


    @PostConstruct
    public void init() {
        LOG.info("Created {}: ui={}, bus={}", getClass().getSimpleName(), ui, bus);
    }
}
