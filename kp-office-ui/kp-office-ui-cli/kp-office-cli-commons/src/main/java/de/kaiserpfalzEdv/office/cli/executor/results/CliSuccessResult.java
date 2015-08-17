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

package de.kaiserpfalzEdv.office.cli.executor.results;

import de.kaiserpfalzEdv.office.cli.CliModuleInfo;
import de.kaiserpfalzEdv.office.cli.executor.impl.CliResultCode;

import java.util.UUID;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 02.03.15 23:39
 */
public class CliSuccessResult extends CliResult {
    private static final long serialVersionUID = 4671491562734669447L;


    public CliSuccessResult(CliModuleInfo moduleInfo, CliResultCode key) {
        super(moduleInfo, key);

    }

    public CliSuccessResult(UUID id, CliModuleInfo moduleInfo, CliResultCode key, String displayNumber, String displayName) {
        super(id, moduleInfo, key, displayNumber, displayName);
    }

    public CliSuccessResult(CliModuleInfo moduleInfo, CliResultCode key, String displayNumber, String displayName) {
        super(UUID.randomUUID(), moduleInfo, key, displayNumber, displayName);
    }


    @Override
    public final boolean isFailure() {
        return false;
    }

    @Override
    public final boolean isSuccess() {
        return true;
    }
}
