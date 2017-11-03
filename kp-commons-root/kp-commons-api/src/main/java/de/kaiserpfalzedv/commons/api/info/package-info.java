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

/**
 * These interfaces allow the Vaadin views to work on the needed data. There is no implementation in the
 * KP Commons library. You need to implement them, to use the default views for displaying version info and
 * the license of the software. A producer for
 * {@link de.kaiserpfalzedv.commons.api.info.SoftwareInformation} needs to be implemented for the presenter
 * of the version screen:
 * <p>
 * <code><pre>import javax.enterprise.inject.Produces;
 * <p>
 * public class SoftwareInformationProducer {
 *   @Produces
 *   public SoftwareInformation getSoftwareInformation() {
 *     [... implement here ...]
 *   }
 * }</pre></code>
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 1.0.0
 */
package de.kaiserpfalzedv.commons.api.info;