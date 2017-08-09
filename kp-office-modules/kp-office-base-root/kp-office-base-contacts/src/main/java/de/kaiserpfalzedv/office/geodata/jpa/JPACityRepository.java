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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.kaiserpfalzedv.office.common.data.Pageable;
import de.kaiserpfalzedv.office.common.data.PagedListable;
import de.kaiserpfalzedv.office.common.data.impl.PageableBuilder;
import de.kaiserpfalzedv.office.common.data.impl.PagedListBuilder;
import de.kaiserpfalzedv.office.geodata.api.City;
import de.kaiserpfalzedv.office.geodata.api.Country;
import de.kaiserpfalzedv.office.geodata.impl.CityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-08-09
 */
public class JPACityRepository implements CityRepository, Serializable {
    private static final long serialVersionUID = -3045622214158270742L;

    private static final Logger LOG = LoggerFactory.getLogger(CityRepository.class);


    private static final ArrayList<Country> COUNTRIES = new ArrayList<>(10);

    static {
        Collections.addAll(COUNTRIES, Country.DE, Country.AT, Country.CH, Country.LI, Country.LU, Country.BE, Country.FR);
    }


    @PersistenceContext(unitName = "CONTACTS")
    private EntityManager em;


    @Override
    public PagedListable<? extends Country> retrieveCountries() {
        return new PagedListBuilder<Country>().withData(COUNTRIES).build();
    }

    @Override
    public PagedListable<? extends City> retrieve(final Country country, final String postalCode, final Pageable page) {
        long totalCount = page.getTotalCount();

        List<CityJPA> data = em.createNamedQuery("City.ByPostalCode", CityJPA.class)
                               .setParameter("country", country)
                               .setParameter("code", postalCode)
                               .setFirstResult(page.getFirstResult())
                               .setMaxResults(page.getMaxResults())
                               .getResultList();

        Pageable resultPage;
        if (totalCount == 0) {
            LOG.debug("Need to count the total results for postal code search: country={}, postalCode={}",
                      country, postalCode
            );

            totalCount = em.createNamedQuery("City.ByPostalCode.count", Long.class)
                           .setParameter("country", country)
                           .setParameter("code", postalCode)
                           .getSingleResult();

            resultPage = new PageableBuilder()
                    .withPaging(page)
                    .withTotalCount(totalCount)
                    .withTotalPages(0)
                    .build();
        } else {
            resultPage = new PageableBuilder().withPaging(page).build();
        }

        return new PagedListBuilder<CityJPA>().withData(data).withPageable(resultPage).build();
    }


    @Override
    public PagedListable<? extends City> retrieve(final Country country, final Pageable page) {
        long totalCount = page.getTotalCount();

        List<CityJPA> data = em.createNamedQuery("City.ByCountry", CityJPA.class)
                               .setParameter("country", country)
                               .setFirstResult(page.getFirstResult())
                               .setMaxResults(page.getMaxResults())
                               .getResultList();

        Pageable resultPage;
        if (totalCount == 0) {
            LOG.debug("Need to count the total results for country search: country={}", country);

            totalCount = em.createNamedQuery("City.ByCountry.count", Long.class)
                           .setParameter("country", country)
                           .getSingleResult();

            resultPage = new PageableBuilder()
                    .withPaging(page)
                    .withTotalCount(totalCount)
                    .withTotalPages(0)
                    .build();
        } else {
            resultPage = new PageableBuilder().withPaging(page).build();
        }

        return new PagedListBuilder<CityJPA>().withData(data).withPageable(resultPage).build();
    }

    @Override
    public PagedListable<? extends City> retrieve(final String cityName, final Pageable page) {
        long totalCount = page.getTotalCount();

        List<CityJPA> data = em.createNamedQuery("City.ByCityNme", CityJPA.class)
                               .setParameter("cityName", cityName)
                               .setFirstResult(page.getFirstResult())
                               .setMaxResults(page.getMaxResults())
                               .getResultList();

        Pageable resultPage;
        if (totalCount == 0) {
            LOG.debug("Need to count the total results for city search: city name={}", cityName);

            totalCount = em.createNamedQuery("City.ByCityNme.count", Long.class)
                           .setParameter("cityName", cityName)
                           .getSingleResult();

            resultPage = new PageableBuilder()
                    .withPaging(page)
                    .withTotalCount(totalCount)
                    .withTotalPages(0)
                    .build();
        } else {
            resultPage = new PageableBuilder().withPaging(page).build();
        }

        return new PagedListBuilder<CityJPA>().withData(data).withPageable(resultPage).build();
    }
}
