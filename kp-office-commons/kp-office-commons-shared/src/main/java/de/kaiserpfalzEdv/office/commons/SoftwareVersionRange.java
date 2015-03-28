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

package de.kaiserpfalzEdv.office.commons;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import de.kaiserpfalzEdv.commons.service.VersionRange;
import de.kaiserpfalzEdv.commons.service.Versionable;

import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

/**
 * A range of semantic versions.
 * 
 * @author klenkes
 * @version 2015Q1
 * @since 15.02.15 07:32
 */
@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class SoftwareVersionRange implements VersionRange, Serializable {

    private SoftwareVersion start;
    private SoftwareVersion end;


    @Deprecated
    public SoftwareVersionRange() {}
    
    
    public SoftwareVersionRange(final Versionable start, final Versionable end) {
        this.start = new SoftwareVersion(start.getVersionString());
        this.end = new SoftwareVersion(end.getVersionString());
    }

    
    @Override
    public Versionable getStart() {
        return start;
    }

    @Override
    public Versionable getEnd() {
        return end;
    }

    @Override
    public boolean overlapsWith(VersionRange range) {
        return this.start.between(range) || this.end.between(range);   
    }
}
