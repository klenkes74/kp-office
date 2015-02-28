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

package de.kaiserpfalzEdv.office.core.license.impl;

import de.kaiserpfalzEdv.office.core.license.FeatureNotLicensedException;
import de.kaiserpfalzEdv.office.core.license.LicenseService;
import de.kaiserpfalzEdv.office.core.license.ModuleInformation;
import de.kaiserpfalzEdv.office.core.license.OfficeLicense;
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
 * <p>Aspect to check the license of {@link de.kaiserpfalzEdv.office.core.license.ModuleInformation} annotated classes or
 * methods.</p>
 * * 
 * <p>The licensing interceptor intercepts calls to</p>
 * <ol>
 *     <li>classes annotated with {@link de.kaiserpfalzEdv.office.core.license.ModuleInformation}.</li>
 *     <li>methods annotated with {@link de.kaiserpfalzEdv.office.core.license.ModuleInformation}.</li>
 * </ol>
 * 
 * <p>as long as they are in or on classes with their name ending on <code>...ModuleImpl</code>. That restriction is
 * for making the aspect easier to assign at runtime. Otherwise every single method call would have to be checkt on the
 * pointcuts.</p>
 * 
 * <p>The real license check is delegated to the {@link de.kaiserpfalzEdv.office.core.license.LicenseService}.</p>
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
    private LicenseService licenseService;

    public LicenseManager() {
        LOG.trace("Created: {}", this);

    }

    public LicenseManager(LicenseService licenseService) {
        this.licenseService = licenseService;

        LOG.trace("Created: {}", this);
    }


    @PostConstruct
    public void init() {
        LOG.trace("Licensing Interceptor uses: {}", licenseService);
    }


    @PreDestroy
    public void close() {
        LOG.trace("Destroyed: {}", this);
    }


    @Pointcut("@within(de.kaiserpfalzEdv.office.core.license.ModuleInformation)")
    public void classAnnotatedWithOfficeModule() {}

    @Pointcut("@annotation(de.kaiserpfalzEdv.office.core.license.ModuleInformation)")
    public void methodAnnotatedWithOfficeModule() {}
    
    @Pointcut("execution(* de.kaiserpfalzEdv.office..*(..))")
    public void officeModuleImplementation() {}


    @Before("classAnnotatedWithOfficeModule() && officeModuleImplementation()")
    public void checkLicenseOfClass(final JoinPoint invocation) throws FeatureNotLicensedException {
        LOG.trace("Checking licensing for {} ...", invocation.getTarget().getClass().getName());

        ModuleInformation module = getOfficeModuleAnnotationFromClass(invocation);
        checkLicenseOfModule(module);
    }

    private ModuleInformation getOfficeModuleAnnotationFromClass(final JoinPoint invocation) {
        return invocation.getTarget().getClass().getAnnotation(ModuleInformation.class);
    }


    @Before("methodAnnotatedWithOfficeModule() && officeModuleImplementation()")
    public void checkLicenseOfMethod(final JoinPoint invocation) throws FeatureNotLicensedException {
        LOG.trace("Checking licensing for {} ...", invocation.getSignature().getName());

        ModuleInformation module = getOfficeModuleFromMethodAnnotation(invocation);
        checkLicenseOfModule(module);
    }

    private ModuleInformation getOfficeModuleFromMethodAnnotation(final JoinPoint invocation) {
        MethodSignature signature = (MethodSignature) invocation.getSignature();
        return signature.getMethod().getAnnotation(ModuleInformation.class);
    }


    /**
     * Checks the license of the module by checking if the module needs a license and then checking it against the
     * {@link de.kaiserpfalzEdv.office.core.license.OfficeLicense} provided by {@link #licenseService}.
     *
     * @param module The OfficeModule annoatation to check the data for. 
     * @throws FeatureNotLicensedException If there is a problem with the licensing of the module.
     */
    private void checkLicenseOfModule(final ModuleInformation module) throws FeatureNotLicensedException {
        if (module.needsLicence()) {
            OfficeLicense license = licenseService.getLicense();
            
            if (! license.containsFeature(module.featureName())) {
                LOG.warn("Feature '{}' not licensed in {}", module.featureName(), license);
                
                throw new FeatureNotLicensedException(license, module.featureName());
            }

            LOG.trace("Checked license for: {} (#{})", module.name(), module.id());
        } else {
            LOG.trace("No license needed for module: {} (#{})", module.name(), module.id());
        }
    }
}
