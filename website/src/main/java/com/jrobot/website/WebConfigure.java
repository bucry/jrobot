package com.jrobot.website;

import org.springframework.context.annotation.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.tiles2.TilesConfigurer;
import org.springframework.web.servlet.view.tiles2.TilesViewResolver;

/**
 * Created by twcn on 10/19/16.
 */


@Configuration
@ComponentScan(basePackageClasses = WebConfigure.class)
@EnableTransactionManagement
// The code equals aop config or provider annotation transaction.
@EnableAspectJAutoProxy
@PropertySource({"classpath:site-jdbc.properties"})
@EnableWebMvc
public class WebConfigure extends DBConfigure {

    @Bean
    public TilesViewResolver viewResolver() {
        return new TilesViewResolver();
    }

    @Bean
    public TilesConfigurer tilesConfigurer() {
        TilesConfigurer tilesConfigurer = new TilesConfigurer();
        tilesConfigurer.setDefinitions(new String[]{"classpath*:config/tiles/page-tiles.xml"});
        return tilesConfigurer;
    }

}