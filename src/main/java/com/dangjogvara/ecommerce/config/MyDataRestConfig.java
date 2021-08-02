package com.dangjogvara.ecommerce.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.dangjogvara.ecommerce.entity.Product;
import com.dangjogvara.ecommerce.entity.ProductCategory;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

	@Autowired
	private EntityManager entityManager;

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

		exposeIds(config);

	}

	private void exposeIds(RepositoryRestConfiguration config) {
		// expose entity id
		// get a list of all entity classes from the entity manager
		Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

		// create an array of the entity types
		List<Class> entityClasses = new ArrayList<>();

		// get the entity types for the entities
		for (EntityType tempEntityType : entities) {
			entityClasses.add(tempEntityType.getJavaType());
		}

		// expose the entity ids for the entity/domain types
		Class[] domainTypes = entityClasses.toArray(new Class[0]);
		config.exposeIdsFor(domainTypes);
	}

}
