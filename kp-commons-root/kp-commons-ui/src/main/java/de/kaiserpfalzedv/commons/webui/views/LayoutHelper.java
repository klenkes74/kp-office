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

package de.kaiserpfalzedv.commons.webui.views;

import java.io.Serializable;

import com.vaadin.server.Resource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TextField;
import de.kaiserpfalzedv.commons.webui.i18n.I18NHandler;

import static com.google.common.base.Preconditions.checkArgument;
import static com.vaadin.server.Sizeable.Unit.PERCENTAGE;


/**
 * @author rlic
 * @version 1.0.0
 * @since 01.09.15 08:39
 */
@SuppressWarnings("unused")
public class LayoutHelper<L extends Layout> implements Serializable {
    @SuppressWarnings("unused")
    public static final int ROW_01 = 0;
    @SuppressWarnings("unused")
    public static final int ROW_02 = 1;
    @SuppressWarnings("unused")
    public static final int ROW_03 = 2;
    @SuppressWarnings("unused")
    public static final int ROW_04 = 3;
    @SuppressWarnings("unused")
    public static final int ROW_05 = 4;
    @SuppressWarnings("unused")
    public static final int ROW_06 = 5;
    @SuppressWarnings("unused")
    public static final int ROW_07 = 6;
    @SuppressWarnings("unused")
    public static final int ROW_08 = 7;
    @SuppressWarnings("unused")
    public static final int ROW_09 = 8;
    @SuppressWarnings("unused")
    public static final int ROW_10 = 9;
    @SuppressWarnings("unused")
    public static final int COL_01 = 0;
    @SuppressWarnings("unused")
    public static final int COL_02 = 1;
    @SuppressWarnings("unused")
    public static final int COL_03 = 2;
    @SuppressWarnings("unused")
    public static final int COL_04 = 3;
    @SuppressWarnings("unused")
    public static final int COL_05 = 4;
    @SuppressWarnings("unused")
    public static final int COL_06 = 5;
    @SuppressWarnings("unused")
    public static final int COL_07 = 6;
    @SuppressWarnings("unused")
    public static final int COL_08 = 7;
    @SuppressWarnings("unused")
    public static final int COL_09 = 8;
    @SuppressWarnings("unused")
    public static final int COL_10 = 9;
    private static final long serialVersionUID = 1027995557177150809L;
    private I18NHandler i18n;
    private L layout;

    private int maxCols, maxRows;

    @SuppressWarnings("unused")
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


    @SuppressWarnings("unused")
    public Button createButton(final String caption, Resource icon, final int tabIndex, final int col, final int row) {
        Button result = new Button(i18n.get(caption), icon);
        addToLayout(result, tabIndex, col, row);
        return result;
    }

    @SuppressWarnings("WeakerAccess")
    public void addToLayout(Component.Focusable result, int tabIndex, int col, int row) {
        addToLayout(result, tabIndex, col, row, col, row);
    }

    @SuppressWarnings("WeakerAccess")
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

    @SuppressWarnings("unused")
    public TextField createTextField(final String caption, final int tabIndex, final int col, final int row) {
        return createTextField(caption, tabIndex, col, row, col, row);
    }

    @SuppressWarnings("WeakerAccess")
    public TextField createTextField(
            final String caption, final int tabIndex,
            final int startColumn, final int startRow,
            final int endColumn, final int endRow
    ) {
        TextField result = new TextField(i18n.get(caption));
        addToLayout(result, tabIndex, startColumn, startRow, endColumn, endRow);
        return result;
    }

    @SuppressWarnings("unused")
    public DateField createDateField(final String caption, final int tabIndex, final int col, final int row) {
        return createDateField(caption, tabIndex, col, row, col, row);
    }

    @SuppressWarnings("WeakerAccess")
    public DateField createDateField(
            final String caption, final int tabIndex,
            final int startColumn, final int startRow,
            final int endColumn, final int endRow
    ) {
        DateField result = new DateField(i18n.get(caption));
        addToLayout(result, tabIndex, startColumn, startRow, endColumn, endRow);
        return result;
    }
}
