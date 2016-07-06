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
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
public class SystemHibernateConfiguration {

	@Bean
	@ConfigurationProperties(prefix = "system.datasource")
	public DataSourceProperties systemDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean(name = "systemDataSource")
	@ConfigurationProperties(prefix = "system.datasource")
	public DataSource systemDataSource() {
		return DataSourceBuilder.create().type(systemDataSourceProperties().getType()).build();
	}

	@Bean(name = "systemEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean systemEntityManagerFactory(EntityManagerFactoryBuilder builder) {
		final Map<String, Object> jpaProperties = new HashMap<>();
		jpaProperties.put(Environment.DEFAULT_SCHEMA, "system");
		return builder.dataSource(systemDataSource())
				.persistenceUnit("system")
				.properties(jpaProperties)
				.build();
	}

	@Bean(name = "systemTransactionManager")
	@Autowired
	public PlatformTransactionManager systemTransactionManager(
			@Qualifier("systemEntityManagerFactory") EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
	}

}
