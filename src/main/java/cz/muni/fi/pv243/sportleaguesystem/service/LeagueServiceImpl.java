package cz.muni.fi.pv243.sportleaguesystem.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import cz.muni.fi.pv243.sportleaguesystem.dao.interfaces.LeagueDAO;
import cz.muni.fi.pv243.sportleaguesystem.entities.League;
import cz.muni.fi.pv243.sportleaguesystem.entities.Match;
import cz.muni.fi.pv243.sportleaguesystem.entities.Sport;
import cz.muni.fi.pv243.sportleaguesystem.entities.User;
import cz.muni.fi.pv243.sportleaguesystem.service.interfaces.LeagueService;

@ApplicationScoped
public class LeagueServiceImpl implements LeagueService {

	@Inject
	private LeagueDAO leagueDAO;
		
	@Override
	public void createLeague(League league) {
        if (league == null) {
                throw new IllegalArgumentException("null league");
        }
        if (league.getId() != null) {
                throw new IllegalArgumentException("league id already set");
        }
        leagueDAO.create(league);
	}

	@Override
	public void updateLeague(League league) {
		if (league == null) {
            throw new IllegalArgumentException("null league");
	    }
	    if (league.getId() == null || leagueDAO.get(league.getId()) == null) {
	            throw new IllegalArgumentException("league does not exist");
	    }
	    leagueDAO.update(league);
	}

	@Override
	public League getById(Long id) {
		if (id == null) {
            throw new IllegalArgumentException("null id");
	    }
	    return leagueDAO.get(id);
	}

	@Override
	public void deleteLeague(League league) {
		if (league == null) {
            throw new IllegalArgumentException("null league");
	    }
	    leagueDAO.delete(league);
	}

	@Override
	public Map<League, Boolean> findByUser(User user, Sport sport) {
		if (user == null) {
            throw new IllegalArgumentException("null user");
	    }
		if (sport == null) {
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
	    return leagueMap;
	}
	
	@Override
	public List<League> findBySport(Sport sport) {
		if (sport == null) {
            throw new IllegalArgumentException("null sport");
	    }
	
	    return leagueDAO.findLeaguesBySport(sport);
	}
		
	public void addPlayer(User user, League league){
		
		if (user == null ){
			throw new IllegalArgumentException("null league");
		}
		if (user.getId() == null ){
			throw new IllegalArgumentException("null league id");
		}
		
		league = getById(league.getId());
		if(! league.getPlayers().contains(user))
			league.getPlayers().add(user);
		updateLeague(league);
	}
	
	public void generateMatches(League league){
		if (league == null) {
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
		updateLeague(league);		
	}
		
}
