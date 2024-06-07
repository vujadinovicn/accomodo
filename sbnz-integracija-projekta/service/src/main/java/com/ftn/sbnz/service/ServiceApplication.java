package com.ftn.sbnz.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@EnableJpaRepositories("com.ftn.sbnz.*")
@ComponentScan(basePackages = { "com.ftn.sbnz.*" })
@EntityScan("com.ftn.sbnz.*")
@SpringBootApplication( exclude = { SecurityAutoConfiguration.class } )
public class ServiceApplication  {
	
	public static void main(String[] args) {
		SpringApplication.run(ServiceApplication.class, args);
		System.out.println("KRENULO!");
	}

	@Bean
	public KieContainer kieContainer() {
		KieServices ks = KieServices.Factory.get();
		KieContainer kc = ks.newKieClasspathContainer();
		return kc;
	}

	@Bean
	@DependsOn({ "kieContainer" })
	// @Primary
	public KieSession cepSession(@Autowired KieContainer kieContainer) {
		return kieContainer.newKieSession("cepKsession"); 
	}

	// @Bean
	// @DependsOn({ "kieContainer" })
	// public KieSession backwardSession(@Autowired KieContainer kieContainer) {
	// 	return kieContainer.newKieSession("bwKsession"); 
	// }
	
	/*
	 * KieServices ks = KieServices.Factory.get(); KieContainer kContainer =
	 * ks.newKieContainer(ks.newReleaseId("drools-spring-v2",
	 * "drools-spring-v2-kjar", "0.0.1-SNAPSHOT")); KieScanner kScanner =
	 * ks.newKieScanner(kContainer); kScanner.start(10_000); KieSession kSession =
	 * kContainer.newKieSession();
	 */
}
