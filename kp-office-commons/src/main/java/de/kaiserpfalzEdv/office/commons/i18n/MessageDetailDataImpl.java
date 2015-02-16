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

package de.kaiserpfalzEdv.office.commons.i18n;

import java.io.Serializable;
import java.util.Locale;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 13.02.15 02:24
 */
class MessageDetailDataImpl<T extends Serializable> implements MessageDetailData {
    private static final long serialVersionUID = -4068490192983589926L;
    
    private T data;
    
    public MessageDetailDataImpl(final T data) {
        this.data = data;
    }


    @Override
    public T getValue() {
        return data;
    }

    
    @Override
    public String getValue(Locale locale) {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzEdv.office.core.i18n.MessageObjectImpl.getValue
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    
    @Override
    public Class<?> getType() {
        return data.getClass();
    }
}
