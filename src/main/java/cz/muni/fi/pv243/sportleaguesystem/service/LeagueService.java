package cz.muni.fi.pv243.sportleaguesystem.service;

import cz.muni.fi.pv243.sportleaguesystem.entities.League;
import cz.muni.fi.pv243.sportleaguesystem.entities.User;

import java.util.List;

/**
 *
 * @author Marian Rusnak
 */
public interface LeagueService {
    void createLeague(League league);

    void updateLeague(League league);

    League getById(Long id);

    void deleteLeague(League league);

    List<League> findByName(String name);

    List<League> findByUser(User user);
}
