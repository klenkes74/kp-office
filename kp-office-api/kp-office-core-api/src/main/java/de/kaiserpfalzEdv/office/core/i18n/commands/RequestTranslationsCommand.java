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

package de.kaiserpfalzEdv.office.core.i18n.commands;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import de.kaiserpfalzEdv.office.commons.commands.Command;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 01.03.15 18:50
 */
@JsonAutoDetect(fieldVisibility = ANY)
public class RequestTranslationsCommand implements Command {
    private static final long serialVersionUID = 1144693910132922664L;
}