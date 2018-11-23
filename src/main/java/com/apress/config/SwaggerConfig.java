package com.apress.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

/**
 * 10-07-18
 *
 * @author Tom
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {

   // In order to disable the default error message codes in Swatter UI,
   // see statement return new Docket...
   // @Bean
   // public SwaggerSpringMvcPlugin   // not for Swagger Fox


    @Bean
    public Docket api() {
        // this shows all the API's, with PathSelectors.any()
        // the version /v1/polls, etc
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select().apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.any())
//                .build()
//                .apiInfo(apiInfo())
//                ;

        // Filtering API
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select().apis(RequestHandlerSelectors.basePackage("com.apress.controller"))
//                .paths(PathSelectors.ant("/polls/*"))
//                .build()
//                .apiInfo(apiInfo() // in order to show the custom info
//                );

        // Filtering API, exclude the basic-error-controller from Spring, using Predicates.not
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("quick-poll-api-v1")
                //.useDefaultResponseMessages(false) // then default response messages will not be shown. Only code 200 is shown by default
                .select().apis(RequestHandlerSelectors.basePackage("com.apress.v1.controller"))  // after adding /v1/ the default basic-error-controller
                // are not shown anymore. Since they are on a higher package level.
//                .paths(Predicates.not(PathSelectors.regex("/error/*"))) // with /error/* also works fine. Not necessary anymore.
                .build()
                .apiInfo(apiInfo() // in order to show the custom info
                );

    }


    // this one is for the with v2. A different group name has to be defined.
    @Bean
    public Docket apiV2() {

        // Filtering API, exclude the basic-error-controller from Spring, using Predicates.not
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("quick-poll-api-v2")
                .select().apis(RequestHandlerSelectors.basePackage("com.apress.v2.controller"))
                .build()
                .apiInfo(apiInfoV2() // in order to show the custom info
                );

    }

    // this one is for the with v3. A different group name has to be defined.
    @Bean
    public Docket apiV3() {

        // Filtering API, exclude the basic-error-controller from Spring, using Predicates.not
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("quick-poll-api-v3")
                .select().apis(RequestHandlerSelectors.basePackage("com.apress.v3.controller"))
                .build()
                .apiInfo(apiInfoV3() // in order to show the custom info
                );

    }

    // custom information
    private ApiInfo apiInfo() {

        // ApiInfoBuilder provides better structure
        return new ApiInfoBuilder()
                .title("Quick Poll REST API")
                .description("Custom description of Spring REST API for Quick Poll application.")
                .version("1.0.0")
                .license("Apache license").version("1.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                .contact(new Contact("John Doe", "https://www.apache.org", "myeaddress@company.com" ))
                .build();

// // this also works fine.
//        ApiInfo apiInfo = new
//                ApiInfo(
//                "Quick Poll REST API",
//                "Some custom description of API.",
//                "API TOS",
//                "Terms of service",
//                new Contact("John Doe", "https://www.apache.org", "myeaddress@company.com"),
//                "License of API", "API license URL", Collections.emptyList()
//        );

      //  return apiInfo;

    }


    private ApiInfo apiInfoV2() {
        // ApiInfoBuilder provides better structure
        return new ApiInfoBuilder()
                .title("Quick Poll REST API")
                .description("Custom description of Spring REST API for Quick Poll application.")
                .version("2.0.0")
                .license("Apache license").version("2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                .contact(new Contact("John Doe", "https://www.apache.org", "myeaddress@company.com" ))
                .build();

    }
    private ApiInfo apiInfoV3() {
        // ApiInfoBuilder provides better structure
        return new ApiInfoBuilder()
                .title("Quick Poll REST API")
                .description("Custom description of Spring REST API for Quick Poll application.")
                .version("3.0.0")
                .license("Apache license").version("2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                .contact(new Contact("John Doe", "https://www.apache.org", "myeaddress@company.com" ))
                .build();

    }

}

