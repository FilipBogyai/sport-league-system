package cz.muni.fi.pv243.sportleaguesystem.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import cz.muni.fi.pv243.sportleaguesystem.dao.interfaces.UserDAO;
import cz.muni.fi.pv243.sportleaguesystem.entities.User;
import cz.muni.fi.pv243.sportleaguesystem.service.interfaces.UserService;

@ApplicationScoped
public class UserServiceImpl implements UserService {

	@Inject
	private UserDAO userDAO;
	
	@Override
	public void createUser(User user) {
		if (user == null) {
			throw new IllegalArgumentException("null user");
		}
		/*TODO
		if (user.getId() != null) {
			throw new IllegalArgumentException("user id assigned");
		}
		*/
		userDAO.create(user);
	}

	@Override
	public void updateUser(User user) {
		if (user == null) {
			throw new IllegalArgumentException("null user");
		}
		/*TODO
		if (user.getId() == null || userDAO.get(user.getId()) == null) {
			throw new IllegalArgumentException("nonexistent user");
		}
		*/
		userDAO.update(user);
	}

	@Override
	public User getById(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("null id");
		}
		return userDAO.get(id);
	}

	@Override
	public List<User> getAll() {
		return userDAO.findAll();
	}

	@Override
	public void deleteUser(User user) {
		if (user == null) {
			throw new IllegalArgumentException("null league");
		}
		userDAO.delete(user);
	}

	@Override
	public List<User> findByName(String name) {
		if (name == null) {
			throw new IllegalArgumentException("null name");
		}
		return userDAO.findUsersByName(name);
	}

}
