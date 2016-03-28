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

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import de.kaiserpfalzEdv.vaadin.auth.Access;
import de.kaiserpfalzEdv.vaadin.backend.about.ModuleVersion;
import de.kaiserpfalzEdv.vaadin.backend.db.main.DatabaseChangeLog;
import de.kaiserpfalzEdv.vaadin.ui.PresentationHelper;
import de.kaiserpfalzEdv.vaadin.ui.menu.MenuEntry;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

import static com.vaadin.server.Sizeable.Unit.PERCENTAGE;
import static com.vaadin.server.Sizeable.Unit.PIXELS;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 10.09.15 07:33
 */
@Named
@UIScope
@Access({"user", "judge", "management", "office", "admin"})
@MenuEntry(
        name = AboutViewImpl.VIEW_NAME,
        i18nKey = AboutViewImpl.VIEW_NAME,
        access = {"user", "judge", "management", "office", "admin"},
        order = 98,
        separator = true
)
@SpringView(name = AboutViewImpl.VIEW_NAME)
public class AboutViewImpl extends CustomComponent implements AboutView, View {
    public static final String VIEW_NAME = "about";

    private AboutPresenter presenter;

    private VerticalLayout mainLayout, versionLayout, changelogLayout, licenseLayout;
    private TabSheet   tabSheet;
    private GridLayout overviewLayout;

    private String                  licenseText;
    private List<ModuleVersion>     versions;
    private List<DatabaseChangeLog> changeLog;

    @Inject
    public AboutViewImpl(
            final AboutPresenter presenter,
            @SuppressWarnings("UnusedParameters") final PresentationHelper presentationHelper // we need it somewhere.
    ) {
        this.presenter = presenter;
        this.presenter.setView(this);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        if (mainLayout == null) {
            mainLayout = createVerticalLayout(null, null, null);
            mainLayout.setMargin(true);
            mainLayout.setSpacing(true);
            mainLayout.setHeightUndefined();


            initalizeOverview();
            initializeModuleVersions();
            initializeDatabaseChangeLog();
            initializeLicense();

            tabSheet = new TabSheet(versionLayout, changelogLayout, licenseLayout);
            tabSheet.setSizeFull();
            mainLayout.addComponent(tabSheet);

            setCompositionRoot(mainLayout);
        }
    }

    private GridLayout createGridLayout(final int cols, final int rows) {
        GridLayout result = new GridLayout(cols, rows);

        result.setSizeFull();
        result.setSpacing(true);
        result.setResponsive(true);

        return result;
    }

    private VerticalLayout createVerticalLayout(final String caption, final String description, Resource icon) {
        VerticalLayout result = new VerticalLayout();

        result.setSizeFull();
        result.setSpacing(true);
        result.setResponsive(true);

        if (caption != null)
            result.setCaption(presenter.translate(caption));

        if (description != null)
            result.setDescription(presenter.translate(description));

        if (icon != null)
            result.setIcon(icon);

        return result;
    }

    private void initalizeOverview() {
        overviewLayout = createGridLayout(2, 3);
        overviewLayout.setHeight(300f, PIXELS);
        overviewLayout.setWidth(100f, PERCENTAGE);
        overviewLayout.setColumnExpandRatio(0, 70f);
        overviewLayout.setColumnExpandRatio(1, 30f);
        overviewLayout.setRowExpandRatio(0, 33f);
        overviewLayout.setRowExpandRatio(1, 33f);
        overviewLayout.setRowExpandRatio(2, 33f);

        Image logo = new Image("", new ThemeResource("img/logo.png"));
        logo.setHeight(300f, PIXELS);
        logo.setWidth(300f, PIXELS);
        overviewLayout.addComponent(logo, 0, 0, 0, 2);

        overviewLayout.addComponent(createOverviewLabel("about.application"), 1, 0);
        overviewLayout.addComponent(createOverviewLabel("about.version"), 1, 1);
        overviewLayout.addComponent(createManufactorLabel("about.manufactor"), 1, 2);

        mainLayout.addComponent(overviewLayout);
    }

    private Label createOverviewLabel(final String i18nBase) {
        String text = "<small>" + presenter.translate(i18nBase + ".caption") + "</small><br/>"
                + "<big>" + presenter.translate(i18nBase + ".data") + "</big>";

        return new Label(text, ContentMode.HTML);
    }

    private Label createManufactorLabel(final String i18nBase) {
        String text = "<small>" + presenter.translate(i18nBase + ".caption") + "</small><br/>"
                + "<big><a href=\"" + presenter.translate(i18nBase + ".url") + "\" target=\"top\">"
                + presenter.translate(i18nBase + ".data") + "</a></big>";

        return new Label(text, ContentMode.HTML);
    }

    private void initializeModuleVersions() {
        versionLayout = createVerticalLayout("about.modules.caption", "about.modules.description", FontAwesome.LIST);

        BeanItemContainer<ModuleVersion> data = new BeanItemContainer<>(ModuleVersion.class);
        data.addAll(versions);

        Grid display = new Grid(data);
        display.setSizeFull();
        display.setFrozenColumnCount(1);
        display.setColumns("caption", "description", "version");

        formatColumn(display, "caption", "about.modules", 25);
        formatColumn(display, "description", "about.modules", 50);
        formatColumn(display, "version", "about.modules", 25);
        versionLayout.addComponent(display);
    }

    private void initializeDatabaseChangeLog() {
        changelogLayout = createVerticalLayout("about.database.caption", "about.database.description", FontAwesome.TABLE);

        BeanItemContainer<DatabaseChangeLog> data = new BeanItemContainer<>(DatabaseChangeLog.class);
        data.addAll(changeLog);

        Grid display = new Grid(data);
        display.setSizeFull();
        display.setFrozenColumnCount(1);
        display.setColumns("id", "fileName", "dateExecuted", "comments", "liquibase");

        formatColumn(display, "id", "about.database", 10);
        formatColumn(display, "fileName", "about.database", 20);
        formatColumn(display, "dateExecuted", "about.database", 20);
        formatColumn(display, "comments", "about.database", 40);
        formatColumn(display, "liquibase", "about.database", 10);
        changelogLayout.addComponent(display);
    }

    private void formatColumn(Grid grid, final String column, final String i18nBase, final int expandRatio) {
        grid.getColumn(column).setHeaderCaption(presenter.translate(i18nBase + "." + column + ".caption"));
        grid.getColumn(column).setExpandRatio(expandRatio);
    }

    private void initializeLicense() {
        licenseLayout = createVerticalLayout("about.license.caption", "about.license.description", FontAwesome.FILE_TEXT);

        Label text = new Label(licenseText, ContentMode.HTML);
        text.setWidth(100f, PERCENTAGE);

        Panel panel = new Panel(text);
        panel.setWidth(100f, PERCENTAGE);
        panel.setHeight(300f, PIXELS);

        licenseLayout.addComponent(panel);
    }


    @Override
    public void setLicense(String licenseText) {
        this.licenseText = licenseText;
    }

    @Override
    public void setModuleVersions(List<ModuleVersion> versions) {
        this.versions = versions;
    }

    @Override
    public void setDatabaseChangeLog(List<DatabaseChangeLog> changeLog) {
        this.changeLog = changeLog;
    }
}
