package com.example;

import com.example.infra.SystemHibernateConfiguration;
import com.example.infra.TenantHibernateConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NewAnnotationFailsTest {

	@Configuration
	@EnableAutoConfiguration
	@Import({SystemHibernateConfiguration.class,
			TenantHibernateConfiguration.class,
			// The difference is in here ↓↓↓
			TenantRepositoryConfigurationNew.class})
	static class Config {
	}

	@Autowired
	TenantRepository repository;

	@Test
	public void contextLoads() {
	}

}
