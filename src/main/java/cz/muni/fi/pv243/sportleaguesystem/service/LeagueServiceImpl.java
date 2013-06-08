package cz.muni.fi.pv243.sportleaguesystem.service;

import java.util.ArrayList;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.SecurityDomain;

import cz.muni.fi.pv243.sportleaguesystem.dao.interfaces.LeagueDAO;
import cz.muni.fi.pv243.sportleaguesystem.dao.interfaces.UserDAO;
import cz.muni.fi.pv243.sportleaguesystem.entities.League;
import cz.muni.fi.pv243.sportleaguesystem.entities.Match;
import cz.muni.fi.pv243.sportleaguesystem.entities.PlayerResult;
import cz.muni.fi.pv243.sportleaguesystem.entities.Sport;
import cz.muni.fi.pv243.sportleaguesystem.entities.User;
import cz.muni.fi.pv243.sportleaguesystem.service.interfaces.LeagueService;

@SecurityDomain("sport")
@RolesAllowed({"ADMIN", "LEAGUE_SUPERVISOR", "PLAYER"})
@Stateless
public class LeagueServiceImpl implements LeagueService {

    private static final long SIGNED_OUT_ID = 2L;

    @Inject
    private Logger logger;

    @Inject
    private LeagueDAO leagueDAO;

    @Inject
    private UserDAO userDAO;

    @RolesAllowed("ADMIN")
    @Override
    public void createLeague(League league) {
        if (league == null) {
            logger.error("Creating a null league.");
            throw new IllegalArgumentException("null league");
        }
        if (league.getId() != null) {
            logger.error("Creating a league with a set id.");
            throw new IllegalArgumentException("league id already set");
        }
        leagueDAO.create(league);
        logger.info("Created league. " + league.toString());
    }

    @Override
    public void updateLeague(League league) {
        if (league == null) {
            logger.error("Updating null league.");
            throw new IllegalArgumentException("null league");
        }
        if (league.getId() == null || leagueDAO.get(league.getId()) == null) {
            logger.error("Updating nonexistent league.");
            throw new IllegalArgumentException("league does not exist");
        }
        leagueDAO.update(league);
        logger.info("Updated league with Id=" + league.getId());
    }

    @Override
    public League getById(Long id) {
        if (id == null) {
            logger.error("Getting league with null id.");
            throw new IllegalArgumentException("null id");
        }
        logger.info("Returning league with id=" + id);
        return leagueDAO.get(id);
    }

    @RolesAllowed("ADMIN")
    @Override
    public void deleteLeague(League league) {
        if (league == null) {
            logger.error("Deleting null league.");
            throw new IllegalArgumentException("null league");
        }
        leagueDAO.delete(league);
        logger.info("Deleted league with id=" + league.getId());
    }

    @Override
    public Map<League, Boolean> findByUser(User user, Sport sport) {
        if (user == null) {
            logger.error("Finding league by null user.");
            throw new IllegalArgumentException("null user");
        }
        if (sport == null) {
            logger.error("Finding league by null sport");
            throw new IllegalArgumentException("null sport");
        }

        Map<League, Boolean> leagueMap = new HashMap<League, Boolean>();
        List<League> allLeagues = findBySport(sport);
        for (League league : allLeagues) {
            if (user.getLeagues().contains(league)) {
                leagueMap.put(league, true);
            } else {
                leagueMap.put(league, false);
            }
        }
        logger.info("Returning leagues found for user with Id=" + user.getId());
        return leagueMap;
    }

    @Override
    public List<League> findAll() {
        logger.info("Returning all leagues.");
        return leagueDAO.findAll();
    }

    @Override
    public List<League> findBySport(Sport sport) {
        if (sport == null) {
            logger.error("Finding leagues by null sport.");
            throw new IllegalArgumentException("null sport");
        }
        logger.info("Returning all leagues found by sport with id=" + sport.getId());
        return leagueDAO.findLeaguesBySport(sport);
    }

    @Override
    public void addPlayer(User user, League league) {

        if (user == null) {
            logger.error("Adding a null player to a league");
            throw new IllegalArgumentException("null user");
        }
        if (user.getId() == null) {
            logger.error("Adding a player with null id to a league");
            throw new IllegalArgumentException("null user id");
        }
        if (league == null) {
            logger.error("Adding a player to a null league");
            throw new IllegalArgumentException("null league");
        }
        if (league.getId() == null) {
            logger.error("Adding a player to a league with null id");
            throw new IllegalArgumentException("null league id");
        }

        league = getById(league.getId());
        if (!league.getPlayers().contains(user)) {
            league.getPlayers().add(user);
            logger.info("User with id" + user.getId() + " was registered to league with id=" + league.getId());
        }
        updateLeague(league);
        logger.info("Updated league.");
    }

