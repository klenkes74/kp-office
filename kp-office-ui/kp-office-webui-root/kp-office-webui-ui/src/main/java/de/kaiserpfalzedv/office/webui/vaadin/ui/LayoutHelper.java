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

package de.kaiserpfalzedv.office.webui.vaadin.ui;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.addon.jpacontainer.fieldfactory.SingleSelectConverter;
import com.vaadin.server.Resource;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import de.kaiserpfalzEdv.vaadin.i18n.I18NHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import java.io.Serializable;

import static com.google.gwt.thirdparty.guava.common.base.Preconditions.checkArgument;
import static com.vaadin.server.Sizeable.Unit.PERCENTAGE;

/**
 * @author rlic
 * @version 1.0.0
 * @since 01.09.15 08:39
 */
public class LayoutHelper<L extends Layout> implements Serializable {
    public static final  int    ROW_01 = 0;
    public static final  int    ROW_02 = 1;
    public static final  int    ROW_03 = 2;
    public static final  int    ROW_04 = 3;
    public static final  int    ROW_05 = 4;
    public static final  int    ROW_06 = 5;
    public static final  int    ROW_07 = 6;
    public static final  int    ROW_08 = 7;
    public static final  int    ROW_09 = 8;
    public static final  int    ROW_10 = 9;
    public static final  int    COL_01 = 0;
    public static final  int    COL_02 = 1;
    public static final  int    COL_03 = 2;
    public static final  int    COL_04 = 3;
    public static final  int    COL_05 = 4;
    public static final  int    COL_06 = 5;
    public static final  int    COL_07 = 6;
    public static final  int    COL_08 = 7;
    public static final  int    COL_09 = 8;
    public static final  int    COL_10 = 9;
    private static final Logger LOG    = LoggerFactory.getLogger(LayoutHelper.class);
    private I18NHandler i18n;
    private L           layout;

    private int maxCols, maxRows;

    public LayoutHelper(
            final I18NHandler ui,
            final L layout,
            final int maxCols,
            final int maxRows
    ) {
        this.i18n = ui;
        this.layout = layout;

        this.maxCols = maxCols;
        this.maxRows = maxRows;
    }


    public Button createButton(final String caption, Resource icon, final int tabIndex, final int col, final int row) {
        Button result = new Button(i18n.get(caption), icon);
        addToLayout(result, tabIndex, col, row);
        return result;
    }

    public TextField createTextField(final String caption, final int tabIndex, final int col, final int row) {
        return createTextField(caption, tabIndex, col, row, col, row);
    }

    public TextField createTextField(
            final String caption, final int tabIndex,
            final int startColumn, final int startRow,
            final int endColumn, final int endRow
    ) {
        TextField result = new TextField(i18n.get(caption));
        addToLayout(result, tabIndex, startColumn, startRow, endColumn, endRow);
        return result;
    }

    public void addToLayout(Component.Focusable result, int tabIndex, int col, int row) {
        addToLayout(result, tabIndex, col, row, col, row);
    }

    public void addToLayout(Component.Focusable result, int tabIndex, int startColumn, int startRow, int endColumn, int endRow) {
        checkArgument(startColumn < maxCols, "Grid does not match! start column %s bigger than max column %s", startColumn, maxCols);
        checkArgument(endColumn < maxCols, "Grid does not match! end column %s bigger than max column %s", endColumn, maxCols);

        checkArgument(startRow < maxRows, "Grid does not match! start row %s bigger than max row %s", startRow, maxRows);
        checkArgument(endRow < maxRows, "Grid does not match! end row %s bigger than max row %s", endRow, maxRows);

        result.setWidth(100f, PERCENTAGE);
        result.setTabIndex(tabIndex);

        if (GridLayout.class.isAssignableFrom(layout.getClass())) {
            ((GridLayout) layout).addComponent(result, startColumn, startRow, endColumn, endRow);
        } else {
            layout.addComponent(result);
        }
    }

    public DateField createDateField(final String caption, final int tabIndex, final int col, final int row) {
        return createDateField(caption, tabIndex, col, row, col, row);
    }

    public DateField createDateField(
            final String caption, final int tabIndex,
            final int startColumn, final int startRow,
            final int endColumn, final int endRow
    ) {
        DateField result = new DateField(i18n.get(caption));
        addToLayout(result, tabIndex, startColumn, startRow, endColumn, endRow);
        return result;
    }


    public <T> ComboBox createJpaComboBox(
            final EntityManager em, final Class<?> clasz, final String displayColumn,
            final String caption, final int tabIndex,
            final int startColumn, final int startRow, final int endColumn, final int endRow
    ) {
        JPAContainer<T> data = createJpaContainer(em, clasz, displayColumn);

        ComboBox result = new ComboBox(i18n.get(caption), data);
        result.setInputPrompt(i18n.get(caption + ".prompt"));
        result.setScrollToSelectedItem(true);
        result.setItemCaptionPropertyId(displayColumn);
        result.setTextInputAllowed(true);
        result.setConverter(new SingleSelectConverter<Long>(result));

        addToLayout(result, tabIndex, startColumn, startRow, endColumn, endRow);
        return result;
    }

    /**
     * Creates a {@link JPAContainer} for the select boxes.
     *
     * @param em            The entity manager to base the select on.
     * @param clasz         The class to create the JPAContainer for.
     * @param displayColumn The column name to be displayed.
     * @param <T>           Only needed internally :-)
     *
     * @return The container to use in comboboxes and so on ...
     */
    private <T> JPAContainer<T> createJpaContainer(EntityManager em, Class<?> clasz, String displayColumn) {
        @SuppressWarnings("unchecked")
        JPAContainer<T> data = (JPAContainer<T>) JPAContainerFactory.make(clasz, em);

        data.sort(new String[]{displayColumn}, new boolean[]{true});

        return data;
    }


    public <T> TwinColSelect createJpaTwinColSelect(
            final EntityManager em, final Class<?> clasz, final String displayColumn,
            final String caption, final String captionLeft, final String captionRight, final int tabIndex,
            final int startColumn, final int startRow, final int endColumn, final int endRow
    ) {
        JPAContainer<T> data = createJpaContainer(em, clasz, displayColumn);

        TwinColSelect result = new TwinColSelect(i18n.get(caption), data);
        result.setLeftColumnCaption(i18n.get(captionLeft));
        result.setRightColumnCaption(i18n.get(captionRight));
        result.setMultiSelect(true);
        result.setRows(2);
        result.setItemCaptionPropertyId(displayColumn);
        result.setConverter(new SingleSelectConverter<T>(result));

        addToLayout(result, tabIndex, startColumn, startRow, endColumn, endRow);
        return result;
    }
}
