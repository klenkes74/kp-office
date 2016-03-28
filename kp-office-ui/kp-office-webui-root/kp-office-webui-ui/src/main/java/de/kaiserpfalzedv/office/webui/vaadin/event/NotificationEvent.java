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

package de.kaiserpfalzEdv.vaadin.event;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 09.09.15 20:03
 */
public class NotificationEvent extends BaseEvent<NotificationPayload> {
    private static final long serialVersionUID = -2385655891384342903L;

    public NotificationEvent(Object source, NotificationPayload payload) {
        super(source, payload);
    }
}
