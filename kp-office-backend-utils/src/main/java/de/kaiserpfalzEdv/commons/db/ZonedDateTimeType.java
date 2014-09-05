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

package de.kaiserpfalzEdv.commons.db;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.Mapping;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.metamodel.relational.Size;
import org.hibernate.metamodel.spi.TypeContributions;
import org.hibernate.metamodel.spi.TypeContributor;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.AbstractStandardBasicType;
import org.hibernate.type.ForeignKeyDirection;
import org.hibernate.type.TimestampType;
import org.hibernate.type.Type;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;
import org.hibernate.type.descriptor.java.MutabilityPlan;
import org.hibernate.type.descriptor.sql.TimestampTypeDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

/**
 * A mapper for the new {@link java.time.ZonedDateTime} for which no current mapper for hibernate exists.
 * The ZonedDateTime will be mapped into a {@link java.sql.Timestamp}. Zone information gets lost in this conversation.
 *
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public class ZonedDateTimeType extends AbstractStandardBasicType<ZonedDateTime> implements TypeContributor {
    private static final Logger LOG = LoggerFactory.getLogger(ZonedDateTimeType.class);

    private static final String BASE_CLASS = Timestamp.class.getName();

    public ZonedDateTimeType() {
        super(new TimestampTypeDescriptor(), new ZonedDateTimeJavaDescriptor());
    }


    @Override
    public Size[] dictatedSizes(Mapping mapping) throws MappingException {
        return getBaseMapping(mapping).dictatedSizes(mapping);
    }

    @Override
    public Size[] defaultSizes(Mapping mapping) throws MappingException {
        return getBaseMapping(mapping).defaultSizes(mapping);
    }


    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, boolean[] settable, SessionImplementor session) throws HibernateException, SQLException {
        if (value == null) {
            TimestampType.INSTANCE.set(st, null, index, session);
        } else {
            final ZonedDateTime zdt = (ZonedDateTime) value;
            TimestampType.INSTANCE.set(st, Timestamp.from(zdt.toInstant()), index, session);
        }
    }


    @Override
    public String getName() {
        return "dateTime";
    }


    @Override
    public Object replace(Object original, Object target, SessionImplementor session, Object owner, Map copyCache, ForeignKeyDirection foreignKeyDirection) throws HibernateException {
        return original;
    }

    @Override
    public boolean[] toColumnNullness(Object value, Mapping mapping) {
        return new boolean[] {
                value == null
        };
    }

    @Override
    public String[] getRegistrationKeys() {
        return new String[] {
                ZonedDateTime.class.getName(),
                ZonedDateTime.class.getSimpleName()
        };
    }


    private Type getBaseMapping(Mapping mapping) {
        return mapping.getIdentifierType(BASE_CLASS);
    }


    @Override
    public void contribute(TypeContributions typeContributions, ServiceRegistry serviceRegistry) {
        LOG.debug("***** Register ZonedDateTimeType as type mapper to: {}", typeContributions);

        typeContributions.contributeType(this);
    }
}

/**
 * The description of the java part of the type mapping ...
 */
class ZonedDateTimeJavaDescriptor extends AbstractTypeDescriptor<ZonedDateTime> {
    private static final Logger LOG = LoggerFactory.getLogger(ZonedDateTimeJavaDescriptor.class);


    protected ZonedDateTimeJavaDescriptor() {
        super(ZonedDateTime.class, new MutabilityPlan<ZonedDateTime>() {
            @Override
            public boolean isMutable() {
                return false;
            }

            @Override
            public ZonedDateTime deepCopy(ZonedDateTime value) {
                return ZonedDateTime.from(value);
            }

            @Override
            public Serializable disassemble(ZonedDateTime value) {
                return value;
            }

            @Override
            public ZonedDateTime assemble(Serializable cached) {
                return (ZonedDateTime) cached;
            }
        });
    }

    @Override
    public String toString(ZonedDateTime value) {
        return value.toString();
    }

    @Override
    public ZonedDateTime fromString(String string) {
        return ZonedDateTime.parse(string);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <X> X unwrap(ZonedDateTime value, Class<X> type, WrapperOptions options) {
        if (value == null) {
            return null;
        }
        if (ZonedDateTime.class.isAssignableFrom(type)) {
            return (X) value;
        }
        if ( java.sql.Date.class.isAssignableFrom( type ) ) {
            return (X) new java.sql.Date(value.toLocalDate().atStartOfDay().toInstant(value.getOffset()).getEpochSecond());
        }
        if ( java.sql.Time.class.isAssignableFrom( type ) ) {
            return (X) new java.sql.Time(value.toLocalTime().toSecondOfDay());
        }
        if ( java.sql.Timestamp.class.isAssignableFrom( type ) ) {
            return (X) new Timestamp(value.toInstant().getEpochSecond());
        }
        if ( Date.class.isAssignableFrom( type ) ) {
            return (X) Date.from(value.toInstant());
        }
        if ( Calendar.class.isAssignableFrom( type ) ) {
            final GregorianCalendar cal = new GregorianCalendar();
            cal.setTimeInMillis( value.toInstant().getEpochSecond() );
            return (X) cal;
        }
        if ( Long.class.isAssignableFrom( type ) ) {
            return (X) Long.valueOf( value.toInstant().getEpochSecond() );
        }
        throw unknownUnwrap( type );
    }

    private static final ZoneId UTC = ZoneId.of("UTC");

    @Override
    public <X> ZonedDateTime wrap(X value, WrapperOptions options) {
        LOG.trace("Converting to ZonedDateTime: {} (Type: {})", value, value.getClass().getCanonicalName());

        if (value == null) {
            return null;
        }
        if (ZonedDateTime.class.isInstance(value)) {
            return (ZonedDateTime) value;
        }
        if ( Date.class.isInstance( value ) ) {
            return ZonedDateTime.of(LocalDateTime.ofInstant(((Date)value).toInstant(), UTC), UTC);
        }

        if ( Long.class.isInstance( value ) ) {
            return ZonedDateTime.ofInstant(new java.sql.Date((Long) value).toInstant(), UTC);
        }

        if ( Calendar.class.isInstance( value ) ) {
            return ZonedDateTime.ofInstant(new java.sql.Date(((Calendar) value).getTimeInMillis()).toInstant(), UTC);
        }

        throw unknownWrap( value.getClass() );
    }
}