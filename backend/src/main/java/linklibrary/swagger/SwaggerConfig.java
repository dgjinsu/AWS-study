package linklibrary.swagger;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static linklibrary.security.filter.JwtFilter.AUTHORIZATION_HEADER;


/**
 * Swagger 문서로 만들고 싶지 않을 경우 해당 컨트롤러에 @ApiIgnore 어노테이션을 추가하여 제외시킬 수 있음.
 * http://localhost:8080/swagger-ui/index.html
 */
@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    // Swagger 설정
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                // 인증 설정
                .securityContexts(Arrays.asList(securityContext())) // SecurityContext 객체 설정
                .securitySchemes(Arrays.asList(apiKey())) // ApiKey 객체 설정
                .select()
                // API 문서를 생성할 대상 선택
                .apis(RequestHandlerSelectors.basePackage("linklibrary.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    // 인증에 대한 정보 생성
    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }

    // 인증에 대한 정보 설정
    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything"); // 인증 범위 객체 생성
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        // "Authorization"이라는 이름의 보안 참조 객체를 생성하여 반환
        return Arrays.asList(new SecurityReference("Authorization", authorizationScopes));
    }

    // 헤더에 JWT 토큰을 포함하는 ApiKey 객체 생성.
    // 이 객체는 JWT 토큰을 헤더에 포함하는 역할을 함
    // 주의) keyname : jwt 설정에서 쓴 헤더 이름으로 작성해야 함
    // AUTHORIZATION_HEADER 값
    private ApiKey apiKey() {
        return new ApiKey("Authorization", "Authorization", "header");
    }
}
