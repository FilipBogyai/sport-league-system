package cz.muni.fi.pv243.sportleaguesystem.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import cz.muni.fi.pv243.sportleaguesystem.RolesEnum;
import cz.muni.fi.pv243.sportleaguesystem.entities.Principal;
import cz.muni.fi.pv243.sportleaguesystem.entities.User;
import cz.muni.fi.pv243.sportleaguesystem.service.interfaces.PrincipalService;

@Model
public class PrincipalController {

	@Inject
	private FacesContext facesContext;
	
	@Inject
	private PrincipalService principalService;
	
	@Produces
	@Named
	private Principal principal;
	
	public Principal getPrincipal() {
		return principal;
	}
	
	public String register() {
		principalService.create(principal);
		return "/index?faces-redirect=true";
	}

	public String save() {
		principalService.update(principal);
		return "index?faces-redirect=true";
	}
	
	@PostConstruct
	public void populatePrincipal() {
		String remote = facesContext.getExternalContext().getRemoteUser();
		if (remote != null || !"".equals(remote.trim())) {
			principal = principalService.findPrincipalByLoginName(remote);
		}
		else {
			User user = new User();
			principal = new Principal();
			principal.setUser(user);
		}
		
		// temporary
		/*User user = new User();
		user.setFirstName("Pepa");
		user.setLastName("Vomacka");
		user.setPhoneNumber("123");
		
		principal = new Principal();
		principal.setLoginName("pepa");
		principal.setPassword("pepa");
		principal.setRole(RolesEnum.ADMIN.toString());
		principal.setUser(user);*/
	}
}
