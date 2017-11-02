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

package de.kaiserpfalzedv.commons.webui.menu;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;

import com.vaadin.cdi.CDIView;
import com.vaadin.cdi.UIScoped;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewProvider;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.themes.ValoTheme;
import de.kaiserpfalzedv.commons.webui.events.PresentationHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Responsive navigation menu presenting a list of available views to the user.
 */
@UIScoped
public class MenuImpl extends CssLayout implements Menu {
    private static final Logger LOG = LoggerFactory.getLogger(MenuImpl.class);

    private static final String VALO_MENUITEMS = "valo-menuitems";
    private static final String VALO_MENU_TOGGLE = "valo-menu-toggle";
    private static final String VALO_MENU_VISIBLE = "valo-menu-visible";

    private static final Annotation QUALIFIER_ANY = new AnnotationLiteral<Any>() {
    };


    private CssLayout menuItemsLayout;
    private CssLayout menuPart;

    private BeanManager beanManager;
    private ViewProvider viewProvider;
    private PresentationHelper helper;

    private Map<Integer, Component> viewButtons = new HashMap<>();

    @Inject
    public MenuImpl(
            final PresentationHelper helper,
            final ViewProvider viewProvider,
            final BeanManager beanManager
    ) {
        this.helper = helper;
        this.beanManager = beanManager;
        this.viewProvider = viewProvider;

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
                translate("button.logout.caption"),
                FontAwesome.valueOf(translate("button.logout.icon")),
                selectedItem -> {
                    VaadinSession.getCurrent().getSession().invalidate();
                    Page.getCurrent().reload();
                }
        );

        logoutMenu.addStyleName("user-menu");
        menuPart.addComponent(logoutMenu);

        // button for toggling the visibility of the menu when on a small screen
        final Button showMenu = new Button(
                translate("application.name"), (Button.ClickListener) event -> {
            if (menuPart.getStyleName().contains(VALO_MENU_VISIBLE)) {
                menuPart.removeStyleName(VALO_MENU_VISIBLE);
            } else {
                menuPart.addStyleName(VALO_MENU_VISIBLE);
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

    private String translate(final String key) {
        return helper.translate(key);
    }

    @Override
    public void generate() {
        for (View entry : getViewsInMenu()) {
            LOG.trace("Generating menu entry for bean: {}", entry);
            MenuEntry menuDescriptor = entry.getClass().getAnnotation(MenuEntry.class);

            String caption = (!"".equals(menuDescriptor.captionKey())) ?
                    translate(menuDescriptor.captionKey()) : menuDescriptor.caption();
            String description = (!"".equals(menuDescriptor.descriptionKey())) ?
                    translate(menuDescriptor.descriptionKey()) : menuDescriptor.description();

            ((Component) entry).setCaption(caption);

            FontAwesome icon = (!"".equals(menuDescriptor.iconKey())) ?
                    FontAwesome.valueOf(translate(menuDescriptor.iconKey())) : null;

            int order = menuDescriptor.order();

            if (menuDescriptor.separator()) {
                viewButtons.put(order, new Label("<hr/>", ContentMode.HTML));
                order++;
            }

            createViewButton(menuDescriptor.name(), caption, description, icon, order);
        }


        ConcurrentSkipListSet<Integer> listEntryKeys = new ConcurrentSkipListSet<>();
        listEntryKeys.addAll(viewButtons.keySet());

        for (Integer index : listEntryKeys) {
            LOG.trace("Adding menu entry {}: {}", index, viewButtons.get(index));

            menuItemsLayout.addComponent(viewButtons.get(index));
        }
    }

    private Set<View> getViewsInMenu() {
        LOG.debug("Retrieveing all views with menu entries from CDI.");

        HashSet<View> result = new HashSet<>();

        Set<Bean<?>> all = beanManager.getBeans(View.class, QUALIFIER_ANY);
        if (all.isEmpty()) {
            LOG.error("No views found! Please add at least one single view to your application!");
        } else {
            for (Bean<?> bean : all) {
                Class<?> beanClass = bean.getBeanClass();

                MenuEntry menuAnnotation = beanClass.getAnnotation(MenuEntry.class);
                CDIView viewAnnotation = beanClass.getAnnotation(CDIView.class);
                if (menuAnnotation == null || viewAnnotation == null) {
                    continue;
                }
                View view = viewProvider.getView(viewAnnotation.value());
                result.add(view);
            }
        }

        return result;
    }

    private void createViewButton(
            final String name,
            final String caption,
            final String description,
            final FontAwesome icon,
            int index
    ) {
        Button button = new Button(
                caption,
                event -> {
                    LOG.trace("Menu click: {}", name);
                    helper.navigateTo(name);
                }
        );
        button.setDescription(description);
        button.setPrimaryStyleName(ValoTheme.MENU_ITEM);

        if (icon != null) {
            button.setIcon(icon);
        }

        viewButtons.put(index, button);

        LOG.debug("Created menu entry: {}", name);
    }
}