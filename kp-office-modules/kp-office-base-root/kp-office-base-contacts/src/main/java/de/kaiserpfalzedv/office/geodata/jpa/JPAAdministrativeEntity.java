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

package de.kaiserpfalzedv.office.geodata.jpa;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import de.kaiserpfalzedv.office.geodata.api.AdministrativeEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-08-08
 */
@Embeddable
public class JPAAdministrativeEntity implements AdministrativeEntity {
    @Column(name = "ADMIN_CODE_", length = 20)
    private String code;

    @Column(name = "ADMIN_NAME", length = 100)
    private String name;


    @Deprecated
    public JPAAdministrativeEntity() {}

    public JPAAdministrativeEntity(final String code, final String name) {
        this.code = code;
        this.name = name;
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getCode() {
        return code;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("code", code)
                .append("name", name)
                .toString();
    }
}
