/*
 * Copyright (c) 2014 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzEdv.office.security;

import de.kaiserpfalzEdv.commons.correlation.CorrelationId;
import de.kaiserpfalzEdv.commons.correlation.RequestCorrelated;
import de.kaiserpfalzEdv.commons.correlation.RequestCorrelation;
import de.kaiserpfalzEdv.commons.correlation.RequestId;
import de.kaiserpfalzEdv.commons.correlation.ResponseCorrelated;
import de.kaiserpfalzEdv.office.tenant.Tenant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * @author klenkes
 * @since 2014Q
 */
@Secured("scm")
public class SecurityCheckManager {
    private static final Logger LOG = LoggerFactory.getLogger(SecurityCheckManager.class);

    private String info = "info";


    public String getInfo() {
        return info;
    }

    public void setInfo(final String info) {
        this.info = info;
    }

    public void setInfo(@TenantMarker final Tenant tenant, final String info) {
        this.info = info;
    }

    @RequestCorrelated("scm")
    public void setInfo(@TenantMarker final Tenant tenant, @CorrelationId final RequestCorrelation request, final String info) {
        this.info = info;
    }

    @RequestCorrelated("scm")
    public void setInfo(@TenantMarker final Tenant tenant, @RequestId final UUID request, final String info) {
        this.info = info;
    }

    @RequestCorrelated("scm")
    public void setInfo(@CorrelationId final UUID correlationId, @RequestId final UUID requestId, final String info) {
        this.info = info;
    }

    @ResponseCorrelated("scm")
    public void setInfo(@TenantMarker final Tenant tenant, @CorrelationId final ResponseCorrelated response, final String info) {
        this.info = info;
    }
}
