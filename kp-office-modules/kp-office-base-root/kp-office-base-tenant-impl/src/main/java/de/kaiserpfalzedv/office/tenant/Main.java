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

package de.kaiserpfalzedv.office.tenant;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.spi.api.JARArchive;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-17
 */
public class Main {
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String... args) throws Exception {
        JARArchive deployment = createDeployment();

        Swarm swarm = new Swarm(args);
        swarm.deploy(deployment);
        swarm.start();
    }

    private static JARArchive createDeployment() {
        JARArchive deployment = ShrinkWrap.create(JARArchive.class);
        deployment.addPackage("de.kaiserpfalz.office");

        return deployment;
    }
}