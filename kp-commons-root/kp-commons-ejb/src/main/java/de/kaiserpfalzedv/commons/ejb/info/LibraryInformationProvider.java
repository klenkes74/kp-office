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

package de.kaiserpfalzedv.commons.ejb.info;

import com.github.zafarkhaja.semver.Version;
import de.kaiserpfalzedv.commons.api.data.ValidityDuration;
import de.kaiserpfalzedv.commons.api.data.VersionRange;
import de.kaiserpfalzedv.commons.api.info.SoftwareLibrary;
import de.kaiserpfalzedv.commons.api.info.SoftwareLicense;
import de.kaiserpfalzedv.commons.impl.info.SoftwareLibraryBuilder;
import de.kaiserpfalzedv.commons.impl.license.OpenSourceLicense;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.inject.Produces;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.HashSet;

import static de.kaiserpfalzedv.commons.impl.license.OpenSourceLicense.*;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 1.0.0
 */
public class LibraryInformationProvider {
    private static final Logger LOG = LoggerFactory.getLogger(LibraryInformationProvider.class);

    private static final Object[][] libraries = new Object[][]{
            new Object[]{"Vaadin", "---", APACHEv2_0},
            new Object[]{"Bean Validation (JSR-303) API", "---", APACHEv2_0},
            new Object[]{"Apache Commons (CLI, Discovery, IO, Jexl, Lang, Logging, Math)", "---", APACHEv2_0},
            new Object[]{"Google Collections", "---", APACHEv2_0},
            new Object[]{"Google GWT", "---", APACHEv2_0},
            new Object[]{"Closure Stylesheets", "---", APACHEv2_0},
            new Object[]{"JSON", "---", APACHEv2_0},
            new Object[]{"JSoup", "---", MIT},
            new Object[]{"streamhtmlparser", "---", BSD_3},
            new Object[]{"Atmosphere Framework", "---", APACHEv2_0},
            new Object[]{"SLF4J", "---", MIT},
            new Object[]{"jQuery", "---", MIT},
            new Object[]{"FontAwesom", "---", OFLv1_1},
            new Object[]{"ASM", "Copyright (c) 2000-2011 INRIA, France Telecom", BSD_3_CLAUSE},
            new Object[]{"Open Sans", "---", APACHEv2_0},
            new Object[]{"Roboto", "---", APACHEv2_0},
            new Object[]{"Roboto Condensed", "---", APACHEv2_0},
            new Object[]{"Source Sans Pro", "---", OFLv1_1},
            new Object[]{"Lato", "---", OFLv1_1},
            new Object[]{"Lora", "---", OFLv1_1},
            new Object[]{"SCSS Blend Modes", "---", MIT},
            new Object[]{"Sass list functions", "---", MIT},
            new Object[]{"Vaadin Sass Compiler", "---", APACHEv2_0},
            new Object[]{"Bourbon", "---", MIT},
            new Object[]{"drag-drop-polyfill", "Copyright (c) 2013 Tim Ruffles", BSD_2_CLAUSE},
            new Object[]{"Jackson", "---", APACHEv2_0},
            new Object[]{"Simple-JNDI", "Copyright (c) 2003-2008, Henri Yandell + Robert Zigweid", BSD_3},
            new Object[]{"JCIP Annotations", "Copyright (C) Stephen Connolly, https://github.com/stephenc", APACHEv2_0},
            new Object[]{"Semver", "Copyright 2012-2014 Zafar Khaja <zafarkhaja@gmail.com>", MIT},
            new Object[]{"Guava", "---", APACHEv2_0},
            new Object[]{"Google Code Findbugs", "Copyright (c) 2007-2009, JSR305 expert group", BSD_3_CLAUSE},
            new Object[]{"Google Errorprone Annotations", "---", APACHEv2_0},
            new Object[]{"Google J2 Objc", "---", APACHEv2_0},
            new Object[]{"JCabi Manifests and Log", "Copyright (c) 2012-2017, jcabi.com", BSD_3_CLAUSE},
            new Object[]{"C3P0 Cache", "Copyright (C) 2015 Machinery For Change, Inc.", LGPLv2_1},
            new Object[]{"NimbusDS", "---", APACHEv2_0},
            new Object[]{"License3j", "---", LGPLv2_1},
            new Object[]{"Vaadin Messagebox", "---", APACHEv2_0},
            new Object[]{"JavaBeans Activation Framework", "---", CDDLv1_0},
            new Object[]{"Java Annotations API", "---", CDDLv1_0},
            new Object[]{"Expression Language API", "---", CDDLv1_0},
            new Object[]{"CDI APIs", "---", APACHEv2_0},
            new Object[]{"Java Inject API", "---", APACHEv2_0},
            new Object[]{"JavaMail API", "---", GPLv2},
            new Object[]{"JSR 354 Money And Currency API", "---", CDDLv1_0},
            new Object[]{"Java EE 8 Specification API", "---", CDDLv1_0},
            new Object[]{"ActiveMQ Artemis", "---", APACHEv2_0},
            new Object[]{"Bouncycastle", "Copyright (c) 2000 - 2017 The Legion of the Bouncy Castle Inc. (http://www.bouncycastle.org)", MIT},
            new Object[]{"Hibernate", "---", LGPLv2_1},
            new Object[]{"Jasypt", "---", APACHEv2_0},
            new Object[]{"Liquibase", "---", APACHEv2_0},
            new Object[]{"Lazy Luke log4jdbc", "Copyright 2007-2010 Arthur Blake", APACHEv2_0},
            new Object[]{"Vaadin CDI MVP and CDI-Properties", "---", APACHEv2_0},
            new Object[]{"W2C css sac", "Copyright © [YEAR] W3C® (MIT, ERCIM, Keio, Beihang)", W3C},
            new Object[]{"SnakeYAML", "Copyright (c) 2008, http://www.snakeyaml.org", APACHEv2_0},
    };

