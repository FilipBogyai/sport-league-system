package cz.muni.fi.pv243.sportleaguesystem.test;

import java.util.ArrayList;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

import cz.muni.fi.pv243.sportleaguesystem.dao.impl.LeagueDAOImpl;
import cz.muni.fi.pv243.sportleaguesystem.dao.impl.MatchDAOImpl;
import cz.muni.fi.pv243.sportleaguesystem.dao.impl.PrincipalDAOImpl;
import cz.muni.fi.pv243.sportleaguesystem.dao.impl.SportDAOImpl;
import cz.muni.fi.pv243.sportleaguesystem.dao.impl.UsersDAOImpl;
import cz.muni.fi.pv243.sportleaguesystem.dao.interfaces.LeagueDAO;
import cz.muni.fi.pv243.sportleaguesystem.dao.interfaces.MatchDAO;
import cz.muni.fi.pv243.sportleaguesystem.dao.interfaces.PrincipalDAO;
import cz.muni.fi.pv243.sportleaguesystem.dao.interfaces.SportDAO;
import cz.muni.fi.pv243.sportleaguesystem.dao.interfaces.UserDAO;
import cz.muni.fi.pv243.sportleaguesystem.entities.League;
import cz.muni.fi.pv243.sportleaguesystem.entities.Match;
import cz.muni.fi.pv243.sportleaguesystem.entities.Principal;
import cz.muni.fi.pv243.sportleaguesystem.entities.Sport;
import cz.muni.fi.pv243.sportleaguesystem.entities.User;
import cz.muni.fi.pv243.sportleaguesystem.service.PrincipalServiceImpl;
import cz.muni.fi.pv243.sportleaguesystem.service.UserServiceImpl;
import cz.muni.fi.pv243.sportleaguesystem.service.interfaces.PrincipalService;
import cz.muni.fi.pv243.sportleaguesystem.service.interfaces.UserService;
import cz.muni.fi.pv243.sportleaguesystem.util.Resources;

@RunWith(Arquillian.class)
public class ServicesTest {
	 @Deployment
	 public static Archive<?> createTestArchive() {
		 return ShrinkWrap.create(WebArchive.class,"test.war")
				 .addClasses(Sport.class,League.class,Resources.class,SportDAOImpl.class,SportDAO.class,
						 LeagueDAO.class,LeagueDAOImpl.class,Match.class,User.class,MatchDAO.class,MatchDAOImpl.class,
						 UserDAO.class,UsersDAOImpl.class, PrincipalDAO.class, PrincipalDAOImpl.class , Principal.class,
						 UserService.class, UserServiceImpl.class, PrincipalService.class, PrincipalServiceImpl.class)
				 .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
				 .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				// Deploy our test datasource
		        .addAsWebInfResource("test-ds.xml", "test-ds.xml");
		 
	 }
	 
	 @Inject
	 SportDAO sportDAO;
	 
	 @Inject 
	 LeagueDAO leagueDAO;
	 
	 @Inject
	 UserService userService;
	 
	 @Inject
	 PrincipalService principalService;
	 
	 @Inject 
	 MatchDAO matchDAO;
	 
	 @Inject
	 PrincipalDAO principalDAO; 
	 
	 
	 @Test
	 public void testRegisteringToLeague() throws Exception{
		 
		User user =  buildUser("Vlado", "Vostinar", "4124213");
		userService.createUser(user);
		
		Sport sport = buildSport("tenis");
		sportDAO.create(sport);
		
		League league = buildLeague("tenis A", "top liga", sport);
		leagueDAO.create(league);
		
		userService.registerToLeague(user, league);
		
		user = userService.getById(user.getId());
		league = leagueDAO.get(league.getId());
		
		assertEquals(1, user.getLeagues().size());
		assertEquals(1, league.getPlayers().size());
		
		}
	 @Test
	 public void testDeleteUser() {
	    	User user1 = buildUser("Jozko", "Mrkvicka","0903123456");
	    	//cascaduje sa create
	        //userService.createUser(user1);
	    	Principal principal = buildPrincipal("Pepa", "autobus");
	        principal.setUser(user1);
	        principalDAO.create(principal);	  	                  

	        User user2 = buildUser("Ferko", "Slany","0903654321");
	        userService.createUser(user2);
	        
	        Sport sport = buildSport("tenis");
	        sportDAO.create(sport);
	        
	        League league = buildLeague("extra", "tenisova" , sport);
	        leagueDAO.create(league);
	        
	        //create Match with User1
	        Match match1 = buildMatch(user1, user2, league, "telocvicna");	        
	        matchDAO.create(match1);     
	        
	        //register User1 to League
	        userService.registerToLeague(user1, league);   
	        
	        userService.deleteUser(user1);
	        
	 }
	 
	 @Test
	 public void testDeletePrincipal() {
	    	User user1 = buildUser("Jozko", "Mrkvicka","0903123456");	        
	    	Principal principal = buildPrincipal("Johny", "autobus");
	        principal.setUser(user1);
	        principalService.create(principal);   	                      

	        User user2 = buildUser("Ferko", "Slany","0903654321");
	        userService.createUser(user2);
	        
	        Sport sport = buildSport("tenis");
	        sportDAO.create(sport);
	        
	        League league = buildLeague("extra", "tenisova" , sport);
	        leagueDAO.create(league);
	        
	        //create Match with User1
	        Match match1 = buildMatch(user1, user2, league, "telocvicna");	        
	        matchDAO.create(match1);     
	        
	        //register User1 to League
	        userService.registerToLeague(user1, league);
	        
	        principalService.delete(principal);
	        
	 }
	 
	 public static User buildUser(String firstName,String lastName, String phoneNumber){
			User user = new User();
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setPhoneNumber(phoneNumber);
			return user;		
		}
		
		public static Match buildMatch(User player1, User player2, League league, String location){
			Match match = new Match();
			match.setPlayer1(player1);
			match.setPlayer2(player2);
			match.setLeague(league);
			match.setLocation(location);		
			return match;		
		}

		public static League buildLeague(String name, String description, Sport sport){
			League league = new League();
			league.setName(name);
			league.setDescription(description);
			league.setSport(sport);
			league.setMatches(new ArrayList<Match>());
			league.setPlayers(new ArrayList<User>());
			return league;		
		}
		public static Sport buildSport(String name){
			Sport sport = new Sport();
			sport.setName(name);
			return sport;		
		}
		
		public static Principal buildPrincipal(String loginName,String password){
			Principal principal = new Principal();
			principal.setLoginName(loginName);
			principal.setPassword(password);
			principal.setRole("ADMIN");
			return principal;
		}

}
