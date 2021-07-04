package com.dangjogvara.ecommerce.config;

import com.dangjogvara.ecommerce.entity.Product;
import com.dangjogvara.ecommerce.entity.ProductCategory;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class MydataRestConfig implements RepositoryRestConfigurer {

	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {

		HttpMethod[] theUnsupportedActions = { HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE };

		// Disable HTTP methods for Product: PUT, POST, DELETE
		config.getExposureConfiguration().forDomainType(Product.class)
				.withItemExposure((metData, httpMethods) -> httpMethods.disable(theUnsupportedActions))
				.withCollectionExposure((metData, httpMethods) -> httpMethods.disable(theUnsupportedActions));

		// Disable HTTP methods for ProductCategory: PUT, POST, DELETE
		config.getExposureConfiguration().forDomainType(ProductCategory.class)
				.withItemExposure((metData, httpMethods) -> httpMethods.disable(theUnsupportedActions))
				.withCollectionExposure((metData, httpMethods) -> httpMethods.disable(theUnsupportedActions));

		// Configure cors mapping
		cors.addMapping("/api/**").allowedOrigins("http://localhost:4200");

	}

}
