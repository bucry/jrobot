package com.jrobot.website;

import com.jrobot.core.database.HibernateAccess;
import com.jrobot.core.database.JDBCAccess;
import com.jrobot.core.web.DefaultWebConfig;
import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.hibernate.dialect.PostgreSQL82Dialect;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.inject.Inject;
import javax.sql.DataSource;

/**
 * Created by twcn on 10/19/16.
 */
public class DBConfigure extends DefaultWebConfig {

    @Inject
    Environment env;

    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(env.getRequiredProperty("jdbc.driver.name"));
        dataSource.setUrl(env.getRequiredProperty("jdbc.writedb.proxy.url"));
        //dataSource.setUsername(env.getRequiredProperty("jdbc.username"));
        //dataSource.setPassword(env.getRequiredProperty("jdbc.password"));
        dataSource.setTestOnBorrow(true);
        dataSource.setValidationQuery("select 1");
        return dataSource;
    }


    @Bean
    public SessionFactory sessionFactory() {
        LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(
                dataSource()).scanPackages(WebConfigure.class.getPackage()
                .getName());
        //builder.setProperty(org.hibernate.cfg.Environment.DIALECT,
          //      MySQL5Dialect.class.getName());

        builder.setProperty(org.hibernate.cfg.Environment.DIALECT,
                PostgreSQL82Dialect.class.getName());
        //builder.setProperty(org.hibernate.cfg.Environment.HBM2DDL_AUTO, "create");
        return builder.buildSessionFactory();
    }


    @Bean
    public SessionLocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        return localeResolver;
    }

    @Bean
    public HibernateAccess hibernateAccess() {
        HibernateAccess hibernateAccess = new HibernateAccess();
        hibernateAccess.setSessionFactory(sessionFactory());
        return hibernateAccess;
    }

    @Bean
    public JDBCAccess jdbcAccess() {
        JDBCAccess jdbcAccess = new JDBCAccess();
        jdbcAccess.setDataSource(dataSource());
        return jdbcAccess;
    }
}
