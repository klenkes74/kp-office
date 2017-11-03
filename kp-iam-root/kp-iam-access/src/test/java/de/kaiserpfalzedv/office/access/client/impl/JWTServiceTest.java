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

package de.kaiserpfalzedv.office.access.client.impl;

import com.nimbusds.jose.JWSAlgorithm;
import de.kaiserpfalzedv.office.access.client.JWTService;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.validation.constraints.NotNull;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-08-25
 */
public class JWTServiceTest {
    private static final Logger LOG = LoggerFactory.getLogger(JWTServiceTest.class);

    private static final String PREPARED_JWT =
            "eyJhbGciOiJSUzI1NiIsImtpZCI6InMxIn0.eyJzY3AiOlsib3BlbmlkIiwiZW1haWwiLCJwcm9maWxl" +
                    "Il0sImV4cCI6MTQ2MDM0NTczNiwic3ViIjoiYWxpY2UiLCJpc3MiOiJodHRwczpcL1wvZGVtby5jMmlk" +
                    "LmNvbVwvYzJpZCIsInVpcCI6eyJncm91cHMiOlsiYWRtaW4iLCJhdWRpdCJdfSwiY2xtIjpbIiE1djhI" +
                    "Il0sImNpZCI6IjAwMDEyMyJ9.Xeg3cMrePht8R0731mfndUDoX48NWhfCuEjcEERcZ3krfnOacNJzyJd" +
                    "7zOWdNrlvEpJMjmmgkbhZOMJlVMv4fQnGB2d3eevmtjuT7hMnJVQc_4h80ODHPMlW27T0Iukpe7Y-A-R" +
                    "rROP5yinry7BFBL2nVWrNtB9IS11H9C8X5fQ";
    private static final String PREPARED_PROVIDER = "https://demo.c2id.com/c2id/jwks.json";
    private static final JWSAlgorithm PREPARED_PROVIDER_ALGORITHM = JWSAlgorithm.RS256;


    private JWTService sut;

    @BeforeClass
    public static void setUpMDC() {
        MDC.put("test-class", "jwt-converter");
        MDC.put("test", "preparation");
    }

    @AfterClass
    public static void tearDownMDC() {
        MDC.remove("test");
        MDC.remove("test-class");
    }

    @Test
    public void checkPreparedJWT() {
        prepareTest("prepared-jwt", "Checking prepared JWT: {}", PREPARED_JWT);

        SSLCertificateValidation.disable();

        sut.readToken(PREPARED_JWT, PREPARED_PROVIDER, PREPARED_PROVIDER_ALGORITHM);
    }

    private void prepareTest(@NotNull final String testName, @NotNull final String logging, Object... params) {
        MDC.put("test", testName);
        LOG.debug(logging, params);
    }

    @Before
    public void setUp() {
        sut = new JWTServiceImpl();
    }

    @After
    public void tearDown() {
        MDC.remove("test");
    }
}
