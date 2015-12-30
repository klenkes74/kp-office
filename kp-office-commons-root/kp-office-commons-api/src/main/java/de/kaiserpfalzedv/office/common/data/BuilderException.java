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

package de.kaiserpfalzedv.office.common.data;

import de.kaiserpfalzedv.office.common.BaseSystemException;

/**
 * This exception is thrown when a KP defined Builder for any object failes.
 *
 * @author klenkes
 * @version 2015Q1
 * @since 29.12.15 19:16
 */
public class BuilderException extends BaseSystemException {
    private static final long serialVersionUID = 1483860261806629962L;

    private Class<?> clasz;
    private String[] failures;

    public BuilderException(Class<?> clasz, String[] failures) {
        super(generateMessage(clasz, failures));
    }

    private static String generateMessage(Class<?> clasz, String[] failures) {
        StringBuilder result = new StringBuilder("Can't create ")
                .append(clasz.getSimpleName()).append(". Failures are:");

        for (String f : failures) {
            result.append("\n- ").append(f);
        }

        return result.toString();
    }

    public Class<?> getClasz() {
        return clasz;
    }

    public String[] getFailures() {
        return failures;
    }
}
