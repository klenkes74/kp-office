/*
 * Copyright 2016 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzEdv.vaadin.about;

import com.google.common.collect.Lists;
import de.kaiserpfalzEdv.vaadin.backend.about.ModuleVersion;
import de.kaiserpfalzEdv.vaadin.i18n.I18NHandler;
import org.apache.commons.lang3.builder.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 12.09.15 18:47
 */
public class ModuleVersionListBuilder implements Builder<List<ModuleVersion>> {
    private static final Logger LOG = LoggerFactory.getLogger(ModuleVersionListBuilder.class);

    private static final String            I18N_PREFIX             = "about.version.";
    private static final String            I18N_CAPTION_SUFFIX     = ".caption";
    private static final String            I18N_DESCRIPTION_SUFFIX = ".description";
    private static final String            I18N_VERSION_SUFFIX     = ".version";
    private final        ArrayList<String> modules                 = new ArrayList<>();
    private I18NHandler i18n;

    public ModuleVersionListBuilder(I18NHandler i18n) {
        this.i18n = i18n;
    }

    @Override
    public List<ModuleVersion> build() {
        ArrayList<ModuleVersion> result = new ArrayList<>(modules.size());

        for (String m : modules) {
            result.add(buildVersion(m));
        }

        return result;
    }

    public void clear() {
        modules.clear();
    }

    private ModuleVersion buildVersion(final String module) {
        String modulePrefix = I18N_PREFIX + module;

        String caption = i18n.get(modulePrefix + I18N_CAPTION_SUFFIX);
        String description = i18n.get(modulePrefix + I18N_DESCRIPTION_SUFFIX);
        String version = i18n.get(modulePrefix + I18N_VERSION_SUFFIX);

        if (description.equals(modulePrefix + I18N_DESCRIPTION_SUFFIX)) {
            description = "";
        }

        return new ModuleVersion(caption, description, version);
    }

    public ModuleVersionListBuilder withModule(final String module) {
        modules.add(module);
        return this;
    }

    public ModuleVersionListBuilder withModules(final String... modules) {
        return withModules(Lists.newArrayList(modules));
    }

    public ModuleVersionListBuilder withModules(final Collection<String> modules) {
        this.modules.addAll(modules);
        return this;
    }
}
