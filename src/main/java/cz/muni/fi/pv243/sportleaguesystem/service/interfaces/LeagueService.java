package cz.muni.fi.pv243.sportleaguesystem.service.interfaces;

import cz.muni.fi.pv243.sportleaguesystem.entities.League;
import cz.muni.fi.pv243.sportleaguesystem.entities.Sport;
import cz.muni.fi.pv243.sportleaguesystem.entities.User;

import java.util.List;

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
     * Returns list of Leagues in the database with given name.
     *
     * @throws IllegalArgumentException if parameter is null.   
     * @return Leagues with given name or empty list if there are none.   
     */
    List<League> findByName(String name);        
    
}
