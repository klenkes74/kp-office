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

package de.kaiserpfalzEdv.commons;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * This exception is thrown if a validation error is thrown. The reasons may be red from the set returned by
 * {@link #getReasons()}.
 *
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public class BuilderValidationException extends RuntimeException {
    private static final long serialVersionUID = -4704563513014900337L;

    private final HashSet<String> reasons = new HashSet<>();

    public BuilderValidationException(@NotNull final String message, @NotNull final Collection<String> reasons) {
        super(message);

        this.reasons.addAll(reasons);
    }


    public Set<String> getReasons() {
        return Collections.unmodifiableSet(reasons);
    }
}
