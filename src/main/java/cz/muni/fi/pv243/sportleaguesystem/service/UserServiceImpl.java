package cz.muni.fi.pv243.sportleaguesystem.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import cz.muni.fi.pv243.sportleaguesystem.dao.interfaces.LeagueDAO;
import cz.muni.fi.pv243.sportleaguesystem.dao.interfaces.MatchDAO;
import cz.muni.fi.pv243.sportleaguesystem.dao.interfaces.UserDAO;
import cz.muni.fi.pv243.sportleaguesystem.entities.League;
import cz.muni.fi.pv243.sportleaguesystem.entities.Match;
import cz.muni.fi.pv243.sportleaguesystem.entities.User;
import cz.muni.fi.pv243.sportleaguesystem.service.interfaces.UserService;

@ApplicationScoped
public class UserServiceImpl implements UserService {

	@Inject
	private UserDAO userDAO;
	
	@Inject 
	private MatchDAO matchDAO;
	
	@Inject
	private LeagueDAO leagueDAO;
	
	@Override
	public void createUser(User user) {
		if (user == null) {
			throw new IllegalArgumentException("null user");
		}		
		if (user.getId() != null) {
			throw new IllegalArgumentException("user id assigned");
		}		
		userDAO.create(user);
	}

	@Override
	public void updateUser(User user) {
		if (user == null) {
			throw new IllegalArgumentException("null user");
		}
		
		if (user.getId() == null || userDAO.get(user.getId()) == null) {
			throw new IllegalArgumentException("nonexistent user");
		}		
		userDAO.update(user);
	}

	@Override
	public User getById(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("null id");
		}
		return userDAO.get(id);
	}

	@Override
	public List<User> getAll() {
		return userDAO.findAll();
	}

	@Override
	public void deleteUser(User user) {
		if (user == null) {
			throw new IllegalArgumentException("null league");
		}
		List<Match> userMatches = matchDAO.findMatchesByUser(user);
		for(Match match : userMatches){
			matchDAO.delete(match);
		}
		user = getById(user.getId());
		List<League> registeredLeagues = user.getLeagues();
		for(League league : registeredLeagues){			
			league.getPlayers().remove(user);
			leagueDAO.update(league);
		}
		userDAO.delete(user);
	}

	@Override
	public List<User> findByName(String name) {
		if (name == null) {
			throw new IllegalArgumentException("null name");
		}
		return userDAO.findUsersByName(name);
	}
	
	@Override
	public void registerToLeague(User user, League league){
		
		if (league == null ){
			throw new IllegalArgumentException("null league");
		}
		if (league.getId() == null ){
			throw new IllegalArgumentException("null league id");
		}
		user = getById(user.getId());
		league = leagueDAO.get(league.getId());
		if(! league.getPlayers().contains(user))
			league.getPlayers().add(user);
		leagueDAO.update(league);
	}

}
