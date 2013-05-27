package cz.muni.fi.pv243.sportleaguesystem.test;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;

import cz.muni.fi.pv243.sportleaguesystem.entities.League;
import cz.muni.fi.pv243.sportleaguesystem.entities.Match;
import cz.muni.fi.pv243.sportleaguesystem.entities.PlayerResult;
import cz.muni.fi.pv243.sportleaguesystem.entities.Sport;
import cz.muni.fi.pv243.sportleaguesystem.entities.User;
import cz.muni.fi.pv243.sportleaguesystem.util.Resources;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class SortingTest {
   @Deployment
   public static Archive<?> createTestArchive() {
      return ShrinkWrap.create(WebArchive.class, "test.war")
            .addClasses(PlayerResult.class, Resources.class, User.class, League.class, Match.class, Sport.class)
            .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
            // Deploy our test datasource
            .addAsWebInfResource("test-ds.xml", "test-ds.xml");
   }

   

   @Inject
   Logger log;


   @Test
   public void sorResults(){
	   User user = new User();
	   user.setFirstName("Ferko");
	   
	   PlayerResult playerResult1 = new PlayerResult(user, 2, 0, 0, 0, 0, 0);
	   PlayerResult playerResult2 = new PlayerResult(user, 4, 0, 0, 0, 0, 0);
	   PlayerResult playerResult3 = new PlayerResult(user, 1, 0, 0, 0, 0, 0);
	   PlayerResult playerResult4 = new PlayerResult(user, 6, 0, 0, 0, 0, 0);
	   
	   List<PlayerResult> results = new ArrayList<PlayerResult>();
	   results.add(playerResult1);
	   results.add(playerResult2);
	   results.add(playerResult3);
	   results.add(playerResult4);
	   for(PlayerResult playerResult : results){
		   log.info(playerResult.toString());		   
	   }
	   
	   Collections.sort(results);
	   log.info("zoradene");
	   for(PlayerResult playerResult : results){
		   log.info(playerResult.toString());		   
	   }
	   
   }
}
