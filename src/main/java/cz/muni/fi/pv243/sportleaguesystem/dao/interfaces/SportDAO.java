package cz.muni.fi.pv243.sportleaguesystem.dao.interfaces;

import java.util.List;

import cz.muni.fi.pv243.sportleaguesystem.entities.Sport;


/**
*
* @author Filip Bogyai
*/
public interface SportDAO {
   
   /**
    * Adds new Sport to the database.
    * 
    * @param Sport Sport to add.    
    */
   void create(Sport Sport);
   
   /**
    * Returns Sport with given id.
    * 
    * @param id primary key of requested Sport.
    * @return Sport with given id or null if such Sport doesn't exist. 
    */
   Sport get(Long id);
   
   /**
    * Updates existing Sport.
    * 
    * @param Sport Sport to update (specified by id) with new attributes.   
    */
   void update(Sport Sport);
   
   /**
    * Removes existing Sport.
    * 
    * @param Sport Sport to remove (specified by id).   
    */
   void delete(Sport Sport);
   
   /**
    * Returns list of all Sports in the database.
    * 
    * @return all Sports in the DB or empty list if there are none.  
    */
   List<Sport> findAll();
   
   /**
    * Returns list of Sports with given name
    * 
    * @param name name of requested Sports
    * @return all Sports with given name or empty list if there are none.  
    */
   List<Sport> findSportsByName(String name);
}