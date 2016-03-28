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

package de.kaiserpfalzEdv.vaadin.ui.mvp;

import java.io.Serializable;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 09.09.15 20:24
 */
public interface Presenter<V extends View> extends Serializable {
    V getView();

    void setView(V view);

    void navigateTo(String target);


    void notate(String caption, String description);

    void notate(String caption, String description, Object... params);

    void notate(String description);

    void notate(String description, Object... params);


    void warn(String caption, String description);

    void warn(String description);

    void warn(String caption, String description, Object... params);

    void warn(String description, Object... params);


    void error(String caption, String description);

    void error(String description);

    void error(String caption, String description, Object... params);

    void error(String description, Object... params);


    String translate(final String key);

    String translate(final String key, final Object... params);
}