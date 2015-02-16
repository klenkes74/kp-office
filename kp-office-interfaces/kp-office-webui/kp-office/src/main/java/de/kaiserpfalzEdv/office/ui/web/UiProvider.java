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

package de.kaiserpfalzEdv.office.ui.web;

import com.vaadin.ui.UI;
import org.springframework.web.context.WebApplicationContext;
import org.vaadin.spring.VaadinUI;
import org.vaadin.spring.servlet.SpringAwareUIProvider;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 08.02.15 12:52
 */
public class UiProvider extends SpringAwareUIProvider {
    public UiProvider(WebApplicationContext webApplicationContext) {
        super(webApplicationContext);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void detectUIs() {
        logger.info("Checking the application context for Vaadin UIs for provider: {}", this);
        final String[] uiBeanNames = getWebApplicationContext().getBeanNamesForAnnotation(VaadinUI.class);
        for (String uiBeanName : uiBeanNames) {
            Class<?> beanType = getWebApplicationContext().getType(uiBeanName);
            if (UI.class.isAssignableFrom(beanType)) {
                final String path = getWebApplicationContext().findAnnotationOnBean(uiBeanName, VaadinUI.class).path();
                Class<? extends UI> existingBeanType = getUIByPath(path);

                logger.info("Found Vaadin UI [{} -> {}]", path, beanType.getCanonicalName());

                if (existingBeanType != null) {
                    throw new IllegalStateException(String.format("[%s] is already mapped to the path [%s]", existingBeanType.getCanonicalName(), path));
                }
                logger.debug("Mapping Vaadin UI [{}] to path [{}]", beanType.getCanonicalName(), path);
                mapPathToUI(path, (Class<? extends UI>) beanType);
            }
        }
    }

    protected void mapPathToUI(String path, Class<? extends UI> uiClass) {
        logger.debug("Mapping '{}' to UI '{}' for provider {}.", path, uiClass.getSimpleName(), this);
        
        super.mapPathToUI(path, uiClass);
    }

    protected Class<? extends UI> getUIByPath(String path) {
        logger.debug("Searching provider {} for UI for path '{}' ...", this, path);
        
        return super.getUIByPath(path);
    }
}
