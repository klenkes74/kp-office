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

/**
 * <p>The license managing for the whole Kaiserpfalz Office. License Check is done on a module level via the open source
 * <a href="https://github.com/verhas/License3j">License3j</a> from <a href="https://github.com/verhas">Peter 
 * Verhas</a>.</p>
 *
 * <p>License public key is needed within the classpath as file <code>classpath:/kpoffice.pub</code>. The license file
 * should be configures via <code>application.properties</code>. As fallback a file named <code>kpoffice.lic</code> in 
 * the startup directory of the software is used.</p>
 *
 * <p>The license check is annotated per aspect to every method within every class named like <code>...ModuleImpl</code>
 * and annotated with {@link de.kaiserpfalzEdv.office.commons.ModuleInformation}. If the parameter
 * {@link de.kaiserpfalzEdv.office.commons.ModuleInformation#needsLicence()} is set to <code>true</code>, then the
 * feature {@link de.kaiserpfalzEdv.office.commons.ModuleInformation#featureName()} is checked within the license.
 * If there is no license or the license is invalid, an {@link de.kaiserpfalzEdv.office.core.license.LicensingException}
 * or one of its subclasses is thrown.</p>
 *
 * <p>The check is also applied to method calls within classes named like <code>...ModuleImpl</code> if the methods are
 * annotated with {@link de.kaiserpfalzEdv.office.commons.ModuleInformation}. This may be useful with a
 * <a href="http://en.wikipedia.org/wiki/Service_locator_pattern">Service Locator</a>.</p>
 */
package de.kaiserpfalzEdv.office.core.license;