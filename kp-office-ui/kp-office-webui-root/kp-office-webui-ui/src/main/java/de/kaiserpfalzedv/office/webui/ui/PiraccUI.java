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

package de.kaiserpfalzEdv.piracc.ui;

import com.google.common.eventbus.EventBus;
import com.vaadin.addon.jpacontainer.LazyLoadingDelegate;
import com.vaadin.addon.jpacontainer.util.HibernateLazyLoadingDelegate;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Viewport;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.EnableVaadin;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.spring.server.SpringVaadinServlet;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;
import de.kaiserpfalzEdv.vaadin.backend.auth.AuthenticationProvider;
import de.kaiserpfalzEdv.vaadin.backend.auth.CurrentUserStore;
import de.kaiserpfalzEdv.vaadin.i18n.I18NHandler;
import de.kaiserpfalzEdv.vaadin.i18n.impl.I18NHandlerImpl;
import de.kaiserpfalzEdv.vaadin.ui.LoginScreen;
import de.kaiserpfalzEdv.vaadin.ui.menu.Menu;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.CompositeResourceAccessor;
import liquibase.resource.FileSystemResourceAccessor;
import liquibase.resource.ResourceAccessor;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.context.ContextLoaderListener;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Properties;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Viewport("user-scalable=no,initial-scale=1.0")
@Push
@Theme("mytheme")
@Widgetset("de.kaiserpfalzEdv.piracc.MyAppWidgetset")
@SpringUI
public class PiraccUI extends UI {
    private static final Logger LOG = LoggerFactory.getLogger(PiraccUI.class);

    @Inject
    private AuthenticationProvider accessControl;

    @Inject
    private CurrentUserStore userStore;


    @Inject
    private SpringViewProvider viewProvider;

    @Inject
    private EventBus bus;

    @Inject
    private I18NHandler i18n;

    @Inject
    private Menu menu;


    @Override
    protected void init(VaadinRequest vaadinRequest) {
        Responsive.makeResponsive(this);
        setLocale(vaadinRequest.getLocale());
        i18n.setLocale(vaadinRequest.getLocale());
        getPage().setTitle(translate("application.name"));

        if (userStore.getCurrentUser() == null) {
            LOG.info("Starting log-in for session: {}", vaadinRequest.getWrappedSession().getId());

            setContent(new LoginScreen(accessControl, this::showMainView, i18n, bus));
        } else {
            LOG.info("New Request in session: {}", vaadinRequest.getWrappedSession().getId());
            showMainView();
        }
    }

    protected void showMainView() {
        HorizontalLayout screen = new HorizontalLayout();

        CssLayout viewContainer = new CssLayout();
        viewContainer.addStyleName("valo-content");
        viewContainer.setSizeFull();


        screen.setStyleName("main-screen");
        screen.addComponent(menu);
        screen.addComponent(viewContainer);
        screen.setExpandRatio(viewContainer, 1);
        screen.setSizeFull();


        Navigator navigator = new Navigator(this, viewContainer);
        navigator.addProvider(viewProvider);

        addStyleName(ValoTheme.UI_WITH_MENU);
        setContent(screen);

        if (isNotBlank(getNavigator().getState())) {
            getNavigator().navigateTo(getNavigator().getState());
        }

        menu.generate();
    }


    private String translate(final String key) {
        return i18n.get(key);
    }


    @WebServlet(value = {"/piracc/*", "/VAADIN/*"}, name = "PIRACCServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = PiraccUI.class, productionMode = false)
    public static class MyUIServlet extends SpringVaadinServlet {
    }

    @WebListener
    public static class SpringContextLoaderListener extends ContextLoaderListener {}


    @EnableVaadin
    @Configuration
    @ComponentScan(
            basePackages = {
                    "de.kaiserpfalzEdv.vaadin",
                    "de.kaiserpfalzEdv.piracc"
            },
            excludeFilters = {
                    @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Repository.class)
            }
    )
    @EnableJpaRepositories(
            basePackages = {
                    "de.kaiserpfalzEdv.vaadin.backend.db",
                    "de.kaiserpfalzEdv.piracc.backend.db"
            }
    )
    @EnableTransactionManagement
    @EnableJpaAuditing
    // @EnableCaching
    @EnableSpringConfigured
    @PropertySource("/WEB-INF/application.properties")
    public static class MyConfiguration {
        private static final String PROPERTY_NAME_DATABASE_DRIVER   = "db.driver";
        private static final String PROPERTY_NAME_DATABASE_PASSWORD = "db.password";
        private static final String PROPERTY_NAME_DATABASE_URL      = "db.url";
        private static final String PROPERTY_NAME_DATABASE_USERNAME = "db.username";
        private static final String PROPERTY_NAME_DATABASE_SCHEMA   = "db.schema.main";
        private static final String PROPERTY_NAME_CHANGELOG_FILE    = "db.changelog";

