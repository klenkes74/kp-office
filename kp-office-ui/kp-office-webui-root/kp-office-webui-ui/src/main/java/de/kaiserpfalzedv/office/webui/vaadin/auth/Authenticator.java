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

package de.kaiserpfalzEdv.vaadin.auth;

import com.ejt.vaadin.loginform.LoginForm;
import com.vaadin.spring.access.ViewAccessControl;
import com.vaadin.ui.UI;
import de.kaiserpfalzEdv.vaadin.backend.auth.AuthenticationProvider;
import de.kaiserpfalzEdv.vaadin.backend.auth.CurrentUserStore;
import de.kaiserpfalzEdv.vaadin.backend.auth.LoginFailedException;
import de.kaiserpfalzEdv.vaadin.backend.auth.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 06.09.15 06:53
 */
@Service
public class Authenticator implements LoginForm.LoginListener, ViewAccessControl, ApplicationContextAware {
    private static final Logger                  LOG         = LoggerFactory.getLogger(Authenticator.class);
    /**
     * A local cache for caching all access annotations.
     */
    private final        HashMap<String, Access> accessCache = new HashMap<>();
    private AuthenticationProvider provider;
    private CurrentUserStore       store;
    private ApplicationContext     context;


    @Autowired
    public Authenticator(
            final AuthenticationProvider provider,
            final CurrentUserStore store
    ) {
        this.provider = provider;
        this.store = store;
    }

    @Override
    public void onLogin(LoginForm.LoginEvent loginEvent) {
        try {
            provider.signIn(loginEvent.getUserName(), loginEvent.getPassword());
        } catch (LoginFailedException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);
        }
    }


    public User user() {
        return store.getCurrentUser();
    }


    public boolean userInRole(final String roleName) {
        return user().isInRole(roleName);
    }


    @Override
    public boolean isAccessGranted(UI ui, String beanName) {
        LOG.trace("Checking access for: ui={}, beanName={}", ui, beanName);

        Access annotation = getAnnotationForBeanClass(beanName);
        if (annotation == null) {
            LOG.warn("Not secured view: {}", beanName);
            return true;
        }

        boolean result = false;
        for (String r : annotation.value())
            result = result || user().isInRole(r);

        return result;
    }

    private Access getAnnotationForBeanClass(String beanName) {
        if (!accessCache.containsKey(beanName)) {
            Access annotation = context.getBean(beanName).getClass().getAnnotation(Access.class);
            accessCache.put(beanName, annotation);
        }

        return accessCache.get(beanName);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        LOG.trace("Change application context: {} -> {}", context, applicationContext);

        this.context = applicationContext;
    }
}
