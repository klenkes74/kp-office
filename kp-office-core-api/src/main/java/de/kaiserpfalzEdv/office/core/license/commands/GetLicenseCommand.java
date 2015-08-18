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

package de.kaiserpfalzEdv.office.core.license.commands;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import de.kaiserpfalzEdv.office.core.license.notifications.LicenseDataNotification;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 01.03.15 22:14
 */
@JsonAutoDetect(fieldVisibility = ANY)
public class GetLicenseCommand implements LicenseCommand {
    private static final long serialVersionUID = 6267589569621720307L;


    @Override
    public LicenseDataNotification execute(LicenseCommandExecutor executor) {
        return executor.execute(this);
    }
}