        private static final String PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN = "entitymanager.packages.to.scan";

        private static final String PROPERTY_TRANSLATION_FILE = "translation";


        @Resource
        private Environment env;


        @Bean
        public DataSource dataSource() {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();

            dataSource.setDriverClassName(env.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
            dataSource.setUrl(env.getRequiredProperty(PROPERTY_NAME_DATABASE_URL));
            dataSource.setUsername(env.getRequiredProperty(PROPERTY_NAME_DATABASE_USERNAME));
            dataSource.setPassword(env.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD));

            try {
                initializeDatabase(dataSource);
            } catch (SQLException | DatabaseException e) {
                LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);
            }

            return dataSource;
        }

        private void initializeDatabase(final DataSource dataSource) throws DatabaseException, SQLException {
            LOG.info("Updating data structures via Liquibase ...");

            String schema = env.getProperty(PROPERTY_NAME_DATABASE_SCHEMA, "piracc_admin");
            String changelog = env.getProperty(PROPERTY_NAME_CHANGELOG_FILE, "data/db-changelog.xml");
            Connection connection = null;
            Database database = null;

            try {
                connection = dataSource.getConnection();

                Thread currentThread = Thread.currentThread();
                ClassLoader contextClassLoader = currentThread.getContextClassLoader();
                ResourceAccessor threadClFO = new ClassLoaderResourceAccessor(contextClassLoader);

                ResourceAccessor clFO = new ClassLoaderResourceAccessor();
                ResourceAccessor fsFO = new FileSystemResourceAccessor();


                database = DatabaseFactory.getInstance()
                                          .findCorrectDatabaseImplementation(new JdbcConnection(connection));
                database.setDefaultSchemaName(schema);
                database.setLiquibaseSchemaName(schema);
                Liquibase liquibase = new Liquibase(changelog, new CompositeResourceAccessor(clFO, fsFO, threadClFO), database);


                liquibase.update(new Contexts(), new LabelExpression());

                LOG.info("Database up-to-date.");
            } catch (SQLException | LiquibaseException e) {
                LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);
            } finally {
                if (database != null) {
                    database.close();
                } else if (connection != null) {
                    connection.close();
                }
            }
        }


        @Bean
        public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
            LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
            entityManagerFactoryBean.setDataSource(dataSource());
            entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);

            String[] packages = env.getRequiredProperty(PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN).split(",");
            entityManagerFactoryBean.setPackagesToScan(packages);

            Properties hibProperties = hibProperties();
            entityManagerFactoryBean.setJpaProperties(hibProperties);

            LOG.debug("Packages to scan: {}", env.getRequiredProperty(PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN));
            LOG.debug("Data Source: {}", dataSource());
            LOG.debug("Persistent Provider: {}", HibernatePersistenceProvider.class.getCanonicalName());
            LOG.trace("HBM2DDL Auto Setting: {}", hibProperties.getProperty(AvailableSettings.HBM2DDL_AUTO));
            LOG.trace("Hibernate Dialect: {}", hibProperties.getProperty(AvailableSettings.DIALECT));
            LOG.trace("Show SQL: {}", hibProperties.get(AvailableSettings.SHOW_SQL));

            return entityManagerFactoryBean;
        }

        private Properties hibProperties() {
            Properties properties = new Properties();
            properties.put(AvailableSettings.HBM2DDL_AUTO, env.getRequiredProperty(AvailableSettings.HBM2DDL_AUTO));
            properties.put(AvailableSettings.DIALECT, env.getRequiredProperty(AvailableSettings.DIALECT));
            properties.put(AvailableSettings.SHOW_SQL, env.getRequiredProperty(AvailableSettings.SHOW_SQL));
            return properties;
        }

        @Bean
        public JpaTransactionManager transactionManager() {
            JpaTransactionManager transactionManager = new JpaTransactionManager();
            transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
            return transactionManager;
        }

        @Bean
        public CacheManager cacheManager() {
            return new EhCacheCacheManager(ehCacheCacheManager().getObject());
        }

        @Bean
        public EhCacheManagerFactoryBean ehCacheCacheManager() {
            EhCacheManagerFactoryBean cmfb = new EhCacheManagerFactoryBean();
            cmfb.setConfigLocation(new ClassPathResource("ehcache.xml"));
            cmfb.setShared(true);
            return cmfb;
        }

        @Bean
        public LazyLoadingDelegate lazyLoadingDelegate() {
            return new HibernateLazyLoadingDelegate();
        }


        @Bean
        I18NHandler i18nHandler() {
            return new I18NHandlerImpl(env.getRequiredProperty(PROPERTY_TRANSLATION_FILE), Locale.getDefault());
        }

        @Bean
        public EventBus uiEventBus() {
            return new EventBus("bus-" + System.identityHashCode(this));
        }
    }
}
