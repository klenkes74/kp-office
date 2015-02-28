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

package de.kaiserpfalzEdv.office.files.impl;

import de.kaiserpfalzEdv.office.commons.impl.KPOTenantHoldingEntity;
import de.kaiserpfalzEdv.office.contacts.contact.Contact;
import de.kaiserpfalzEdv.office.files.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 28.02.15 07:39
 */
public class FileImpl extends KPOTenantHoldingEntity implements File {
    private static final Logger LOG = LoggerFactory.getLogger(FileImpl.class);

    @Override
    public Set<Contact> getContacts(String type) {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzEdv.office.files.impl.FileImpl.getContacts
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
