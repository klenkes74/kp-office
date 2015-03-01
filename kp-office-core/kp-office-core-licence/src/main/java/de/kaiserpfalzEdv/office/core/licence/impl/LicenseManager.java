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

package de.kaiserpfalzEdv.office.core.licence.impl;

import de.kaiserpfalzEdv.office.commons.ModuleInformation;
import de.kaiserpfalzEdv.office.core.licence.FeatureNotLicencedException;
import de.kaiserpfalzEdv.office.core.licence.LicenceService;
import de.kaiserpfalzEdv.office.core.licence.OfficeLicence;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

/**
 * <p>Aspect to check the licence of {@link de.kaiserpfalzEdv.office.core.licence.ModuleInformation} annotated classes or
 * methods.</p>
 * * 
 * <p>The licensing interceptor intercepts calls to</p>
 * <ol>
 *     <li>classes annotated with {@link de.kaiserpfalzEdv.office.core.licence.ModuleInformation}.</li>
 *     <li>methods annotated with {@link de.kaiserpfalzEdv.office.core.licence.ModuleInformation}.</li>
 * </ol>
 * 
 * <p>as long as they are in or on classes with their name ending on <code>...ModuleImpl</code>. That restriction is
 * for making the aspect easier to assign at runtime. Otherwise every single method call would have to be checkt on the
 * pointcuts.</p>
 *
 * <p>The real licence check is delegated to the {@link de.kaiserpfalzEdv.office.core.licence.LicenceService}.</p>
 * 
 * @author klenkes
 * @version 2015Q1
 * @since 11.02.15 21:19
 */
@Aspect
@Component
public class LicenseManager {
    private static final Logger LOG = LoggerFactory.getLogger(LicenseManager.class);


    @Inject
    private LicenceService licenceService;

    public LicenseManager() {
        LOG.trace("Created: {}", this);

    }

    public LicenseManager(LicenceService licenceService) {
        this.licenceService = licenceService;

        LOG.trace("Created: {}", this);
    }


    @PostConstruct
    public void init() {
        LOG.trace("Licensing Interceptor uses: {}", licenceService);
    }


    @PreDestroy
    public void close() {
        LOG.trace("Destroyed: {}", this);
    }


    @Pointcut("@within(de.kaiserpfalzEdv.office.commons.ModuleInformation)")
    public void classAnnotatedWithOfficeModule() {}

    @Pointcut("@annotation(de.kaiserpfalzEdv.office.commons.ModuleInformation)")
    public void methodAnnotatedWithOfficeModule() {}

    @Pointcut("execution(* de.kaiserpfalzEdv.office..*(..))")
    public void officeModuleImplementation() {}


    @Before("classAnnotatedWithOfficeModule() && officeModuleImplementation()")
    public void checkLicenseOfClass(final JoinPoint invocation) throws FeatureNotLicencedException {
        LOG.trace("Checking licensing for {} ...", invocation.getTarget().getClass().getName());

        ModuleInformation module = getOfficeModuleAnnotationFromClass(invocation);
        checkLicenseOfModule(module);
    }

    private ModuleInformation getOfficeModuleAnnotationFromClass(final JoinPoint invocation) {
        return invocation.getTarget().getClass().getAnnotation(ModuleInformation.class);
    }


    @Before("methodAnnotatedWithOfficeModule() && officeModuleImplementation()")
    public void checkLicenseOfMethod(final JoinPoint invocation) throws FeatureNotLicencedException {
        LOG.trace("Checking licensing for {} ...", invocation.getSignature().getName());

        ModuleInformation module = getOfficeModuleFromMethodAnnotation(invocation);
        checkLicenseOfModule(module);
    }

    private ModuleInformation getOfficeModuleFromMethodAnnotation(final JoinPoint invocation) {
        MethodSignature signature = (MethodSignature) invocation.getSignature();
        return signature.getMethod().getAnnotation(ModuleInformation.class);
    }


    /**
     * Checks the licence of the module by checking if the module needs a licence and then checking it against the
     * {@link de.kaiserpfalzEdv.office.core.licence.OfficeLicence} provided by {@link #licenceService}.
     *
     * @param module The OfficeModule annoatation to check the data for. 
     * @throws de.kaiserpfalzEdv.office.core.licence.FeatureNotLicencedException If there is a problem with the licensing of the module.
     */
    private void checkLicenseOfModule(final ModuleInformation module) throws FeatureNotLicencedException {
        if (module.needsLicence()) {
            OfficeLicence license = licenceService.getLicence();
            
            if (! license.containsFeature(module.featureName())) {
                LOG.warn("Feature '{}' not licensed in {}", module.featureName(), license);

                throw new FeatureNotLicencedException(license, module.featureName());
            }

            LOG.trace("Checked licence for: {} (#{})", module.name(), module.id());
        } else {
            LOG.trace("No licence needed for module: {} (#{})", module.name(), module.id());
        }
    }
}
