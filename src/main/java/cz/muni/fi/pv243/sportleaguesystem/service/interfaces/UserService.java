package cz.muni.fi.pv243.sportleaguesystem.service.interfaces;

import cz.muni.fi.pv243.sportleaguesystem.entities.User;

import java.util.List;

/**
 *
 * @author Marian Rusnak
 */
public interface UserService {
	

    /**
     * Adds new User to the database.
     * 
     * @param User User to add.   
     * @throws IllegalArgumentException if parameter is null or if user id is already assigned.
     */
    void createUser(User user);

    /**
     * Updates existing User.
     * 
     * @param User User to update (specified by id) with new attributes.   
     * @throws IllegalArgumentException if parameter is null or if user does not exist.   
     */
    void updateUser(User user);

    /**
     * Returns User with given id.
     * 
     * @param id primary key of requested User.
     * @throws IllegalArgumentException if parameter is null.   
     * @return User with given id or null if such User doesn't exist.  
     */
    User getById(Long id);

    /**
     * Returns list of all Users in the database.
     * 
     * @return all Users in the DB or empty list if there are none.  
     */
    List<User> getAll();

    /**
     * Removes existing User.
     * 
     * @param User User to remove (specified by id).   
     * @throws IllegalArgumentException if parameter is null.   
     */
    void deleteUser(User user);

    /**
     * Returns list of Users with given name
     * 
     * @param name name of requested Users
     * @throws IllegalArgumentException if parameter is null.   
     * @return all Users with given name or empty list if there are none.  
     */
    List<User> findByName(String name);
}
