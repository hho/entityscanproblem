package com.example;

// old annotation; works
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(basePackageClasses = TenantRepository.class)
@EntityScan(basePackageClasses = PersistentTenant.class)
public class TenantRepositoryConfigurationOld {

}
