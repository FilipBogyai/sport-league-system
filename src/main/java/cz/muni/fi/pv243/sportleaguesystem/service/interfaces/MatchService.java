package cz.muni.fi.pv243.sportleaguesystem.service.interfaces;

import cz.muni.fi.pv243.sportleaguesystem.entities.League;
import cz.muni.fi.pv243.sportleaguesystem.entities.Match;
import cz.muni.fi.pv243.sportleaguesystem.entities.User;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Marian Rusnak
 */
public interface MatchService {

    /**
     * Updates existing Match.
     *
     * @throws IllegalArgumentException if parameter is null or doesn't exist.
     * @param match match to update
     */
    void updateMatch(Match match);

    /**
     * Returns the Match that has the specified ID.
     *
     * @param id id to search for.
     * @throws IllegalArgumentException if parameter is null.   
     * @return Match with corresponding id,  null if such Match doesn't exist.     
     */
    Match getById(Long id);

    /**
     * Returns all Matches or empty list if no match exists.
     *
     * @return all Matches or empty list if there are none.
     */
    List<Match> findAll();

    /**		
     * Return all Matches of given user.		
     *		
     * @param user user
     * @throws IllegalArgumentException if parameter is null.   
     * @return Matches of given user.		     	
     */		
    List<Match> findByUser(User user);

}
