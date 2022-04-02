package com.ycao.mysite;

import com.fasterxml.classmate.TypeResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;
import java.util.Map;

/**
 * swagger configuration class
 * Created by Donghua.Chen on 2018/4/20
 */
@Configuration
@EnableSwagger2
public class Swagger2 {
    @Value("${swagger.show}")
    private boolean swaggerShow;

    @Autowired
    private TypeResolver typeResolver;

    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
//                .alternateTypeRules(//解决返回对象为Map<String, List<VersionPushStatisticResp>>时，Swagger页面报错
//                        AlternateTypeRules.newRule(
//                                typeResolver.resolve(Map.class, String.class, typeResolver.resolve(List.class, VersionPushStatisticResp.class)),
//                                typeResolver.resolve(Map.class, String.class, WildcardType.class), Ordered.HIGHEST_PRECEDENCE))
                .enable(swaggerShow)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ycao.mysite.controller"))
                .paths(PathSelectors.any())
                .build();
    }
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("YCAO Site Swagger Restful Api")
                .description("get more info about me: www.cklovery.life")
                .termsOfServiceUrl("http://cklovery.life")
                .contact("yuancaocc")
                .version("1.0")
                .build();
    }
}
