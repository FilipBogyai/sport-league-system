package cz.muni.fi.pv243.sportleaguesystem.service.interfaces;

import cz.muni.fi.pv243.sportleaguesystem.entities.Sport;

import java.util.List;

/**
 *
 * @author Marian Rusnak
 */
public interface SportService {
	
	/**
     * Adds new Sport to the database.
     * @throws IllegalArgumentException if parameter is null or if sport id is already assigned.   
     * @param Sport Sport to add.    
     */
    void createSport(Sport sport);

    /**
     * Updates existing Sport.
     * @throws IllegalArgumentException if parameter is null or if sport does not exist.
     * @param Sport Sport to update (specified by id) with new attributes.   
     */
    void updateSport(Sport sport);

    /**
     * Returns Sport with given id.
     * 
     * @param id primary key of requested Sport.
     * @throws IllegalArgumentException if parameter is null.   
     * @return Sport with given id or null if such Sport doesn't exist. 
     */
    Sport getById(Long id);

    /**
     * Returns list of all Sports in the database.
     * 
     * @return all Sports in the DB or empty list if there are none.  
     */
    List<Sport> getAll();

    /**
     * Removes existing Sport.
     * 
     * @param Sport Sport to remove (specified by id).
     * @throws IllegalArgumentException if parameter is null.   
     */
    void deleteSport(Sport sport);
    
    /**
     * Returns list of Sports with given name
     * 
     * @param name name of requested Sports
     * @throws IllegalArgumentException if parameter is null.
     * @return all Sports with given name or empty list if there are none.  
     */
    List<Sport> findSportsByName(String name);
}
