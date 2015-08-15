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

package de.kaiserpfalzEdv.office.accounting.postingRecord.impl;

import de.kaiserpfalzEdv.office.accounting.DatabaseMoney;
import de.kaiserpfalzEdv.office.accounting.postingRecord.DocumentInformation;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.money.MonetaryAmount;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.time.LocalDate;

/**
 * The document information for the base document of a posting record. Contains information about the document (invoice)
 * the posting record is about.
 *
 * @author klenkes
 * @version 2015Q1
 * @since 28.02.15 13:08
 */
@Embeddable
public class DocumentInformationImpl implements DocumentInformation {

    @Column(name = "document_number1_")
    private String number1;

    @Column(name = "document_number2_")
    private String number2;

    @Column(name = "document_date_", nullable = false)
    private LocalDate date;

    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "document_amount_value_")),
            @AttributeOverride(name = "currency", column = @Column(name = "document_amount_currency_"))
    })
    @Embedded
    private DatabaseMoney amount;


    @Deprecated // only for Jackson, JAX-B, JPA, ...
    protected DocumentInformationImpl() {}


    public DocumentInformationImpl(final String number1, final String number2, final LocalDate date, final MonetaryAmount amount) {
        this.number1 = number1;
        this.number2 = number2;
        this.date = date;
        this.amount = new DatabaseMoney(amount);
    }


    public String getDocumentNumber1() {
        return number1;
    }

    public String getDocumentNumber2() { return number2; }

    public LocalDate getDocumentDate() {
        return date;
    }

    public MonetaryAmount getDocumentAmount() {
        return amount.getMoney();
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
        DocumentInformation rhs = (DocumentInformation) obj;
        return new EqualsBuilder()
                .append(this.number1, rhs.getDocumentNumber1())
                .append(this.number2, rhs.getDocumentNumber2())
                .append(this.date, rhs.getDocumentDate())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(number1)
                .append(number2)
                .append(date)
                .toHashCode();
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("number1", number1)
                .append("number2", number2)
                .append("date", date)
                .append("amount", amount)
                .toString();
    }
}
