/*
 * Copyright 2017 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzedv.office.common.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The two default loggers. No reason to have more than one instance of these two loggers.
 * 
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-10-31
 */
public class Logging {
    /**
     * The operations logger.
     */
    public static final Logger OPLOG = LoggerFactory.getLogger("OPERATIONS");

    /**
     * The business logger.
     */
    public static final Logger BUSLOG = LoggerFactory.getLogger("BUSINESS");
}