    @Override
    public void removePlayer(User user, League league) {

        if (user == null) {
            logger.error("Removing a null player from league");
            throw new IllegalArgumentException("null user");
        }
        if (user.getId() == null) {
            logger.error("Removing a player with null id from league");
            throw new IllegalArgumentException("null user id");
        }
        if (league == null) {
            logger.error("Removing a player from null league");
            throw new IllegalArgumentException("null league");
        }
        if (league.getId() == null) {
            logger.error("Removing a player from league with null id");
            throw new IllegalArgumentException("null league id");
        }

        league = getById(league.getId());
        if (league.getPlayers().contains(user)) {
            for (Match match : league.getMatches()) {
                if (match.getPlayer1().equals(user)) {
                    match.setPlayer1(userDAO.get(SIGNED_OUT_ID));
                }
                if (match.getPlayer2().equals(user)) {
                    match.setPlayer2(userDAO.get(SIGNED_OUT_ID));
                }
            }
            league.getPlayers().remove(user);
            logger.info("User with id=" + user.getId() + " was removed from league with id=" + league.getId());
        }
        updateLeague(league);
        logger.info("Updated league.");
    }

    @Override
    public Map<Sport, List<League>> findLeaguesOrderedBySport(User user) {
        if (user == null) {
            logger.error("Removing a null player from league");
            throw new IllegalArgumentException("null user");
        }
        List<League> leagues = user.getLeagues();
        Map<Sport, List<League>> sortedLeagues = new HashMap<Sport, List<League>>();
        for (League league : leagues) {
            if (!sortedLeagues.containsKey(league.getSport()))
                sortedLeagues.put(league.getSport(), new ArrayList<League>());
            List<League> temp = sortedLeagues.get(league.getSport());
            temp.add(league);
            sortedLeagues.put(league.getSport(), temp);
        }
        return sortedLeagues;
    }

    @RolesAllowed("LEAGUE_SUPERVISOR")
    @Override
    public void generateMatches(League league) {
        if (league == null) {
            logger.error("Generating matches for a null league.");
            throw new IllegalArgumentException("null league");
        }
        if (league.getId() == null) {
            logger.error("Generating matches for a league with null id.");
            throw new IllegalArgumentException("null league id");
        }
        league = getById(league.getId());
        if (league == null) {
            logger.error("Generating matches for a nonexistent league.");
            throw new IllegalArgumentException("nonexistent league");
        }
        List<User> players = new ArrayList<User>(league.getPlayers());
        int count = players.size();
        Random randomGenerator = new Random();
        for (int i = 0; i < count / 2; i++) {
            int randomInt = randomGenerator.nextInt(count - 2 * i);
            User player1 = players.get(randomInt);
            players.remove(randomInt);
            randomInt = randomGenerator.nextInt((count - 1) - 2 * i);
            User player2 = players.get(randomInt);
            players.remove(randomInt);
            Match match = new Match();
            match.setPlayer1(player1);
            match.setPlayer2(player2);
            match.setLeague(league);
            league.getMatches().add(match);
        }
        logger.info("Updated league with new matches.");
        updateLeague(league);
    }

    @Override
    public List<PlayerResult> evaluateLeague(League league) {
        if (league == null) {
            logger.error("Evaluating null league.");
            throw new IllegalArgumentException("null league");
        }

        ArrayList<PlayerResult> results = new ArrayList<PlayerResult>();
        for (User user : league.getPlayers()) {
            PlayerResult playerResult = new PlayerResult(user, 0, 0, 0, 0, 0, 0);
            for (Match match : league.getMatches()) {
                //skip not evaluated match
                if ((match.getScorePlayer1() == null) || (match.getScorePlayer2() == null))
                    continue;

                if (match.getPlayer1().equals(user)) {

                    playerResult.setPlayerScore(playerResult.getPlayerScore() + match.getScorePlayer1());
                    playerResult.setOpponentsScore(playerResult.getOpponentsScore() + match.getScorePlayer2());
                    //won
                    if (match.getScorePlayer1() > match.getScorePlayer2()) {
                        playerResult.setWonCount(playerResult.getWonCount() + 1);
                        playerResult.setPoints(playerResult.getPoints() + 2);
                        //drawn
                    } else if (match.getScorePlayer1().equals(match.getScorePlayer2())) {
                        playerResult.setDrawnCount(playerResult.getDrawnCount() + 1);
                        playerResult.setPoints(playerResult.getPoints() + 1);
                        //lost
                    } else
                        playerResult.setLostCount(playerResult.getLostCount() + 1);

                }
                if (match.getPlayer2().equals(user)) {
                    playerResult.setPlayerScore(playerResult.getPlayerScore() + match.getScorePlayer2());
                    playerResult.setOpponentsScore(playerResult.getOpponentsScore() + match.getScorePlayer1());
                    //won
                    if (match.getScorePlayer2() > match.getScorePlayer1()) {
                        playerResult.setWonCount(playerResult.getWonCount() + 1);
                        playerResult.setPoints(playerResult.getPoints() + 2);
                        //drawn
                    } else if (match.getScorePlayer2().equals(match.getScorePlayer1())) {
                        playerResult.setDrawnCount(playerResult.getDrawnCount() + 1);
                        playerResult.setPoints(playerResult.getPoints() + 1);
                        //lost
                    } else
                        playerResult.setLostCount(playerResult.getLostCount() + 1);

                }
            }
            results.add(playerResult);
        }
        Collections.sort(results);
        logger.info("Returning results.");
        return results;
    }
}