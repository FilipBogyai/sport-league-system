package cz.muni.fi.pv243.sportleaguesystem.service;

import java.util.Date;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.SecurityDomain;

import cz.muni.fi.pv243.sportleaguesystem.dao.interfaces.MatchDAO;
import cz.muni.fi.pv243.sportleaguesystem.entities.League;
import cz.muni.fi.pv243.sportleaguesystem.entities.Match;
import cz.muni.fi.pv243.sportleaguesystem.entities.User;
import cz.muni.fi.pv243.sportleaguesystem.service.interfaces.MatchService;

@SecurityDomain("sport")
@RolesAllowed({"ADMIN", "LEAGUE_SUPERVISOR", "PLAYER"})
@Stateless
public class MatchServiceImpl implements MatchService {

	@Inject
	private Logger logger;
	
	@Inject
	private MatchDAO matchDAO;
	
	@RolesAllowed("LEAGUE_SUPERVISOR")
	@Override
	public void createMatch(Match match) {
		if (match == null) {
			logger.error("Creating null match");
            throw new IllegalArgumentException("null match");
	    }
	    if (match.getId() != null) {
	    	logger.error("Creating match with id already set");
            throw new IllegalArgumentException("match id already set");
	    }
	    matchDAO.create(match);
	    logger.info("Created match: " + match);
	}
	
	@Override
	public void updateMatch(Match match) {
		if (match == null) {
			logger.error("Updating null match");
            throw new IllegalArgumentException("null match");
	    }
	    if (match.getId() == null || matchDAO.get(match.getId()) == null) {
	    	logger.error("Updating nonexistent match.");
            throw new IllegalArgumentException("match does not exist");
	    }
	    matchDAO.update(match);
	    logger.info("Updated match with id=" + match.getId());
	}
	
	@Override
	public Match getById(Long id) {
		if (id == null) {
			logger.error("Getting match by null id");
            throw new IllegalArgumentException("null id");
	    }
		logger.info("Returning match with id=" + id);
	    return matchDAO.get(id);
	}

	@RolesAllowed("LEAGUE_SUPERVISOR")
	@Override
	public void deleteMatch(Match match) {
		if (match == null) {
			logger.error("Deleting null match");
            throw new IllegalArgumentException("null match");
	    }
	    matchDAO.delete(match);
	    logger.info("Deleted match with id=" + match.getId());
	}

	@Override
	public List<Match> findByUser(User user) {
		if (user == null) {
			logger.error("Trying to find match by null user");
			throw new IllegalArgumentException("null user");
		}
		logger.info("Returning matches found by user with id=" + user.getId());
		return matchDAO.findMatchesByUser(user);
	}

	@Override
	public List<Match> findByDate(Date from, Date to) {
		if (from == null) {
			logger.error("Trying to find matches from null date");
			throw new IllegalArgumentException("null from date");
		}
		if (to == null) {
			logger.error("Trying to find matches to null date");
			throw new IllegalArgumentException("null to date");
		}
		if (from.after(to)) {
			logger.error("Trying to find matches by dates, where 'from' is after 'to'");
			throw new IllegalArgumentException("from date before to date");
		}
		logger.info("Returning matches found from " + from + " to " + to + ".");
		return matchDAO.findMatchesByDate(from, to);
	}

	@Override
	public List<Match> findByDate(Date from, Date to, League league) {
		if (from == null) {
			logger.error("Trying to find matches from null date");
			throw new IllegalArgumentException("null from date");
		}
		if (to == null) {
			logger.error("Trying to find matches to null date");
			throw new IllegalArgumentException("null to date");
		}
		if (from.after(to)) {
			logger.error("Trying to find matches by dates, where 'from' is after 'to'");
			throw new IllegalArgumentException("from date before to date");
		}
		if (league == null) {
			logger.error("Trying to find matches by null league.");
			throw new IllegalArgumentException("null league");
		}
		logger.info("Returing matches found from " + from + " to " + to + " in the league with id=" + league.getId());
		return matchDAO.findMatchesByDate(from, to, league);
	}
	
	@Override
	public List<Match> findByDate(Date from, Date to, User user) {
		if (from == null) {
			logger.error("Trying to find matches from null date");
			throw new IllegalArgumentException("null from date");
		}
		if (to == null) {
			logger.error("Trying to find matches to null date");
			throw new IllegalArgumentException("null to date");
		}
		if (from.after(to)) {
			logger.error("Trying to find matches by dates, where 'from' is after 'to'");
			throw new IllegalArgumentException("from date before to date");
		}
		if (user == null) {
			logger.error("Trying to find matches by null user.");
			throw new IllegalArgumentException("null user");
		}
		logger.info("Returing matches found from " + from + " to " + to + " by user with id=" + user.getId());
		return matchDAO.findMatchesByDate(from, to, user);
	}

}
