package cz.muni.fi.pv243.sportleaguesystem.test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

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

/**
 * @author Filip Bogyai
 */

@RunWith(Arquillian.class)
public class SportLeagueTest {
    @Deployment
    public static Archive<?> createTestArchive() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addClasses(Sport.class, League.class, Resources.class, SportDAOImpl.class, SportDAO.class,
                        LeagueDAO.class, LeagueDAOImpl.class, Match.class, User.class, MatchDAO.class, MatchDAOImpl.class,
                        UserDAO.class, UsersDAOImpl.class)
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
    UserDAO userDAO;

    @Inject
    MatchDAO matchDAO;

    @Inject
    Logger log;

    @Test
    public void testCreatingLeague() throws Exception {
        Sport sport = new Sport();
        sport.setName("tenis");
        sportDAO.create(sport);

        assertNotNull(sport.getId());
        log.info(sport.getName() + " was persisted with id " + sport.getId());

        League league = new League();
        league.setDescription("prva tenisova");
        league.setName("tenis A");
        league.setSport(sport);
        league.setPlayers(new ArrayList<User>());
        league.setMatches(new ArrayList<Match>());

        leagueDAO.create(league);
        assertNotNull(league.getId());
        log.info(league.getName() + " was persisted with id " + league.getId());

        User player1 = new User();
        player1.setFirstName("Pepa");
        player1.setLastName("Velky");
        player1.setPhoneNumber("12345678");
        player1.setLeagues(new ArrayList<League>());

        User player2 = new User();
        player2.setFirstName("Franta");
        player2.setLastName("Maly");
        player2.setPhoneNumber("98765432");
        player2.setLeagues(new ArrayList<League>());

        userDAO.create(player1);
        userDAO.create(player2);

        league.getPlayers().add(player1);
        leagueDAO.update(league);
        //player1.getSportLeagues().add(league);
        //userDAO.update(player1);

        Match match = new Match();
        match.setPlayer1(player1);
        match.setPlayer2(player2);
        match.setLeague(league);
        matchDAO.create(match);

        league = leagueDAO.get(league.getId());
        player1 = userDAO.get(player1.getId());
        assertEquals(1, league.getMatches().size());
        assertEquals(1, league.getPlayers().size());
        assertEquals(1, player1.getLeagues().size());

    }

    @Test
    public void testDeleteUser() {
        User user1 = buildUser("Jozko", "Mrkvicka", "0903123456");
        userDAO.create(user1);

        User user2 = buildUser("Ferko", "Slany", "0903654321");
        userDAO.create(user2);

        Sport sport = buildSport("tenis");
        sportDAO.create(sport);

        League league = buildLeague("extra", "tenisova", sport);
        leagueDAO.create(league);

        Match match1 = buildMatch(user1, user1, league, "telocvicna");
        Match match2 = buildMatch(user2, user1, league, "vonku");
        matchDAO.create(match1);
        matchDAO.create(match2);

        assertThat(matchDAO.get(match1.getId()), is(notNullValue()));
        assertThat(matchDAO.get(match2.getId()), is(notNullValue()));

        matchDAO.delete(match1);

        assertThat(matchDAO.get(match1.getId()), is(nullValue()));
        assertThat(matchDAO.get(match2.getId()), is(notNullValue()));

        matchDAO.delete(match2);
        assertThat(matchDAO.get(match2.getId()), is(nullValue()));

        userDAO.delete(user1);
    }

    public static User buildUser(String firstName, String lastName, String phoneNumber) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhoneNumber(phoneNumber);
        return user;
    }

    public static Match buildMatch(User player1, User player2, League league, String location) {
        Match match = new Match();
        match.setPlayer1(player1);
        match.setPlayer2(player2);
        match.setLeague(league);
        match.setLocation(location);
        return match;
    }

    public static League buildLeague(String name, String description, Sport sport) {
        League league = new League();
        league.setName(name);
        league.setDescription(description);
        league.setSport(sport);
        league.setMatches(new ArrayList<Match>());
        league.setPlayers(new ArrayList<User>());
        return league;
    }

    public static Sport buildSport(String name) {
        Sport sport = new Sport();
        sport.setName(name);
        return sport;
    }

}
