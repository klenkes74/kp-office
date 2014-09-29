/*
 * Copyright (c) 2014 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzEdv.office.security.shiro.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionFactory;
import org.apache.shiro.session.mgt.SimpleSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import java.io.Serializable;

/**
 * @author klenkes
 * @since 2014Q
 */
@Named
public class OfficeSessionFactory implements SessionFactory {
    private static final Logger LOG = LoggerFactory.getLogger(OfficeSessionFactory.class);

    @Override
    public Session createSession(SessionContext initData) {
        SimpleSession result = new SimpleSession();
        if (initData != null) {
            String host = initData.getHost();
            Serializable id = initData.getSessionId();

            if (host != null) {
                result.setHost(host);
            }

            if (id != null) {
                result.setId(id);
            }
        }

        return result;
    }
}
