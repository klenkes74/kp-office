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

package de.kaiserpfalzedv.office.geodata.impl;

import java.io.Serializable;
import java.util.Properties;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import de.kaiserpfalzedv.office.common.data.Pageable;
import de.kaiserpfalzedv.office.common.data.PagedListable;
import de.kaiserpfalzedv.office.common.data.impl.PageableBuilder;
import de.kaiserpfalzedv.office.common.init.InitializationException;
import de.kaiserpfalzedv.office.geodata.api.City;
import de.kaiserpfalzedv.office.geodata.api.CityService;
import de.kaiserpfalzedv.office.geodata.api.Country;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-08-08
 */
@Stateless
@Local
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class CityServiceBean implements CityService, Serializable {
    private static final long serialVersionUID = -8716761228420348131L;


    @Inject
    private CityRepository repository;


    @Override
    public void init() throws InitializationException {
    }

    @Override
    public void init(Properties properties) throws InitializationException {
    }

    @Override
    public void close() {
    }


    @Override
    public PagedListable<? extends Country> retrieveCountries() {
        return repository.retrieveCountries();
    }


    @Override
    public PagedListable<? extends City> retrieve(final Country country, final String postalCode, final int pageSize) {
        return retrieve(country, postalCode, new PageableBuilder().withSize(pageSize).withPage(0).build());
    }

    @Override
    public PagedListable<? extends City> retrieve(final Country country, final String postalCode, final Pageable page) {
        return repository.retrieve(country, postalCode, page);
    }


    @Override
    public PagedListable<? extends City> retrieve(final Country country, int pageSize) {
        return retrieve(country, new PageableBuilder().withSize(pageSize).withPage(0).build());
    }

    @Override
    public PagedListable<? extends City> retrieve(final Country country, final Pageable page) {
        return repository.retrieve(country, page);
    }


    @Override
    public PagedListable<? extends City> retrieve(final String cityName, final int pageSize) {
        return retrieve(cityName, new PageableBuilder().withSize(pageSize).withPage(0).build());
    }

    @Override
    public PagedListable<? extends City> retrieve(final String cityName, final Pageable page) {
        return repository.retrieve(cityName, page);
    }
}
