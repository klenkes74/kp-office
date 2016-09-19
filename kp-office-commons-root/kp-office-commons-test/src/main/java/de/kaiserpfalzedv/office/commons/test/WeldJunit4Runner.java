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

package de.kaiserpfalzedv.office.commons.test;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-05
 */
public class WeldJunit4Runner extends BlockJUnit4ClassRunner {
    private static final Logger LOG = LoggerFactory.getLogger(WeldJunit4Runner.class);

    /**
     * The test class to run.
     */
    private final Class<?> clasz;
    /**
     * Weld infrastructure.
     */
    private final Weld weld;
    /**
     * The container itself.
     */
    private final WeldContainer container;

    /**
     * Runs the class passed as a parameter within the container.
     *
     * @param clasz to run
     *
     * @throws InitializationError if anything goes wrong.
     */
    public WeldJunit4Runner(final Class<Object> clasz) throws InitializationError {
        super(clasz);

        this.clasz = clasz;
        this.weld = new Weld();
        this.container = weld.initialize();
    }


    @Override
    protected Object createTest() throws Exception {
        return container.instance().select(clasz).get();
    }
}
