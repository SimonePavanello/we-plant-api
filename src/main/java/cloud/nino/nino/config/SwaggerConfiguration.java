package cloud.nino.nino.config;

import com.google.common.base.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;


@Configuration
@EnableSwagger2
@ComponentScan({"cloud.nino.nino.web.rest"})
public class SwaggerConfiguration {

    private final Logger log = LoggerFactory.getLogger(SwaggerConfiguration.class);

    @Bean
    public Docket swaggerSpringMvcPlugin() {
        return new Docket(DocumentationType.SWAGGER_2)
            .groupName("external")
            .select()
            //Ignores controllers annotated with @CustomIgnore
            .paths(paths()) // and by paths
            .build()
            .apiInfo(apiInfo());
    }

    //Here is an example where we select any api that matches one of these paths
    private Predicate<String> paths() {
        return or(regex("/api/custom.*"));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("Overview")
            .description("")
            .termsOfServiceUrl("http://springfox.io")
            .license("Apache License Version 2.0")
            .licenseUrl("https://github.com/springfox/springfox/blob/master/LICENSE")
            .version("1.0")
            .build();
    }
}