    @Produces
    @Libraries
    public Collection<SoftwareLibrary> libraries() {
        HashSet<SoftwareLibrary> result = new HashSet<>();

        for (Object[] library : libraries) {
            result.add(
                    new SoftwareLibraryBuilder()
                            .withLibraryName((String) library[0])
                            .withCopyrightNotice((String) library[1])
                            .withLicense((OpenSourceLicense) library[2])
                            .build()
            );
        }

        addManiacSpecialLicenses(result);

        LOG.trace("Generated license information about {} libraries.", result.size());

        return result;
    }

    private void addManiacSpecialLicenses(HashSet<SoftwareLibrary> library) {
        library.add(generateManiacDom4jLibraryEntry());
    }

    private SoftwareLibrary generateManiacDom4jLibraryEntry() {
        return new SoftwareLibraryBuilder()
                .withLibraryName("DOM4J")
                .withCopyrightNotice("Copyright 2001-2016 (C) MetaStuff, Ltd. and DOM4J contributors. All Rights Reserved.")
                .withLicense(new SoftwareLicense() {
                    @Override
                    public String getTitle() {
                        return "DOM4J special change to BSD licenses.";
                    }

                    @Override
                    public String getDisclaimer() {
                        return "THIS SOFTWARE IS PROVIDED BY METASTUFF, LTD. AND CONTRIBUTORS\n" +
                                "``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT\n" +
                                "NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND\n" +
                                "FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL\n" +
                                "METASTUFF, LTD. OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,\n" +
                                "INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES\n" +
                                "(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR\n" +
                                "SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)\n" +
                                "HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,\n" +
                                "STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)\n" +
                                "ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED\n" +
                                "OF THE POSSIBILITY OF SUCH DAMAGE.";
                    }

                    @Override
                    public String getFullText() {
                        return "Redistribution and use of this software and associated documentation\n" +
                                "(\"Software\"), with or without modification, are permitted provided\n" +
                                "that the following conditions are met:\n" +
                                "\n" +
                                "1. Redistributions of source code must retain copyright\n" +
                                "   statements and notices.  Redistributions must also contain a\n" +
                                "   copy of this document.\n" +
                                " \n" +
                                "2. Redistributions in binary form must reproduce the\n" +
                                "   above copyright notice, this list of conditions and the\n" +
                                "   following disclaimer in the documentation and/or other\n" +
                                "   materials provided with the distribution.\n" +
                                " \n" +
                                "3. The name \"DOM4J\" must not be used to endorse or promote\n" +
                                "   products derived from this Software without prior written\n" +
                                "   permission of MetaStuff, Ltd.  For written permission,\n" +
                                "   please contact dom4j-info@metastuff.com.\n" +
                                " \n" +
                                "4. Products derived from this Software may not be called \"DOM4J\"\n" +
                                "   nor may \"DOM4J\" appear in their names without prior written\n" +
                                "   permission of MetaStuff, Ltd. DOM4J is a registered\n" +
                                "   trademark of MetaStuff, Ltd.\n" +
                                " \n" +
                                "5. Due credit should be given to the DOM4J Project - https://dom4j.github.io/\n" +
                                " \n" +
                                "THIS SOFTWARE IS PROVIDED BY METASTUFF, LTD. AND CONTRIBUTORS\n" +
                                "``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT\n" +
                                "NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND\n" +
                                "FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL\n" +
                                "METASTUFF, LTD. OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,\n" +
                                "INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES\n" +
                                "(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR\n" +
                                "SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)\n" +
                                "HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,\n" +
                                "STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)\n" +
                                "ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED\n" +
                                "OF THE POSSIBILITY OF SUCH DAMAGE.";
                    }

                    @Override
                    public boolean isOpenSource() {
                        return true;
                    }

                    @Override
                    public String getKey() {
                        return "--- ./. ---";
                    }

                    @Override
                    public String getLicensee() {
                        return "--- ./. ---";
                    }

                    @Override
                    public ValidityDuration getDuration() {
                        return new ValidityDuration(OffsetDateTime.MIN, OffsetDateTime.MAX);
                    }

                    @Override
                    public VersionRange getVersions() {
                        return new VersionRange(Version.forIntegers(Integer.MIN_VALUE), Version.forIntegers(Integer.MAX_VALUE));
                    }
                })
                .build();
    }
}
