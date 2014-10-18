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

package de.kaiserpfalzEdv.office.communication;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import de.kaiserpfalzEdv.commons.test.CommonTestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import javax.validation.constraints.NotNull;

import static org.testng.Assert.assertEquals;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @version 0.1.0
 * @since 0.1.0
 */
@Test
public class GuavaEventBusCommunicationsTest extends CommonTestBase {
    private static final Logger LOG = LoggerFactory.getLogger(GuavaEventBusCommunicationsTest.class);

    private static final GuavaEventBusChannel CHANNEL = new GuavaEventBusChannel(new EventBus());
    private static final GuavaEventBusSender SENDER = new GuavaEventBusSender();

    public GuavaEventBusCommunicationsTest() {
        super(GuavaEventBusCommunicationsTest.class, LOG);
    }


    public void checkSendAndReceive() {
        CHANNEL.register(this);

        TestCommand command = new TestCommand();

        TestNotification response = SENDER.sendAndReceive(CHANNEL, command);

        CHANNEL.unregister(this);

        assertEquals(response.getCommandId(), command.getId(), "Command IDs don't match!");
    }


    @Test(enabled = false) // this is no test :-)
    @Subscribe
    public void receiveCommand(@NotNull TestCommand command) {
        command.handleCommand();
        SENDER.send(CHANNEL, new TestNotification(command));
    }
}
