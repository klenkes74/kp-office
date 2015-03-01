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

package de.kaiserpfalzEdv.office.core.i18n.notifications;

import de.kaiserpfalzEdv.office.commons.notifications.Notification;
import de.kaiserpfalzEdv.office.core.i18n.TranslationEntry;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 01.03.15 18:53
 */
public class TranslationsNotification implements Notification {
    private final HashSet<TranslationEntry> entries = new HashSet<>();

    public TranslationsNotification(@NotNull final Collection<? extends TranslationEntry> translations) {
        entries.addAll(translations);
    }

    public Set<TranslationEntry> getTranslations() {
        return Collections.unmodifiableSet(entries);
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        TranslationsNotification rhs = (TranslationsNotification) obj;
        return new EqualsBuilder()
                .append(this.entries, rhs.entries)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(entries)
                .toHashCode();
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("count", entries.size())
                .toString();
    }
}