package cz.muni.fi.pv243.sportleaguesystem.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.matchers.JUnitMatchers.hasItems;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import cz.muni.fi.pv243.sportleaguesystem.dao.impl.LeagueDAOImpl;
import cz.muni.fi.pv243.sportleaguesystem.dao.impl.MatchDAOImpl;
import cz.muni.fi.pv243.sportleaguesystem.dao.impl.SportDAOImpl;
import cz.muni.fi.pv243.sportleaguesystem.dao.impl.UsersDAOImpl;
import cz.muni.fi.pv243.sportleaguesystem.dao.interfaces.LeagueDAO;
import cz.muni.fi.pv243.sportleaguesystem.dao.interfaces.MatchDAO;
import cz.muni.fi.pv243.sportleaguesystem.dao.interfaces.SportDAO;
import cz.muni.fi.pv243.sportleaguesystem.dao.interfaces.UserDAO;
import cz.muni.fi.pv243.sportleaguesystem.entities.League;
import cz.muni.fi.pv243.sportleaguesystem.entities.Match;
import cz.muni.fi.pv243.sportleaguesystem.entities.Sport;
import cz.muni.fi.pv243.sportleaguesystem.entities.User;
import cz.muni.fi.pv243.sportleaguesystem.util.Resources;

@RunWith(Arquillian.class)
public class UserDAOImplTest {

	@Deployment
	 public static Archive<?> createTestArchive() {
		 return ShrinkWrap.create(WebArchive.class,"test.war")
				 .addClasses(Sport.class,League.class,Resources.class,SportDAOImpl.class,SportDAO.class,
						 LeagueDAO.class,LeagueDAOImpl.class,Match.class,User.class,MatchDAO.class,MatchDAOImpl.class,
						 UserDAO.class,UsersDAOImpl.class)
				 .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
				 .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				// Deploy our test datasource
		        .addAsWebInfResource("test-ds.xml", "test-ds.xml");
		 
	 }
	
	@Inject
	UserDAO userDAO;
	
	@Test
    public void testCreate() {
        User user = buildUser("Jozko", "Mrkvicka","0903123456");
        userDAO.create(user);

        User user2 = userDAO.get(user.getId());
        assertThat(user2, is(not(sameInstance(user))));
        assertThat(user2, is(notNullValue()));
        assertThat(user, is(equalTo(user2)));
    }

    @Test(expected = Exception.class)
    public void testCreateWithNotNullId() {
    	User user = buildUser("Jozko", "Mrkvicka","0903123456");
        user.setId(5l);

        userDAO.create(user);         
    }

    @Test(expected = Exception.class)
    public void testCreateWithNull() {
    	
        userDAO.create(null);           
    }

    @Test
    public void testGet() {
    	User user = buildUser("Jozko", "Mrkvicka","0903123456");
        userDAO.create(user);

        User user1 = userDAO.get(user.getId());
        User user2 = userDAO.get(user.getId());

        assertThat(user1, is(not(sameInstance(user2))));
        assertThat(user1, is(equalTo(user2)));
        assertThat(user1.getFirstName(), is(equalTo(user2.getFirstName())));
        assertThat(user1.getLastName(), is(equalTo(user2.getLastName())));
        assertThat(user1.getPhoneNumber(),is(equalTo(user2.getPhoneNumber())));
        }

    @Test(expected = Exception.class)
    public void testGetWithNull() {
      
    	userDAO.get(null);            
    }

    @Test
    public void testUpdate() {
    	User user1 = buildUser("first", "last","666666");
        userDAO.create(user1);

        user1.setFirstName("Jozko");
        user1.setLastName("Mrkvicka");
        user1.setPhoneNumber("0903123456");
        userDAO.update(user1);

        User user2 = userDAO.get(user1.getId());

        assertThat(user2.getFirstName(), is(equalTo("Jozko")));
        assertThat(user2.getLastName(), is(equalTo("Mrkvicka")));
        assertThat(user2.getPhoneNumber(), is(equalTo("0903123456")));       
    }

    @Test(expected = Exception.class)
    public void testUpdateWithNull() {
        
            userDAO.update(null);           
    }

    @Test
    public void testDelete() {
    	User user1 = buildUser("Jozko", "Mrkvicka","0903123456");
        userDAO.create(user1);

        User user2 = buildUser("Ferko", "Slany","0903654321");
        userDAO.create(user2);

        assertThat(userDAO.get(user1.getId()), is(notNullValue()));
        assertThat(userDAO.get(user2.getId()), is(notNullValue()));

        userDAO.delete(user1);

        assertThat(userDAO.get(user1.getId()), is(nullValue()));
        assertThat(userDAO.get(user2.getId()), is(notNullValue()));
    }

    @Test(expected = Exception.class)
    public void testDeleteWithNull() {
       
    	userDAO.delete(null);            
    }

    @Test
    public void testFindAll() {
        for (User user : userDAO.findAll()) {
            userDAO.delete(user);
        }
        assertTrue(userDAO.findAll().isEmpty());

        User user1 = buildUser("Jozko", "Mrkvicka","0903123456");
        userDAO.create(user1);

        User user2 = buildUser("Ferko", "Slany","0903654321");
        userDAO.create(user2);

        assertThat(userDAO.findAll(), hasItems(user1, user2));
    }

    public static User buildUser(String firstName,String lastName, String phoneNumber){
		User user = new User();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setPhoneNumber(phoneNumber);
		return user;		
	}
}
