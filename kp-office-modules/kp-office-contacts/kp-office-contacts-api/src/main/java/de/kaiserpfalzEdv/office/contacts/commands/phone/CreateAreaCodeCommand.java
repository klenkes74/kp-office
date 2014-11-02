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

package de.kaiserpfalzEdv.office.contacts.commands.phone;

import de.kaiserpfalzEdv.office.contacts.address.phone.AreaCode;
import de.kaiserpfalzEdv.office.contacts.address.phone.AreaCodeDTO;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public class CreateAreaCodeCommand extends AreaCodeBaseCommand {
    private static final long serialVersionUID = 1L;


    private AreaCodeDTO areaCode;


    @SuppressWarnings("deprecation")
    @Deprecated // Only for Jackson, JAX-B and JPA!
    public CreateAreaCodeCommand() {
    }

    @SuppressWarnings("deprecation")
    public CreateAreaCodeCommand(@NotNull final AreaCode areaCode) {
        setAreaCode(areaCode);
    }

    public AreaCodeDTO getAreaCode() {
        return areaCode;
    }

    @SuppressWarnings("deprecation")
    @Deprecated // Only for Jackson JAX-B and JPA!
    public void setAreaCode(@NotNull final AreaCode areaCode) {
        setAreaCode(new AreaCodeDTO(areaCode));
    }

    @Deprecated // Only for Jackson JAX-B and JPA!
    public void setAreaCode(@NotNull final AreaCodeDTO areaCode) {
        this.areaCode = areaCode;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append(areaCode)
                .toString();
    }
}
