package cz.muni.fi.pv243.sportleaguesystem.dao.interfaces;

import java.util.List;

import cz.muni.fi.pv243.sportleaguesystem.entities.Principal;
import cz.muni.fi.pv243.sportleaguesystem.entities.User;

/**
*
* @author Filip Bogyai
*/
public interface PrincipalDAO {
   
   /**
    * Adds new principal to the database.
    * 
    * @param principal principal to add.   
    */
   void create(Principal principal);
    
   /**
    * Returns list of principals with given name
    * 
    * @param loginName of requested principals
    * @return all principals with given loginName or empty list if there are none.    
    */
   List<Principal> findprincipalsByLoginName(String loginName);
   
   /**
    * Updates existing principal.
    * 
    * @param principal principal to update (specified by id) with new attributes.   
    */
   void update(Principal principal);
   
   /**
    * Removes existing principal.
    * 
    * @param principal principal to remove (specified by loginName).  
    */
   void delete(Principal principal);
   
   /**
    * Returns list of all principals in the database.
    * 
    * @return all principals in the DB or empty list if there are none.   
    */
   List<Principal> findAll();
   
   /**
    * Returns principal of given user.
    * 
    * @param user of requested pricipal
    * @return principal of given user 
    * @throws DataAccessException in case of error.
    */
   Principal findUserByUser(User user);
   
   
}
