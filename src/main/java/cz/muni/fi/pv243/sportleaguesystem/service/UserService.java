package cz.muni.fi.pv243.sportleaguesystem.service;

import cz.muni.fi.pv243.sportleaguesystem.entities.User;

import java.util.List;

/**
 *
 * @author Marian Rusnak
 */
public interface UserService {
    void createUser(User user);

    void updateUser(User user);

    User getById(Long id);

    List<User> getAll();

    void deleteUser(User league);

    List<User> findByName(String name);
}
