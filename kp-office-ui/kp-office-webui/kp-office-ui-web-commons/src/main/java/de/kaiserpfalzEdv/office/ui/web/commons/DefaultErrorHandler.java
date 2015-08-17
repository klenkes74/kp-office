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

package de.kaiserpfalzEdv.office.ui.web.commons;

import com.vaadin.server.ErrorEvent;
import com.vaadin.server.ErrorHandler;
import com.vaadin.ui.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;

import static com.vaadin.ui.Notification.Type.ERROR_MESSAGE;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 17.08.15 08:34
 */
@Named
public class DefaultErrorHandler implements ErrorHandler {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultErrorHandler.class);

    @Override
    public void error(ErrorEvent event) {
        Throwable cause = event.getThrowable();
        String message = cause.getClass().getSimpleName() + ": " + cause.getMessage();
        LOG.error(message, cause);

        Notification.show(message, ERROR_MESSAGE);
    }
}
