package cz.muni.fi.pv243.sportleaguesystem.service.interfaces;

import cz.muni.fi.pv243.sportleaguesystem.entities.League;
import cz.muni.fi.pv243.sportleaguesystem.entities.Sport;
import cz.muni.fi.pv243.sportleaguesystem.entities.User;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Marian Rusnak
 */
public interface LeagueService {
	/**
     * Adds new League to the database.
     *
     * @param League League to add.
     * @throws IllegalArgumentException if parameter is null or id is already assigned.   
     */
    void createLeague(League league);
    
    /**
     * Updates existing League.
     *
     * @param League League to update (specified by id) with new attributes.
     * @throws IllegalArgumentException if parameter is null or if league does not exist.
     */
    void updateLeague(League league);
    
    /**
     * Returns League with given id.
     *
     * @param id primary key of requested League.
     * @throws IllegalArgumentException if parameter is null.   
     * @return League with given id or null if such League doesn't exist. 
     */
    League getById(Long id);

    /**
     * Returns list of all Leagues in the database.
     * 
     * @throws IllegalArgumentException if parameter is null.   
     * @return all Leagues in the DB or empty list if there are none.   
     */
    void deleteLeague(League league);

    /**
     * Returns map of Leagues in the database with in given sport with boolean value whether given user belongs
     * to these leagues.
     *
     * @throws IllegalArgumentException if parameter is null.   
     * @return Leagues and boolean values with given user or empty map if there are none.   
     */
    Map<League, Boolean> findByUser(User user, Sport sport);       
    
    /**
     * Returns list of Leagues in given sport
     *
     * @throws IllegalArgumentException if parameter is null.   
     * @return Leagues in given sport or empty list if there are none.   
     */
    List<League> findBySport(Sport sport);
    
    /**
     * Registers user into the given league
     * 
     * @param User user who will be registered into league
     * @param League league in which user will play
     * @throws IllegalArgumentException if parameters is null.   
     */
    void addPlayer(User user, League league);
    
    /**
     * Creates new round of matches for players registered into league
     *     
     * @param League league in which matches will be generated
     * @throws IllegalArgumentException if parameters is null.   
     */
    void generateMatches(League league);
    
}
