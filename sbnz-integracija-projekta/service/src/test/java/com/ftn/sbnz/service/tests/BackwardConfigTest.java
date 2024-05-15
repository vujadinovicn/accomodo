package com.ftn.sbnz.service.tests;

import java.util.concurrent.TimeUnit;

import org.drools.core.fluent.impl.PseudoClockRunner;
import org.drools.core.time.SessionPseudoClock;
import org.junit.Test;
// import org.kie.api.KieServices;
// import org.kie.api.runtime.KieContainer;
// import org.kie.api.runtime.KieSession;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.ftn.sbnz.model.events.Customer;
import com.ftn.sbnz.model.events.TransactionEvent;
import com.ftn.sbnz.model.events.UnsuccessfullLogin;



public class BackwardConfigTest {

    @Test
    public void test() {
        // KieServices ks = KieServices.Factory.get();
        // KieContainer kContainer = ks.getKieClasspathContainer(); 
        // KieSession ksession = kContainer.newKieSession("cepKsession");
        // SessionPseudoClock clock = ksession.getSessionClock();
        
        // UnsuccessfullLogin ul1 = new UnsuccessfullLogin((long)987654321, (long)3);
        // ksession.insert(ul1);
        
        // UnsuccessfullLogin ul2 = new UnsuccessfullLogin((long)987654321, (long)3);
        // clock.advanceTime(1, TimeUnit.MINUTES);
        // ksession.insert(ul2);

        // UnsuccessfullLogin ul3 = new UnsuccessfullLogin((long)987654321, (long)3);
        // clock.advanceTime(2, TimeUnit.MINUTES);
        // ksession.insert(ul3);

        // long count = ksession.fireAllRules();
        // System.out.println(count);
        
        
        
        
        
        
        
        
        
        
        
        
        // Customer customer = new Customer((long)3, 10000.0);
        // ksession.insert(customer);
        // ksession.insert(new TransactionEvent((long)3, 1200.0));
    
        // SessionPseudoClock clock = ksession.getSessionClock();
        // clock.advanceTime(10, TimeUnit.SECONDS);

        // ksession.insert(new TransactionEvent((long)3, 1200.0));
        // // ksession.insert(new TransactionEvent((long)3, 10.0));
        // long no = ksession.fireAllRules();
        // System.out.println(no);
        // System.out.println(customer.getAccountBalance());
    }
}
