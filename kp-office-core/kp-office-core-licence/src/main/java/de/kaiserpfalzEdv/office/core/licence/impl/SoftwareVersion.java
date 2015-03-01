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

import de.kaiserpfalzEdv.commons.service.VersionRange;
import de.kaiserpfalzEdv.commons.service.Versionable;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>A version number as described in <a href="http://semver.org/spec/v2.0.0.html">Semantic Versioning</a>. The valid 
 * version specifiers are:</p>
 * 
 * <ol>
 *     <li>alpha</li>     
 *     <li>beta</li>
 *     <li>releaseCandidate</li>
 *     <li>release (synonymous to no specifier at al)</li>     
 * </ol>
 *  
 * <p>That leaves us with versions like 23.1.0-beta.</p>
 *
 * @author klenkes
 * @version 2015Q1
 * @since 11.02.15 20:03
 */
public class SoftwareVersion implements Versionable, Serializable {
    /** The parts of the version scheme. Default is a three part version number. */
    public static final int VERSION_SIZE = 3;
    public static final String VERSION_REGEX = "\\d+(.\\d+){0," + (VERSION_SIZE-1) + "}(-(alpha|beta|releaseCandidate|release))?";
    private static final long serialVersionUID = -6778399647727817889L;
    private Integer[] version = new Integer[VERSION_SIZE];
    private ReleaseState state = ReleaseState.release;
    
    public SoftwareVersion(@NotNull final String versionString) {
        parse(versionString);
    }
    
    public SoftwareVersion(final int... version) {
        for (int i=0; i < version.length && i < this.version.length; i++) {
            this.version[i] = version[i];
        }
    }
    
    public SoftwareVersion(final ReleaseState state, final int... version) {
        this(version);
        
        this.state = state;
    }
    
    private void parse(@NotNull final String versionString) {
        String[] state = versionString.split("-");
        if (state.length == 2) {
            this.state = ReleaseState.valueOf(state[1]);
        }
        
        String[] version = state[0].split("\\.");
        if (version.length > VERSION_SIZE || version.length == 0) {
            throw new IllegalArgumentException("Version string in wrong format [" + VERSION_REGEX + "]: " + versionString);
        }
        
        for (int i=0; i < version.length && i < this.version.length; i++) {
            this.version[i] = Integer.parseUnsignedInt(version[i], 10);
        }
    }
    
    @Override
    public String getBuildDescriptor() {
        StringBuilder result = new StringBuilder(getVersionString());

        if (state != null) {
            result.append("-").append(state);
        }
        
        return result.toString();
    }

    @Override
    public String getVersionString() {
        StringBuilder result = new StringBuilder()
                .append(version[0]);

        for (int i=1; i < version.length; i++) {
            if (version[i] != null) 
                result.append(".").append(version[i]);
        }

        return result.toString();
    }

    @Override
    public int[] getVersion() {
        int[] result = new int[VERSION_SIZE];
        
        for (int i=0; i < version.length && i < result.length; i++) {
            result[i] = version[i] != null ? version[i] : 0;
        }

        return result;
    }

    @Override
    public boolean beforeOrEqual(Versionable other) {
        int [] otherVersion = other.getVersion();
        for (int i=0; i < version.length && i < otherVersion.length ; i++) {
            if (version[i] < otherVersion[i]) {
                return true;
            } else if (version[i] > otherVersion[i]) {
                return false;
            }
        }
        
        if (state.ordinal() < other.getReleaseState().ordinal()) {
            return true;
        } else if (state.ordinal() > other.getReleaseState().ordinal()) {
            return false;
        }
        
        return true;
    }

    @Override
    public boolean between(VersionRange range) {
        return afterOrEqual(range.getStart()) && beforeOrEqual(range.getEnd());
    }

    @Override
    public boolean afterOrEqual(Versionable other) {
        return other.beforeOrEqual(this);
    }

    @Override
    public ReleaseState getReleaseState() {
        return state;
    }
    
    
    @Override
    public String toString() {
        return getVersionString();
    }
}
