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
     * Creates a new Match in the database.
     *
     * @throws IllegalArgumentException if parameter is null or if match id is already assigned. 
     * @param Match Match to create.    
     */
    void createMatch(Match match);

    /**
     * TODO
     * @param match
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
     * Removes existing Match it from database
     * 
     * @throws IllegalArgumentException if parameter is null.   
     * @param Match Match to remove.    
     */
    void deleteMatch(Match match);

    /**		
     * Return all Matches of given user.		
     *		
     * @param client client		
     * @throws IllegalArgumentException if parameter is null.   
     * @return Matches of given user.		     	
     */		
    List<Match> findByUser(User user);

    /**
     * Return Matches within given date interval.
     * 
     * @param from start of the interval
     * @param to end of the interval
     * @throws IllegalArgumentException if parameter is null or if from date is after to date.   
     * @return Matches within given date interval.    
     */
    List<Match> findByDate(Date from, Date to);

    /**
     * Return Matches withing given date interval in given league.
     * 
     * @param from start of the interval
     * @param to end of the interval
     * @param league league
     * @throws IllegalArgumentException if parameter is null or if from date is after to date.   
     * @return Matches withing given date interval in given league.     
     */
	List<Match> findByDate(Date from, Date to, League league);
	
	/**
     * Return Matches withing given date interval and player.
     * 
     * @param from start of the interval
     * @param to end of the interval
     * @param user user
     * @throws IllegalArgumentException if parameter is null or if from date is after to date.   
     * @return Matches withing given date interval and player.     
     */
	List<Match> findByDate(Date from, Date to, User user);
}
