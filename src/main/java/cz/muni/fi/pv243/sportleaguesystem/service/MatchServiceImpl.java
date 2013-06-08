package cz.muni.fi.pv243.sportleaguesystem.service;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.SecurityDomain;

import cz.muni.fi.pv243.sportleaguesystem.dao.interfaces.MatchDAO;
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

    @Override
    public List<Match> findAll() {
        logger.info("Returning all matches");
        return matchDAO.findAllMatches();
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

}
