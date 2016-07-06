package com.example.infra;

import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
public class TenantHibernateConfiguration {

	@Bean
	@ConfigurationProperties(prefix = "tenant.datasource")
	public DataSourceProperties systemDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean(name = "tenantDataSource")
	@Primary
	@ConfigurationProperties(prefix = "tenant.datasource")
	public DataSource systemDataSource() {
		return DataSourceBuilder.create().type(systemDataSourceProperties().getType()).build();
	}

	@Bean
	@Primary
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) {
		final Map<String, Object> jpaProperties = new HashMap<>();
		jpaProperties.put(Environment.DEFAULT_SCHEMA, "tenant");
		return builder.dataSource(systemDataSource())
				.persistenceUnit("tenant")
				.properties(jpaProperties)
				.build();
	}

	@Bean
	@Primary
	@Autowired
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
	}
}
