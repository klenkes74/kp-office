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

package de.kaiserpfalzEdv.office.core.licence.impl;

/**
 * <p>The licence managing for the whole Kaiserpfalz Office. License Check is done on a module level via the open source
 * <a href="https://github.com/verhas/License3j">License3j</a> from <a href="https://github.com/verhas">Peter
 * Verhas</a>.</p>
 *
 * <p>License public key is needed within the classpath as file
 * <code>classpath:/{@value de.kaiserpfalzEdv.office.core.licence.impl.LicenceServiceImpl#KEY_RING}</code>. The licence
 * file should be configures via <code>application.properties</code>. As fallback a file named
 * {@value de.kaiserpfalzEdv.office.core.licence.impl.LicenceServiceImpl#LICENSE_FILE} in the startup directory of the
 * software is used.</p>
 *
 * <p>The licence check is annotated per aspect {@link de.kaiserpfalzEdv.office.core.licence.impl.LicenceManager} to
 * every method within every class named like <code>...ModuleImpl</code> and annotated with
 * {@link de.kaiserpfalzEdv.office.core.licence.ModuleInformation}. If the parameter
 * {@link de.kaiserpfalzEdv.office.core.licence.ModuleInformation#needsLicence()} is set to <code>true</code>, then the
 * feature {@link de.kaiserpfalzEdv.office.core.licence.ModuleInformation#featureName()} is checked within the licence.
 * If there is no licence or the licence is invalid, an
 * {@link de.kaiserpfalzEdv.office.core.licence.LicencingException} or one of its subclasses is thrown.</p>
 *
 * <p>The check is also applied to method calls within classes named like <code>...ModuleImpl</code> if they are
 * annotated with {@link de.kaiserpfalzEdv.office.core.licence.ModuleInformation}. This may be useful with a
 * <a href="http://en.wikipedia.org/wiki/Service_locator_pattern">Service Locator</a>.</p>
 *
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @version 0.1.0
 * @since 0.1.0
 */
