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

package de.kaiserpfalzEdv.office.commons;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>The configuration of an OfficeModule. It is used for licensing via the
 * <code>de.kaiserpfalzEdv.office.core.licence.LicensingInterceptor</code> or to configure the version of the
 * office module via a {@link de.kaiserpfalzEdv.commons.service.Versionable} generated from {@link #version()}.</p>
 * <p>
 * <p>The ID is not used but should be configures via a new UUID. If you don't have a generator handy, the webpage
 * <a href="https://www.uuidgenerator.net/">Online UUID Generator</a> may come handy (best use Version 1 UUIDs).</p>
 *
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface ModuleInformation {
    /**
     * @return The ID of the licence needed.
     */
    String id() default "00000000-0000-0000-0000-000000000000";

    /**
     * @return The official name of the licence needed.
     */
    String name() default "No Licence needed";

    /**
     * @return The feature name to check in the licence file.
     */
    String featureName() default "core";


    /**
     * @return The version of this service.
     */
    String version() default "0-alpha";

    /**
     * @return If this module needs a licence at all.
     */
    boolean needsLicence() default false;
}
