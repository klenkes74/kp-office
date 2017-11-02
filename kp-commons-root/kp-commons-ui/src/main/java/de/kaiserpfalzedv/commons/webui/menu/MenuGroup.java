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

package de.kaiserpfalzedv.commons.webui.menu;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author rlic
 * @version 1.0.0 LAS-31
 * @since 27.08.15 19:36
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface MenuGroup {
    /**
     * @return The name of the view as found by CDI.
     */
    String name();

    /**
     * @return The key to be looked up for internationalization.
     */
    String captionKey() default "";

    String caption() default "";

    String descriptionKey() default "";

    String description() default "";

    String menuGroup() default "";

    /**
     * @return The order of this entry.
     */
    int order() default 10;

    /**
     * @return if a separator is to be displayed before this entry.
     */
    boolean separator() default false;
}
