package com.sk.op.application.entity.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootConfiguration
@EnableJpaRepositories(basePackages = "com.sk.op.application.entity.repository")
public class JpaRepositoriesConfiguration {

}
