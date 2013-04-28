package cz.muni.fi.pv243.sportleaguesystem.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import cz.muni.fi.pv243.sportleaguesystem.dao.interfaces.PrincipalDAO;
import cz.muni.fi.pv243.sportleaguesystem.entities.Principal;
import cz.muni.fi.pv243.sportleaguesystem.entities.User;
import cz.muni.fi.pv243.sportleaguesystem.service.interfaces.PrincipalService;

@ApplicationScoped
public class PrincipalServiceImpl implements PrincipalService {

	@Inject
	private PrincipalDAO principalDAO;
	
	@Override
	public void create(Principal principal) {
		if (principal == null) {
			throw new IllegalArgumentException("null principal");
		}
		if (principal.getLoginName() == null) {
			throw new IllegalArgumentException("null loginName");
		}
		if (principal.getPassword() == null) {
			throw new IllegalArgumentException("null password");
		}
		principalDAO.create(principal);
	}

	@Override
	public void update(Principal principal) {
		if (principal == null) {
			throw new IllegalArgumentException("null principal");
		}
		if (principal.getLoginName() == null) {
			throw new IllegalArgumentException("null loginName");
		}
		if (principal.getPassword() == null) {
			throw new IllegalArgumentException("null password");
		}
		if (principalDAO.get(principal.getLoginName()) == null) {
			throw new IllegalArgumentException("nonexistent principal");
		}
		principalDAO.update(principal);

	}

	@Override
	public void delete(Principal principal) {
		if (principal == null) {
			throw new IllegalArgumentException("null principal");
		}
		principalDAO.delete(principal);
	}

	@Override
	public List<Principal> findAll() {
		return principalDAO.findAll();
	}

	@Override
	public Principal findUserByUser(User user) {
		if (user == null) {
			throw new IllegalArgumentException("null user");
		}
		return principalDAO.findPrincipalByUser(user);
	}

}
