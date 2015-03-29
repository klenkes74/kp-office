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

package de.kaiserpfalzEdv.office.commons.jackson;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.Versioned;
import com.fasterxml.jackson.core.util.VersionUtil;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 29.03.15 08:56
 */
public class PackageVersion implements Versioned {
    public final static Version VERSION = VersionUtil.parseVersion(
            "1.0.0", "de.kaiserpflzEdv.office.commons.jackson", "kpo-version-datatype"
    );

    @Override
    public Version version() {
        return VERSION;
    }
}
