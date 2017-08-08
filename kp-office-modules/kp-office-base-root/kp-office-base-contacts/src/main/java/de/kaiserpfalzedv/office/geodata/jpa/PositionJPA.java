/*
 * Copyright 2017 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzedv.office.geodata.jpa;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;

import de.kaiserpfalzedv.office.geodata.api.Position;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-08-08
 */
@Embeddable
public class PositionJPA implements Position {
    @Transient
    private Double longitude;

    @Transient
    private Double latitude;

    @Column(name = "ACCURACY_")
    private int accuracy;

    @Column(name = "LONGITUDE_", length = 8)
    private String longitudeData;

    @Column(name = "LATITUDE_", length = 8)
    private String latitudeData;

    @Deprecated
    public PositionJPA() {}

    public PositionJPA(final Double longitude, final Double latitude, final int accuracy) {
        this.accuracy = accuracy;

        this.longitude = longitude;
        this.latitude = latitude;
        convertPosition();
        convertPositionData();
    }

    @PrePersist
    @PreUpdate
    @PreRemove
    protected void convertPosition() {
        this.longitudeData = longitude.toString().substring(0, Integer.min(longitude.toString().length(), 8));
        this.latitudeData = latitude.toString().substring(0, Integer.min(latitude.toString().length(), 8));
    }

    @PostLoad
    protected void convertPositionData() {
        this.longitude = Double.parseDouble(longitudeData);
        this.latitude = Double.parseDouble(latitudeData);
    }

    @Override
    public Double getLongitude() {
        return longitude;
    }

    @Override
    public Double getLatitude() {
        return latitude;
    }

    @Override
    public int getAccuracy() {
        return accuracy;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("longitude", longitude)
                .append("latitude", latitude)
                .append("accuracy", accuracy)
                .toString();
    }
}
