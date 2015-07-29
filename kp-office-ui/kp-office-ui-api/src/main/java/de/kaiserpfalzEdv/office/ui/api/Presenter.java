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

package de.kaiserpfalzEdv.office.ui.api;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;
import java.io.Serializable;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 12.07.15 06:10
 */
public class Presenter<V extends View> implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(Presenter.class);


    /**
     * The view this presenter is created for.
     */
    private V view;


    public Presenter() {
        LOG.trace("***** Created: {}", this);
    }

    @PreDestroy
    public void close() {
        LOG.trace("***** Destroyed: {}", this);
    }


    public V getView() {
        return view;
    }

    public void setView(final V view) {
        LOG.trace("Updating view: {} -> {}", this.view, view);

        this.view = view;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Presenter)) return false;

        Presenter<?> presenter = (Presenter<?>) o;

        return new EqualsBuilder()
                .append(getView(), presenter.getView())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getView())
                .toHashCode();
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
                .append("view", view)
                .toString();
    }
}
