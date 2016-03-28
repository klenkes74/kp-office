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

package de.kaiserpfalzEdv.vaadin.ui.defaultviews.list;

import java.io.Serializable;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 14.09.15 04:10
 */
public class TableDescription implements Serializable {
    private static final long serialVersionUID = 5030892575507021955L;


    private String fieldName;
    private String i18nHeadingKey;
    private int    expandRatio;

    public TableDescription(String fieldName, int expandRatio) {
        this.fieldName = fieldName;
        this.expandRatio = expandRatio;

        this.i18nHeadingKey = "heading." + fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getI18nHeadingKey() {
        return i18nHeadingKey;
    }

    public TableDescription setI18nHeadingKey(String i18nHeadingKey) {
        this.i18nHeadingKey = i18nHeadingKey;
        return this;
    }

    public int getExpandRatio() {
        return expandRatio;
    }
}
