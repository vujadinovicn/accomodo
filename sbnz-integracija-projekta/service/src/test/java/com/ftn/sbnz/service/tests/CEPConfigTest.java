package com.ftn.sbnz.service.tests;

import org.junit.Test;
// import org.kie.api.KieServices;
// import org.kie.api.runtime.KieContainer;
// import org.kie.api.runtime.KieSession;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.ftn.sbnz.model.models.Biljka;
import com.ftn.sbnz.model.models.Man;
import com.ftn.sbnz.model.models.Parent;
import com.ftn.sbnz.model.models.Woman;
import com.ftn.sbnz.model.models.Telefon;



public class CEPConfigTest {

    @Test
    public void test() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer(); 
        KieSession ksession = kContainer.newKieSession("bwKsession");

        ksession.insert(new Biljka("Golosemenice", "Biljka"));
        ksession.insert(new Biljka("Skrivenosemenice", "Biljka"));
        ksession.insert(new Biljka("Alge", "Golosemenice"));
        ksession.insert(new Biljka("Kombu alga", "Alga"));
        ksession.insert(new Biljka("Nori alga", "Alga"));
        ksession.insert(new Biljka("Agar agar alga", "Alga"));
        ksession.insert(new Biljka("Mahovine", "Golosemenice"));
        ksession.insert(new Biljka("Plutajuća Riccia", "Mahovine"));
        ksession.insert(new Biljka("Javanska mahovina", "Mahovine"));
        ksession.insert(new Biljka("Paprati", "Golosemenice"));
        ksession.insert(new Biljka("Dicksonia antarctica", "Paprati"));
        ksession.insert(new Biljka("Blechnum nudum", "Paprati"));
        ksession.insert(new Biljka("Sa pupoljkom", "Skrivenosemenice"));
        ksession.insert(new Biljka("Ljiljan", "Sa pupoljkom"));
        ksession.insert(new Biljka("Orhideja", "Sa pupoljkom"));
        ksession.insert(new Biljka("Ruza", "Sa pupoljkom"));
        ksession.insert(new Biljka("Bez pupoljka", "Skrivenosemenice"));
        ksession.insert(new Biljka("Drvo zivota", "Bez pupoljka"));
        ksession.insert(new Biljka("Asparagus", "Bez pupoljka"));
        ksession.insert(new Biljka("Hrizantema", "Bez pupoljka"));

        int count = ksession.fireAllRules();
        System.out.println(count);
        // ksession.insert(new Telefon("S serija", "Samsung"));
        // ksession.insert(new Telefon ("A serija", "Samsung"));
        // ksession.insert(new Telefon("Galaxy S10", "S serija"));
        // ksession.insert(new Telefon("S10e", "Galaxy S10"));
        // ksession.insert(new Telefon("S10+", "Galaxy S10"));
        // ksession.insert(new Telefon("S10 Lite", "Galaxy S10"));
        // ksession.insert(new Telefon("Galaxy S20", "S serija"));
        // ksession.insert(new Telefon("S20", "Galaxy S20"));
        // ksession.insert(new Telefon("S20+", "Galaxy S20"));
        // ksession.insert(new Telefon("S20 FE", "Galaxy S20"));
        // ksession.insert(new Telefon("Galaxy Ax0", "A serija"));
        // ksession.insert(new Telefon("A10", "Galaxy Ax0"));
        // ksession.insert(new Telefon("A20", "Galaxy Ax0"));
        // ksession.insert(new Telefon("Galaxy Ax4", "A serija"));
        // ksession.insert(new Telefon("A04e", "Galaxy Ax4"));
        // ksession.insert(new Telefon("A23", "Galaxy Ax4"));

        // ksession.insert(new Man("john"));
        // ksession.insert(new Woman("janet"));
        // // parent
        // ksession.insert(new Man("adam"));
        // ksession.insert(new Parent("john", "adam"));
        // ksession.insert(new Parent("janet", "adam"));
        // ksession.insert(new Man("stan"));
        // ksession.insert(new Parent("john", "stan"));
        // ksession.insert(new Parent("janet", "stan"));
        // // grand parents
        // ksession.insert(new Man("carl"));
        // ksession.insert(new Woman("tina"));
        // //
        // // parent
        // ksession.insert(new Woman("eve"));
        // ksession.insert(new Parent("carl", "eve"));
        // ksession.insert(new Parent("tina", "eve"));
        // //
        // // parent
        // ksession.insert(new Woman("mary"));
        // ksession.insert(new Parent("carl", "mary"));
        // ksession.insert(new Parent("tina", "mary"));
        // ksession.insert(new Man("peter"));
        // ksession.insert(new Parent("adam", "peter"));
        // ksession.insert(new Parent("eve", "peter"));
        // ksession.insert(new Man("paul"));
        // ksession.insert(new Parent("adam", "paul"));
        // ksession.insert(new Parent("mary", "paul"));
        // ksession.insert(new Woman("jill"));
        // ksession.insert(new Parent("adam", "jill"));
        // ksession.insert(new Parent("eve", "jill"));

        // ksession.insert( "go1" );
        // System.out.println("-----------Woman------------");
        // ksession.fireAllRules();

        // ksession.insert( "go2" );
        // System.out.println("-----------Man------------");
        // ksession.fireAllRules();

        // ksession.insert( "go3" );
        // System.out.println("-----------Father------------");
        // ksession.fireAllRules();

        // int a =1;
        // assertTrue(a==1);
      
    }
}
