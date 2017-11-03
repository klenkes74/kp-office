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

package de.kaiserpfalzedv.commons.impl.license;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 1.0.0
 */
public enum OpenSourceLicense {
    APACHEv2_0("Apache License Version 2.0, January 2004", true),
    BSD_3("BSD v3.0", true),
    BSD_3_CLAUSE("The BSD 3-Clause License", true),
    GPLv2("GNU GENERAL PUBLIC LICENSE Version 2, June 1991", true),
    LGPLv2_1("GNU LESSER GENERAL PUBLIC LICENSE Version 2.1, February 1999", true),
    MIT("The MIT License", true),
    BSD_2_CLAUSE("BSD 2 Clause", true),
    OFLv1_1("SIL Open Font License Version 1.1 - 26 February 2007", true),
    CDDLv1_0("Common Development and Distribution License 1.0", true),
    W3C("License", true);

    private String title;
    private boolean openSource;

    OpenSourceLicense(final String title, final boolean isOpenSource) {
        this.title = title;
        this.openSource = isOpenSource;
    }


    public String getTitle() {
        return title;
    }

    public boolean isOpenSource() {
        return openSource;
    }
}
