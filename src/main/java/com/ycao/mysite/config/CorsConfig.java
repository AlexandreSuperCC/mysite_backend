package com.ycao.mysite.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

//@Configuration
/**
 * not activated
 *
 * Created by ycao on
 */
public class CorsConfig {
    private CorsConfiguration buildConfig(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");//set visitors' ip address
//        corsConfiguration.addAllowedOrigin("http://139.224.199.41");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");//set visitors' methods
        corsConfiguration.setAllowCredentials(true);//allow taking cookie
        return corsConfiguration;
    }
//    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",buildConfig());//apply cross-domain config to API
        return new CorsFilter(source);
    }

}
