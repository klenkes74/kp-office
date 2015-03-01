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

package de.kaiserpfalzEdv.office.commons.i18n.test;

import de.kaiserpfalzEdv.commons.test.CommonTestBase;
import de.kaiserpfalzEdv.office.commons.i18n.MessageBuilder;
import de.kaiserpfalzEdv.office.commons.i18n.MessageKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Iterator;

import static org.testng.Assert.assertEquals;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 13.02.15 02:58
 */
@Test
public class MessageBuilderTest extends CommonTestBase {
    private static final Logger LOG = LoggerFactory.getLogger(MessageBuilderTest.class);


    public MessageBuilderTest() {
        super(MessageBuilderTest.class, LOG);
    }


    @Test(dataProvider = "simple-message-provider")
    public void testSimpleMessageWithoutData(
            @NotNull final String testTag,
            final String key,
            final String defaultMessage,
            @NotNull final String resultKey,
            @NotNull final String resultDefaultMessage
    ) {
        logMethod(testTag, "Checking a simple message with key='" + key + "' and defaultMessage='" + defaultMessage + "'.");

        MessageKey result = new MessageBuilder().withKey(key).withMessage(defaultMessage).build();

        assertEquals(result.getKey(), resultKey, "Wrong message key!");
        assertEquals(result.getDefaultMessage(), resultDefaultMessage, "Wrong default message set (should be the same as the key)!");
        assertEquals(result.getDetailData().size(), 0, "There should be no detail data!");
    }


    @DataProvider(name = "simple-message-provider", parallel = true)
    public Iterator<Object[]> simpleMessages() {
        ArrayList<Object[]> result = new ArrayList<>();

        result.add(new Object[]{"msg-key-no-defmsg", "A1000", null, "A1000", "A1000"});
        result.add(new Object[]{"msg-no-key-defmsg", null, "A1000", "A1000", "A1000"});
        result.add(new Object[]{"msg-key-defmsg", "A1000", "Default message.", "A1000", "Default message."});

        return result.iterator();
    }
}
