package com.ljc.system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger配置类
 *
 * @author dachengzi
 * @date 2023-01-10 23:44
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    /**
     * 配置Docket
     *
     * @return
     */
    @Bean
    public Docket createRestApi() {
        //添加header参数
        List<Parameter> pars = new ArrayList<>();
        ParameterBuilder tokenPar = new ParameterBuilder();
        tokenPar.name("Authorization").description("用户token").defaultValue("").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());

        //Swagger配置
        Docket restApi = new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                //是否开启
                .enable(true).select()
                //扫描的包路径
                .apis(RequestHandlerSelectors.basePackage("com.ljc")).build().securitySchemes(securitySchemes()).securityContexts(securityContexts());
        return restApi;
    }

    /**
     * 设置全局Header
     *
     * @return
     */
    private List<SecurityContext> securityContexts() {
        List<SecurityContext> securityContextList = new ArrayList<>();
        List<SecurityReference> securityReferenceList = new ArrayList<>();
        securityReferenceList.add(new SecurityReference("Authorization", scopes()));
        securityContextList.add(SecurityContext.builder().securityReferences(securityReferenceList).forPaths(PathSelectors.any()).build());
        return securityContextList;
    }

    /**
     * 设置作用域
     *
     * @return
     */
    private AuthorizationScope[] scopes() {
        return new AuthorizationScope[]{new AuthorizationScope("global", "accessAnything")};
    }

    /**
     * 设置请求头的信息
     *
     * @return
     */
    private List<ApiKey> securitySchemes() {
        List<ApiKey> apiKeyList = new ArrayList<>();
        apiKeyList.add(new ApiKey("Authorization", "Authorization", "header"));
        return apiKeyList;
    }

    /**
     * 介绍信息
     *
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("基于区块链的工业建筑信息模型-BiBim").description("本文档描述了系统微服务接口定义").version("1.0").contact(new Contact("dachengzi", "Http://990099.xyz", "dachengzi@88.com")).build();
    }
}
