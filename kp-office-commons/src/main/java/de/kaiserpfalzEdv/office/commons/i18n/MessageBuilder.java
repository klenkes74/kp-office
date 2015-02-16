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

import org.apache.commons.lang3.builder.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import static com.google.common.base.Preconditions.checkState;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 13.02.15 02:47
 */
public class MessageBuilder implements Builder<MessageKey> {
    private static final Logger LOG = LoggerFactory.getLogger(MessageBuilder.class);

    private String key;
    private String defaultMessage;

    private  final LinkedList<MessageDetailData<? extends Serializable>> data = new LinkedList<>();

    @Override
    public MessageKey build() {
        setDefaults();
        validate();
        
        return new MessageKeyImpl(key, defaultMessage, data);
    }
    
    private void setDefaults() {
        if (isBlank(defaultMessage) && isNotBlank(key)) {
            defaultMessage = key;
        }
        
        if (isBlank(key) && isNotBlank(defaultMessage)) {
            key = defaultMessage;
        }
    }

    private void validate() {
        checkState(isNotBlank(key), "The key of a message must not be blank!");
    }
    
    
    public void reset() {
        key = null;
        data.clear();
    }
    
    
    public MessageBuilder withKey(final String key) {
        this.key = key;
        
        return this;
    }
    
    
    public MessageBuilder withMessage(final String message) {
        this.defaultMessage = message;
        
        return this;
    }
    
    
    public MessageBuilder withErrorMessage(final ErrorMessage msg) {
        this.key = msg.toString();
        this.defaultMessage = msg.getMessage();
        
        return this;
    }
    
    
    @SuppressWarnings("unchecked")
    public <S extends Serializable> MessageBuilder withData(@NotNull final S data) {
        this.data.add(new MessageDetailDataImpl<>(data));
        
        return this;
    }
    
    @SuppressWarnings("unchecked")
    public MessageBuilder withData(@NotNull final List<? extends Serializable> data) {
        data.forEach(d->{this.data.add(new MessageDetailDataImpl<>(d));});
        
        return this;
    }
}
