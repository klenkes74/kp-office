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

package de.kaiserpfalzEdv.commons.jndi;

import org.osjava.sj.SimpleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;
import java.util.Hashtable;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * A small extension to the default {@link org.osjava.sj.SimpleContextFactory}. If a system property named
 * {@value #CONFIG_FILE_NAME_PROPERTY} exists, then the configuration base directory is set to that directory.
 * A <b>full</b> path name has to be configured.
 *
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public class SimpleContextFactory implements InitialContextFactory {
    public static final String CONFIG_FILE_NAME_PROPERTY = "de.kaiserpfalz-edv.office.config-path";
    private static final Logger LOG = LoggerFactory.getLogger(SimpleContextFactory.class);

    @Override
    public Context getInitialContext(Hashtable<?, ?> environment) throws NamingException {
        Hashtable<Object, Object> env = new Hashtable<>(environment.size());

        for (Object key : environment.keySet()) {
            env.put(key, environment.get(key));
        }

        String configPath = System.getProperty(CONFIG_FILE_NAME_PROPERTY);
        if (isNotBlank(configPath)) {
            env.remove(SimpleContext.SIMPLE_ROOT);
            env.put(SimpleContext.SIMPLE_ROOT, configPath);
        } else {
            configPath = (String) env.get(SimpleContext.SIMPLE_ROOT);
        }

        LOG.trace("***** Loading configuration from: {}", configPath);

        return new SimpleContext(env);
    }
}
