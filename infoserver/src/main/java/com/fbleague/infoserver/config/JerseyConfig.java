package com.fbleague.infoserver.config;

import javax.annotation.PostConstruct;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.fbleague.infoserver.cache.CacheManagerImpl;
import com.fbleague.infoserver.resources.CriteriaResource;
import com.fbleague.infoserver.resources.PositionResource;

import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.config.SwaggerConfigLocator;
import io.swagger.jaxrs.config.SwaggerContextService;
import io.swagger.jaxrs.listing.ApiListingResource;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Component
@Profile("!test")
@EnableSwagger2
public class JerseyConfig extends ResourceConfig {
	static Logger logger = LoggerFactory.getLogger(CacheManagerImpl.class);

    @Value("${spring.jersey.application-path:/}")
    private String apiPath;
	
    @Value("${server.servlet.contextPath}")
    private String contextRoot;
	
	@Value("${appURL}")
	private String appURL;
	
    public JerseyConfig()
    {
    	register(CorsFilter.class);
        register(CriteriaResource.class);
        register(PositionResource.class);
    }
    
	@Bean
	public Client client() {
		return ClientBuilder.newClient(new ClientConfig());
	}
    
    @PostConstruct
    public void init() {
        this.configureSwagger();
    }

    private void configureSwagger() {
        BeanConfig swaggerConfig = new BeanConfig();
        swaggerConfig.setConfigId("League-Infoservice");
        swaggerConfig.setTitle("League Infoservice");
        swaggerConfig.setVersion("v1");
        swaggerConfig.setContact("Shekhar");
        swaggerConfig.setSchemes(new String[] { "http", "https" });
        swaggerConfig.setBasePath(this.contextRoot + "/" + this.apiPath);
        swaggerConfig.setResourcePackage("com.fbleague.infoserver.resources");
        swaggerConfig.setPrettyPrint(true);
        swaggerConfig.setScan(true);
        SwaggerConfigLocator.getInstance().putConfig(SwaggerContextService.CONFIG_ID_DEFAULT, swaggerConfig);
        logger.info(getClass().getPackage().getName());
        logger.info(ApiListingResource.class.getPackage().getName());
        register(ApiListingResource.class);
//        packages(getClass().getPackage().getName(),
//                ApiListingResource.class.getPackage().getName());
    }	
	
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.fbleague.infoserver.spring.swagger.doc.controller"))
				.paths(PathSelectors.any()).build().enable(true).apiInfo(apiInfo());
	}
	
	private ApiInfo apiInfo() {
		Contact contactInfo = new Contact("Shekhar", "www.mywebsite.com", "somemail@gmail.com");
		return new ApiInfoBuilder().title("League-Infoservice")
				.description("A service that provides the football team standing in the league")
				.termsOfServiceUrl(appURL)
				.contact(contactInfo).license("Apache 2.0")
				.licenseUrl("www.apache.org").version("1.0").build();
	}
}
