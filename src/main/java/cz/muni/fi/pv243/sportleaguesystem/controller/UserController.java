package cz.muni.fi.pv243.sportleaguesystem.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import cz.muni.fi.pv243.sportleaguesystem.entities.League;
import cz.muni.fi.pv243.sportleaguesystem.entities.Principal;
import cz.muni.fi.pv243.sportleaguesystem.entities.User;
import cz.muni.fi.pv243.sportleaguesystem.service.interfaces.PrincipalService;
import cz.muni.fi.pv243.sportleaguesystem.service.interfaces.UserService;

@Model
public class UserController {

	@Inject
	private FacesContext facesContext;
	
	@Inject
	private UserService userService;
	
	@Inject
	private PrincipalService principalService;
	
	@Inject
	private SecurityHelper securityHelper;
	
	private List<User> users;
	private Principal currentUser;
	
	@Produces
	@Named
	public Principal getCurrentUser() {
		return currentUser;
	}
	
	@Produces
	@Named
	public List<User> getUsers() {
		return users;
	}
	
	public String save() {
		principalService.update(currentUser);
		return "index?faces-redirect=true";
	}
	
	public String remove() {
		principalService.delete(currentUser);
		return "index?faces-redirect=true";
	}
	
	@PostConstruct
	public void populateUsers() {
		Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap();
		String filterName = params.get("filterName");
		String userId = params.get("userID");
		
		String remote = securityHelper.getRemoteUser();
		Principal principal = principalService.findPrincipalByLoginName(remote);
		
		//TODO remove currently logged user from selection
		if (filterName != null && !"".equals(filterName.trim()))
			users = userService.findByName(filterName);
		else
			users = userService.getAll();
		users.remove(principal.getUser());
		
		if (userId != null) {
			User user = userService.getById(Long.parseLong(userId));
			currentUser = principalService.findUserByUser(user);
		}
	}
}
