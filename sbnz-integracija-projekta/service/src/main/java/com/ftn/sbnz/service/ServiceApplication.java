package com.ftn.sbnz.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.ftn.sbnz.service.helper.SessionBuilder;

import org.drools.template.DataProvider;
import org.drools.template.objects.ArrayDataProvider;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
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
			new String[] {"Booking", "BookingDeniedEvent", "Posalji notifikaciju traveleru da je booking odbijen!"},
			new String[] {"Reservation", "ReservationDeniedEvent", "Posalji notifikaciju traveleru da je rezervacija odbijen!"}
		});

		DataProvider updateLevelsProvider = new ArrayDataProvider(new String [][]{
			new String[] {"TravelerLevel.BRONZE", "3", "0.0", "90", "33"},
			new String[] {"TravelerLevel.SILVER", "5", "500.0", "90", "23"},
			new String[] {"TravelerLevel.GOLD", "10", "1000.0", "90", "13"},
		});

		DataProvider rewardDestinationProvider = new ArrayDataProvider(new String [][]{
			new String[] {"visited", "3", "-1", "10"},
			new String[] {"frequent", "-1", "3", "5"},
		});

		DataProvider rewardTagProvider = new ArrayDataProvider(new String [][]{
			new String[] {"desireble", "3", "-1", "10"},
			new String[] {"frequent", "-1", "3", "5"},
		});


		DataProvider downgradeLevelsProvider = new ArrayDataProvider(new String [][]{
			new String[] {"TravelerLevel.BRONZE", "TravelerLevel.NONE"},
			new String[] {"TravelerLevel.SILVER", "TravelerLevel.BRONZE"},
			new String[] {"TravelerLevel.GOLD", "TravelerLevel.SILVER"},
		});

		sessionBuilder.addRules("/rules/cep_forward/cep.drl");
		sessionBuilder.addRules("/rules/cep_forward/overlapping-bookings.drl");
		// sessionBuilder.addRules("/rules/cep_forward/flag-checker.drl");
		sessionBuilder.addRules("/rules/cep_forward/favorite-locations-cep.drl");
		sessionBuilder.addRules("/rules/cep_forward/irresponsible-traveler-cep.drl");
		sessionBuilder.addRules("/rules/queries/admin-reports.drl");
		sessionBuilder.addRules("/rules/queries/traveler-reports.drl");
		sessionBuilder.addRules("/rules/queries/owner-reports.drl");
		sessionBuilder.addRules("/rules/queries/filter-listings.drl");
		sessionBuilder.addRules("/rules/backward/backward.drl");
		sessionBuilder.addRules("/rules/cep_forward/recommendation-forward.drl");
		sessionBuilder.addRules("/rules/cep_forward/favorite-listings.drl");

        sessionBuilder.addTemplate("/rules/templates/make-booking-reservation.drt", makeBookingProvider);
        sessionBuilder.addTemplate("/rules/templates/accept-booking-reservation.drt",bookingAcceptenceProvider);
        sessionBuilder.addTemplate("/rules/templates/deny-booking-reservation.drt",bookingDenialProvider);
        sessionBuilder.addTemplate("/rules/templates/update-user-levels.drt",updateLevelsProvider);
        sessionBuilder.addTemplate("/rules/templates/downgrade-user-levels.drt",downgradeLevelsProvider);
        sessionBuilder.addTemplate("/rules/templates/reward-destination.drt",rewardDestinationProvider);
        sessionBuilder.addTemplate("/rules/templates/reward-tag.drt",rewardTagProvider);

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
