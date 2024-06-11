package com.ftn.sbnz.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.ftn.sbnz.service.helper.SessionBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.util.stream.Collectors;
import org.drools.template.DataProvider;
import org.drools.template.DataProviderCompiler;
import org.drools.template.objects.ArrayDataProvider;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;
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

	// @Bean
	// @DependsOn({ "kieContainer" })
	// // @Primary
	// public KieSession cepSession(@Autowired KieContainer kieContainer) {
	// 	KieSession kieSession = kieContainer.newKieSession("cepKsession"); 
	// 	return kieSession;
	// }

	@Bean
    public KieSession kieSession() {
        SessionBuilder sessionBuilder = new SessionBuilder();

		DataProvider makeBookingProvider = new ArrayDataProvider(new String [][]{
			new String[] {"BookingEvent", "BookingAcceptedEvent", "BookingDeniedEvent", "EmailNotificationType.BOOKING_CREATED", "Posalji notifikaciju owneru za buking!"},
            new String[] {"ReservationEvent", "ReservationAcceptedEvent", "ReservationDeniedEvent", "EmailNotificationType.BOOKING_CREATED", "Posalji notifikaciju owneru za rezervaciju!"}
        });

		DataProvider bookingAcceptenceProvider = new ArrayDataProvider(new String [][]{
			new String[] {"Booking", "BookingAcceptedEvent", "Posalji notifikaciju traveleru da je booking odbijen zbog:"},
			new String[] {"Reservation", "ReservationAcceptedEvent", "Posalji notifikaciju traveleru da je rezervacija odbijena zbog:"}
		});

		DataProvider bookingDenialProvider = new ArrayDataProvider(new String [][]{
			new String[] {"Booking", "BookingDeniedEvent", "Posalji notifikaciju traveleru da je booking prihvacen!"},
			new String[] {"Reservation", "ReservationDeniedEvent", "Posalji notifikaciju traveleru da je rezervacija prihvacena!"}
		});

		sessionBuilder.addRules("/rules/cep/cep.drl");
		sessionBuilder.addRules("/rules/cep/overlapping-bookings.drl");
		// sessionBuilder.addRules("/rules/cep/flag-checker.drl");
		sessionBuilder.addRules("/rules/cep/favorite-locations-cep.drl");
		sessionBuilder.addRules("/rules/cep/irresponsible-traveler-cep.drl");
		sessionBuilder.addRules("/rules/queries/admin-reports.drl");
		sessionBuilder.addRules("/rules/queries/traveler-reports.drl");
		sessionBuilder.addRules("/rules/queries/owner-reports.drl");
		sessionBuilder.addRules("/rules/queries/filter-listings.drl");
		sessionBuilder.addRules("/rules/backward/backward.drl");

        sessionBuilder.addTemplate("/rules/templates/make-booking-reservation.drt", makeBookingProvider);
        sessionBuilder.addTemplate("/rules/templates/accept-booking-reservation.drt",bookingAcceptenceProvider);
        sessionBuilder.addTemplate("/rules/templates/deny-booking-reservation.drt",bookingDenialProvider);

        return sessionBuilder.build();
    }

	// @Bean
    // public KieBase templateSession() {
    //     InputStream templateStreamHabits = this.getClass().getResourceAsStream("/rules/cep/template.drt");
    //     DataProvider dataProviderHabits = new ArrayDataProvider(new String[][]{
    //             {"Nemanjina", "1"},
    //     });
    //     DataProviderCompiler converter = new DataProviderCompiler();
    //     String drlHabits = converter.compile(dataProviderHabits, templateStreamHabits);
        
    //     KieHelper kieHelper = new KieHelper();
    //     kieHelper.addContent(drlHabits, ResourceType.DRL);

    //     KieServices kieServices = KieServices.Factory.get();
    //     // kieHelper.addResource(kieServices.getResources().newClassPathResource("rules/cep/cep.drl"), ResourceType.DRL);

    //     KieBase kieBase = kieHelper.build();
    //     return kieBase;
    // }

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
