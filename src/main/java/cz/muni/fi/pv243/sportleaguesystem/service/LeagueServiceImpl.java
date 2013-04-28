package cz.muni.fi.pv243.sportleaguesystem.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import cz.muni.fi.pv243.sportleaguesystem.dao.interfaces.LeagueDAO;
import cz.muni.fi.pv243.sportleaguesystem.entities.League;
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
	public List<League> findByName(String name) {
		if (name == null) {
            throw new IllegalArgumentException("null name");
	    }
	
	    return leagueDAO.findLeaguesByName(name);
	}

	@Override
	public List<League> findByUser(User user) {
		if (user == null) {
			throw new IllegalArgumentException("null user");
		}
		return leagueDAO.findLeaguesByUser(user);
	}

	@Override
	public List<League> findLeaguesBySport(Sport sport) {
		if (sport == null) {
			throw new IllegalArgumentException("null sport");
		}
		return leagueDAO.findLeaguesBySport(sport);
	}

}
