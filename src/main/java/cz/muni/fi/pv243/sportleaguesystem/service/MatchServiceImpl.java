package cz.muni.fi.pv243.sportleaguesystem.service;

import java.util.Date;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import cz.muni.fi.pv243.sportleaguesystem.dao.interfaces.MatchDAO;
import cz.muni.fi.pv243.sportleaguesystem.entities.League;
import cz.muni.fi.pv243.sportleaguesystem.entities.Match;
import cz.muni.fi.pv243.sportleaguesystem.entities.User;
import cz.muni.fi.pv243.sportleaguesystem.service.interfaces.MatchService;

@ApplicationScoped
public class MatchServiceImpl implements MatchService {

	@Inject
	private MatchDAO matchDAO;
	
	@Override
	public void createMatch(Match match) {
		if (match == null) {
            throw new IllegalArgumentException("null match");
	    }
	    if (match.getId() != null) {
	            throw new IllegalArgumentException("match id already set");
	    }
	    matchDAO.create(match);
	}

	
	@Override
	public void updateMatch(Match match) {
		if (match == null) {
            throw new IllegalArgumentException("null match");
	    }
	    if (match.getId() == null || matchDAO.get(match.getId()) == null) {
	            throw new IllegalArgumentException("match does not exist");
	    }
	    matchDAO.update(match);
	}
	

	@Override
	public Match getById(Long id) {
		if (id == null) {
            throw new IllegalArgumentException("null id");
	    }
	    return matchDAO.get(id);
	}

	@Override
	public void deleteMatch(Match match) {
		if (match == null) {
            throw new IllegalArgumentException("null match");
	    }
	    matchDAO.delete(match);
	}

	@Override
	public List<Match> findByUser(User user) {
		if (user == null) {
			throw new IllegalArgumentException("null user");
		}
		return matchDAO.findMatchesByUser(user);
	}

	@Override
	public List<Match> findByDate(Date from, Date to) {
		if (from == null) {
			throw new IllegalArgumentException("null from date");
		}
		if (to == null) {
			throw new IllegalArgumentException("null to date");
		}
		if (from.after(to)) {
			throw new IllegalArgumentException("from date before to date");
		}
		return matchDAO.findMatchesByDate(from, to);
	}
	
	@Override
	public List<Match> findByDate(Date from, Date to, League league) {
		if (from == null) {
			throw new IllegalArgumentException("null from date");
		}
		if (to == null) {
			throw new IllegalArgumentException("null to date");
		}
		if (from.after(to)) {
			throw new IllegalArgumentException("from date before to date");
		}
		if (league == null) {
			throw new IllegalArgumentException("null league");
		}
		return matchDAO.findMatchesByDate(from, to, league);
	}

}
