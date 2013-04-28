package cz.muni.fi.pv243.sportleaguesystem.dao.interfaces;

import java.util.List;

import cz.muni.fi.pv243.sportleaguesystem.entities.User;

/**
*
* @author Filip Bogyai
*/
public interface UserDAO {
   
   /**
    * Adds new User to the database.
    * 
    * @param User User to add.   
    */
   void create(User User);
   
   /**
    * Returns User with given id.
    * 
    * @param id primary key of requested User.
    * @return User with given id or null if such User doesn't exist.  
    */
   User get(Long id);
   
   /**
    * Updates existing User.
    * 
    * @param User User to update (specified by id) with new attributes.   
    */
   void update(User User);
   
   /**
    * Removes existing User.
    * 
    * @param User User to remove (specified by id).   
    */
   void delete(User User);
   
   /**
    * Returns list of all Users in the database.
    * 
    * @return all Users in the DB or empty list if there are none.  
    */
   List<User> findAll();
   
   /**
    * Returns list of Users with given name
    * 
    * @param name name of requested Users
    * @return all Users with given name or empty list if there are none.  
    */
   List<User> findUsersByName(String name);
}
