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

package de.kaiserpfalzEdv.vaadin.ui.menu.impl;

import com.google.common.eventbus.EventBus;
import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.themes.ValoTheme;
import de.kaiserpfalzEdv.vaadin.auth.Authenticator;
import de.kaiserpfalzEdv.vaadin.event.NavigateToEvent;
import de.kaiserpfalzEdv.vaadin.i18n.I18NHandler;
import de.kaiserpfalzEdv.vaadin.ui.menu.Menu;
import de.kaiserpfalzEdv.vaadin.ui.menu.MenuEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Responsive navigation menu presenting a list of available views to the user.
 */
@Named
@UIScope
public class MenuImpl extends CssLayout implements Menu {
    private static final Logger LOG = LoggerFactory.getLogger(MenuImpl.class);

    private static final String VALO_MENUITEMS    = "valo-menuitems";
    private static final String VALO_MENU_TOGGLE  = "valo-menu-toggle";
    private static final String VALO_MENU_VISIBLE = "valo-menu-visible";

    private CssLayout menuItemsLayout;
    private CssLayout menuPart;

    private Authenticator accessControl;
    private EventBus      bus;
    private I18NHandler   i18n;

    private Map<Integer, Component> viewButtons = new HashMap<>();
    private List<View> allViews;

    @Inject
    public MenuImpl(
            final Authenticator accessControl,
            final EventBus bus,
            final I18NHandler i18n,
            final List<View> allViews
    ) {
        this.accessControl = accessControl;
        this.bus = bus;
        this.i18n = i18n;
        this.allViews = allViews;

        setPrimaryStyleName(ValoTheme.MENU_ROOT);
        menuPart = new CssLayout();
        menuPart.addStyleName(ValoTheme.MENU_PART);

        // header of the menu
        final HorizontalLayout top = new HorizontalLayout();
        top.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        top.addStyleName(ValoTheme.MENU_TITLE);
        top.setSpacing(true);
        Label title = new Label(translate("application.name"));
        title.addStyleName(ValoTheme.LABEL_H3);
        title.setSizeUndefined();
        Image image = new Image(null, new ThemeResource("img/table-logo.png"));
        image.setStyleName("logo");
        top.addComponent(image);
        top.addComponent(title);
        menuPart.addComponent(top);

        // logout menu item
        MenuBar logoutMenu = new MenuBar();
        logoutMenu.addItem(
                translate("button.logout.caption"), FontAwesome.valueOf(translate("button.logout.icon")), selectedItem -> {
                    VaadinSession.getCurrent().getSession().invalidate();
                    Page.getCurrent().reload();
                }
        );

        logoutMenu.addStyleName("user-menu");
        menuPart.addComponent(logoutMenu);

        // button for toggling the visibility of the menu when on a small screen
        final Button showMenu = new Button(
                translate("application.name"), new ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
                if (menuPart.getStyleName().contains(VALO_MENU_VISIBLE)) {
                    menuPart.removeStyleName(VALO_MENU_VISIBLE);
                } else {
                    menuPart.addStyleName(VALO_MENU_VISIBLE);
                }
            }
        }
        );
        showMenu.addStyleName(ValoTheme.BUTTON_PRIMARY);
        showMenu.addStyleName(ValoTheme.BUTTON_SMALL);
        showMenu.addStyleName(VALO_MENU_TOGGLE);
        showMenu.setIcon(FontAwesome.NAVICON);
        menuPart.addComponent(showMenu);

        // container for the navigation buttons, which are added by addView()
        menuItemsLayout = new CssLayout();
        menuItemsLayout.setPrimaryStyleName(VALO_MENUITEMS);
        menuPart.addComponent(menuItemsLayout);

        addComponent(menuPart);
    }

    @Override
    public void generate() {
        boolean lastEntryWasSeperator = false;

        for (View entry : allViews) {
            String beanName = getBeanName(entry);

            LOG.trace("Generating menu entry for bean '{}': {}", beanName, entry);
            MenuEntry menuDescriptor = entry.getClass().getAnnotation(MenuEntry.class);

            if (menuDescriptor == null) {
                LOG.error("No valid annotation for this menu: {}", beanName);
            } else {
                ((Component) entry).setCaption(translate(menuDescriptor.i18nKey() + ".menu-entry.caption"));
                ((Component) entry).setIcon(FontAwesome.valueOf(translate(menuDescriptor.i18nKey() + ".menu-entry.icon")));

                int order = menuDescriptor.order();

                if (menuDescriptor.separator() && !lastEntryWasSeperator) {
                    viewButtons.put(order, new Label("<hr/>", ContentMode.HTML));
                    order++;

                    lastEntryWasSeperator = true;
                }

                LOG.trace("Checking access to: {}", beanName);
                if (accessControl.isAccessGranted(getUI(), beanName)) {

                    createViewButton(menuDescriptor.name(), menuDescriptor.i18nKey() + ".menu-entry", order);

                    lastEntryWasSeperator = false;
                } else {
                    LOG.debug("BaseUser has no access to menu entry '{}'.", beanName);
                }
            }
        }


        ConcurrentSkipListSet<Integer> listEntryKeys = new ConcurrentSkipListSet<>();
        listEntryKeys.addAll(viewButtons.keySet());

        for (Integer index : listEntryKeys) {
            LOG.trace("Adding menu entry {}: {}", index, viewButtons.get(index));

            menuItemsLayout.addComponent(viewButtons.get(index));
        }
    }


    private String getBeanName(View entry) {
        String result = entry.getClass().getSimpleName();

        String[] cglibCutter = result.split("\\$\\$");
        LOG.trace("Cutting for CGLIB proxies '{}': {}", result, cglibCutter);

        if (cglibCutter.length > 1)
            result = cglibCutter[0];

        // get lowerCase of first character and then the normal name ...
        return result.substring(0, 1).toLowerCase() + result.substring(1);
    }


    private void createViewButton(final String name, String i18nKey, int index) {
        Resource icon = FontAwesome.valueOf(translate(i18nKey + ".icon"));
        Button button = new Button(
                translate(i18nKey + ".caption"),
                event -> {
                    LOG.trace("Menu click: {}", name);
                    bus.post(new NavigateToEvent(this, name));
                }
        );
        button.setDescription(translate(i18nKey + ".description"));
        button.setPrimaryStyleName(ValoTheme.MENU_ITEM);
        button.setIcon(icon);
        viewButtons.put(index, button);


        LOG.debug("Created menu entry: {}", i18nKey);
    }


    private String translate(final String key) {
        return i18n.get(key);
    }
}