package cz.muni.fi.pv243.sportleaguesystem.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import cz.muni.fi.pv243.sportleaguesystem.dao.interfaces.LeagueDAO;
import cz.muni.fi.pv243.sportleaguesystem.entities.League;
import cz.muni.fi.pv243.sportleaguesystem.entities.Match;
import cz.muni.fi.pv243.sportleaguesystem.entities.Sport;
import cz.muni.fi.pv243.sportleaguesystem.entities.User;
import cz.muni.fi.pv243.sportleaguesystem.service.interfaces.LeagueService;

@ApplicationScoped
public class LeagueServiceImpl implements LeagueService {

	@Inject
	private Logger logger;
	
	@Inject
	private LeagueDAO leagueDAO;
		
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
        logger.info("Created league. Id: " + league.getId() + " Sport: " + league.getSport());
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
	    logger.info("Updated league: Id: " + league.getId() + " Sport: " + league.getSport());
	}

	@Override
	public League getById(Long id) {
		if (id == null) {
			logger.error("Getting league with null id.");
            throw new IllegalArgumentException("null id");
	    }
		logger.info("Returning league with id: " + id);
	    return leagueDAO.get(id);
	}

	@Override
	public void deleteLeague(League league) {
		if (league == null) {
			logger.error("Deleting null league.");
            throw new IllegalArgumentException("null league");
	    }
	    leagueDAO.delete(league);
		logger.info("Deleted league. Id: " + league.getId() + " Sport: " + league.getSport());
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
	    logger.info("Returning leagues found for user: " + user.getFirstName() + " " + user.getLastName());
	    return leagueMap;
	}
	
	@Override
	public List<League> findAll(){
		logger.info("Returning all leagues.");
		return leagueDAO.findAll();		
	}
	
	@Override
	public List<League> findBySport(Sport sport) {
		if (sport == null) {
			logger.error("Finding leagues by null sport.");
            throw new IllegalArgumentException("null sport");
	    }
		logger.info("Returning all leagues found by sport - id: " + sport.getId() + " name: " + sport.getName());
	    return leagueDAO.findLeaguesBySport(sport);
	}
		
	public void addPlayer(User user, League league){
		
		if (user == null ){
			logger.error("Trying to add a null player to a league");
			throw new IllegalArgumentException("null league");
		}
		if (user.getId() == null ){
			logger.error("Trying to add a nonexistent player to a league");
			throw new IllegalArgumentException("null league id");
		}
		
		league = getById(league.getId());
		if(!league.getPlayers().contains(user)) {
			league.getPlayers().add(user);
		}
		updateLeague(league);
		logger.info("Updated league.");
	}
	
	public void generateMatches(League league){
		if (league == null) {
			logger.error("Generating matches for a null leauge.");
            throw new IllegalArgumentException("null league");
	    }		
		league = getById(league.getId());
		List<User> players = new ArrayList<User>(league.getPlayers());
		int count = players.size();
		Random randomGenerator = new Random();
		for(int i = 0; i < count/2 ; i++){
			int randomInt = randomGenerator.nextInt(count - 2*i);
			User player1 = players.get(randomInt);
			players.remove(randomInt);
			randomInt = randomGenerator.nextInt((count-1) - 2*i);
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
		
}
