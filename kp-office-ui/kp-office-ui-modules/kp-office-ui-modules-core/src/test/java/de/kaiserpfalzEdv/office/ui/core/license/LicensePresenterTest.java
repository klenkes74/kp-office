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

package de.kaiserpfalzEdv.office.ui.core.license;

import de.kaiserpfalzEdv.commons.jee.eventbus.EventBusHandler;
import de.kaiserpfalzEdv.commons.test.MockingTestBase;
import de.kaiserpfalzEdv.office.clients.core.LicenceClient;
import de.kaiserpfalzEdv.office.commons.SoftwareVersion;
import de.kaiserpfalzEdv.office.commons.SoftwareVersionRange;
import de.kaiserpfalzEdv.office.core.licence.OfficeLicence;
import de.kaiserpfalzEdv.office.core.licence.impl.LicenceImpl;
import org.jmock.Expectations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.UUID;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 30.07.15 01:53
 */
@Test
public class LicensePresenterTest extends MockingTestBase {
    private static final Logger LOG = LoggerFactory.getLogger(LicensePresenterTest.class);

    private static final OfficeLicence LICENSE = new LicenceImpl(UUID.randomUUID(), LocalDate.MIN, "Kaiserpfalz EDV-Service", "Unit Tests", LocalDate.MIN, LocalDate.MAX, "KP Office", new SoftwareVersionRange(new SoftwareVersion("0.0.0"), new SoftwareVersion("999.999.999")), new String[]{});

    private LicensePresenter service;

    private EventBusHandler bus;
    private LicenceClient   client;
    private LicenseView     view;


    public LicensePresenterTest() {
        super(LicensePresenterTest.class, LOG);
    }

    @BeforeMethod
    protected void setUpService() {
        bus = mockery.mock(EventBusHandler.class, "bus");
        client = mockery.mock(LicenceClient.class, "client");
        view = mockery.mock(LicenseView.class, "view");

        mockery.checking(
                new Expectations() {{
                    atLeast(1).of(bus).register(with(any(LicensePresenter.class)));
                }}
        );

        service = new LicensePresenter(bus, client);
    }


    public void checkLicenseBeforeView() {
        logMethod("license-before-view", "Checking setting the license client before setting the view ...");

        mockery.checking(
                new Expectations() {{
                    oneOf(client).getLicence();
                    will(returnValue(LICENSE));

                    oneOf(view).setLicense(with(LICENSE));
                }}
        );

        service.setView(view);
    }


    public void checkLicenseClientChange() {
        logMethod("license-client-change", "Checking changing the license client ...");

        LicenceClient newClient = mockery.mock(LicenceClient.class, "newLicenseClient");

        mockery.checking(
                new Expectations() {{
                    oneOf(client).getLicence();
                    will(returnValue(LICENSE));

                    oneOf(newClient).getLicence();
                    will(returnValue(LICENSE));

                    atLeast(2).of(view).setLicense(with(LICENSE));
                }}
        );

        service.setView(view);
        service.setLicenseClient(newClient);
    }


    public void checkUnregisterBus() {
        logMethod("unregister-bus", "Checking unregistering from the event bus ...");

        mockery.checking(
                new Expectations() {{
                    atLeast(1).of(bus).unregister(with(service));
                }}
        );

        service.close();
    }
}
