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

package de.kaiserpfalzEdv.office.cli.executor.impl;

import de.kaiserpfalzEdv.commons.service.Versionable;
import de.kaiserpfalzEdv.office.cli.CliModule;
import de.kaiserpfalzEdv.office.cli.executor.CliModuleRegistrar;
import de.kaiserpfalzEdv.office.cli.executor.results.CliNothingToDoResult;
import de.kaiserpfalzEdv.office.cli.executor.results.CliResult;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 03.03.15 08:14
 */
public class NullExecutor implements CliModuleRegistrar {
    @Override
    public CliResult init() {
        return new CliNothingToDoResult(new NullModule());
    }

    @Override
    public CliResult close() {
        return new CliNothingToDoResult(new NullModule());
    }
}

class NullModule implements CliModule {
    @Override
    public Versionable getVersion() {
        return new Versionable.Builder().withMajor(0).withMinor(0).build();
    }

    @Override
    public String getShortDescription() {
        return "A realy lazy module.";
    }

    @Override
    public String getDescription() {
        return "This is a module that does nothing at all.";
    }

    @Override
    public boolean isInitialized() {
        return true;
    }

    @Override
    public String getDisplayName() {
        return NullModule.class.getSimpleName();
    }
}
