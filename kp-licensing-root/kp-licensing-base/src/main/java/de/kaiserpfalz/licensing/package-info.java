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
 * A licensing and meta information service for the software. The base is the nice package of
 * {@link https://github.com/verhas/License3j License3j - Free Licence Management Library} which implements a
 * cryptographic license based on GPG.
 * <p>
 * The KP Office is open source. So licensing normally would not be needed. But there are some parts which will cost
 * us <em>real money</em> to implement (like the German tax export module, where we will have to pay the tax office for
 * certificating our exports). And we need to get that money back. So these modules won't be licensed without fee. Don't
 * know when we will implement them. But I liked the idea of incorporating this license manager, so I did it.
 * <p>
 * Just annotate the licenced classes or method calls with {@link de.kaiserpfalzedv.commons.api.licensing.Licensed} and the
 * {@link de.kaiserpfalzedv.office.metainfo.impl.license.LicenseInterceptor CDI interceptor} will check the calls if a matching
 * feature exists within the {@link de.kaiserpfalzedv.commons.api.licensing.ApplicationLicense license}.
 * <p>
 * The implementation will use the {@link de.kaiserpfalzedv.office.metainfo.impl.license.OfficeLicenseImpl} to work with (which
 * in turn uses the {@link com.verhas.licensor.License} as backend for most functionality). But the user of the library
 * will only get access to the {@link de.kaiserpfalzedv.commons.impl.info.ApplicationLicenseTO transfer object} which does
 * not contain any hint on License3j.
 * <p>
 * Future plans include a licensing server for end user licenses and a licensing server for floating licenses.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-10-31
 */
package de.kaiserpfalz.licensing;