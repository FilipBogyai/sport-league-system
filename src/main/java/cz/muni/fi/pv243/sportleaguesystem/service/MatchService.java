package cz.muni.fi.pv243.sportleaguesystem.service;

import cz.muni.fi.pv243.sportleaguesystem.entities.Match;
import cz.muni.fi.pv243.sportleaguesystem.entities.User;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Marian Rusnak
 */
public interface MatchService {
    void createMatch(Match match);

    void updateMatch(Match match);

    Match getById(Long id);

    void deleteMatch(Match match);

    List<Match> findByUser(User user);

    List<Match> findByUserFromDateToDate(User user, Date from, Date to);
}
