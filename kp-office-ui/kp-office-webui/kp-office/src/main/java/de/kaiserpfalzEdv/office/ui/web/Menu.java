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

package de.kaiserpfalzEdv.office.ui.web;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.themes.ValoTheme;
import de.kaiserpfalzEdv.office.ui.web.api.menu.MenuEntry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Responsive navigation menu presenting a list of available views to the user.
 */
public class Menu extends CssLayout {

    private static final String              VALO_MENUITEMS    = "valo-menuitems";
    private static final String              VALO_MENU_TOGGLE  = "valo-menu-toggle";
    private static final String              VALO_MENU_VISIBLE = "valo-menu-visible";
    private              Map<String, Button> viewButtons       = new HashMap<String, Button>();

    private MenuBar menu;
    private CssLayout menuItemsLayout;
    private CssLayout menuPart;

    public Menu() {
        setPrimaryStyleName(ValoTheme.MENU_ROOT);
        menuPart = new CssLayout();
        menuPart.addStyleName(ValoTheme.MENU_PART);

        // header of the menu
        final HorizontalLayout top = new HorizontalLayout();
        top.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        top.addStyleName(ValoTheme.MENU_TITLE);
        top.setSpacing(true);
        Label title = new Label("KP Office");
        title.addStyleName(ValoTheme.LABEL_H3);
        title.setSizeUndefined();
        Image image = new Image(null, new ThemeResource("img/table-logo.png"));
        image.setStyleName("logo");
        top.addComponent(image);
        top.addComponent(title);
        menuPart.addComponent(top);


        // button for toggling the visibility of the menu when on a small screen
        final Button showMenu = new Button(
                "Menu", new ClickListener() {
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

        // logout menu item
        MenuBar logoutMenu = new MenuBar();
        logoutMenu.addItem(
                "Logout", FontAwesome.SIGN_OUT, new Command() {

                    @Override
                    public void menuSelected(MenuItem selectedItem) {
                        VaadinSession.getCurrent().getSession().invalidate();
                        Page.getCurrent().reload();
                    }
                }
        );

        logoutMenu.addStyleName("user-menu");
        menuPart.addComponent(logoutMenu);

        addComponent(menuPart);
    }

    /**
     * Register a pre-created view instance in the navigation menu and in the
     * {@link Navigator}.
     *
     * @param view    view instance to register
     * @param name    view name
     * @param caption view caption in the menu
     * @param icon    view icon in the menu
     *
     * @see Navigator#addView(String, View)
     */
    public void addView(
            View view, final String name, String caption,
            Resource icon
    ) {
        createViewButton(name, caption, icon);
    }

    /**
     * Register a view in the navigation menu and in the {@link Navigator} based
     * on a view class.
     *
     * @param viewClass class of the views to create
     * @param name      view name
     * @param caption   view caption in the menu
     * @param icon      view icon in the menu
     *
     * @see Navigator#addView(String, Class)
     */
    public void addView(
            Class<? extends View> viewClass, final String name,
            String caption, Resource icon
    ) {
        createViewButton(name, caption, icon);
    }


    /**
     * Adds all entries listed in the list menuEntries to the menu and the {@link Navigator}
     * based on a view class.
     *
     * @param menuEntries all entries of the menu. Will be sorted by {@link MenuEntry#getSortOrder()}.
     */
    public void addEntries(final List<MenuEntry> menuEntries) {
        menuEntries.sort((o1, o2) -> o1.getSortOrder() - o2.getSortOrder());

        for (MenuEntry entry : menuEntries) {
            addView(entry, entry.getViewName(), entry.getCaption(), entry.getIcon());
        }
    }


    private void createViewButton(
            final String name, String caption,
            Resource icon
    ) {

        Button button = new Button(
                caption, new ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                getUI().getNavigator().navigateTo(name);
            }
        }
        );
        button.setPrimaryStyleName(ValoTheme.MENU_ITEM);
        button.setIcon(icon);
        menuItemsLayout.addComponent(button);
        viewButtons.put(name, button);
    }

    /**
     * Highlights a view navigation button as the currently active view in the
     * menu. This method does not perform the actual navigation.
     *
     * @param viewName the name of the view to show as active
     */
    public void setActiveView(String viewName) {
        for (Button button : viewButtons.values()) {
            button.removeStyleName("selected");
        }
        Button selected = viewButtons.get(viewName);
        if (selected != null) {
            selected.addStyleName("selected");
        }
        menuPart.removeStyleName(VALO_MENU_VISIBLE);
    }
}
