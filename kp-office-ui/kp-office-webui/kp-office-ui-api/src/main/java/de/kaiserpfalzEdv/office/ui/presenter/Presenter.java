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

package de.kaiserpfalzEdv.office.ui.presenter;

import de.kaiserpfalzEdv.commons.jee.eventbus.EventBusHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.inject.Named;
import java.io.Serializable;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 12.07.15 06:10
 */
@Named
public class Presenter<T> implements ApplicationContextAware, Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(Presenter.class);


    /**
     * The spring application context to retrieve the event bus from.
     */
    private ApplicationContext context;

    /**
     * The view this presenter is created for.
     */
    private T view;


    public Presenter() {
    }

    public void init() {

    }

    public T getView() {
        return view;
    }

    public void setView(T view) {
        this.view = view;
    }

    public EventBusHandler getEventBus() {
        return context.getBean("guavaEventBus", EventBusHandler.class);
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
