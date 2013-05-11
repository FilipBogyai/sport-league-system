package cz.muni.fi.pv243.sportleaguesystem.controller;

import javax.annotation.PostConstruct;
import javax.ejb.EJBTransactionRolledbackException;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
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
	
	@Inject
	private LoginController loginModule;
	
	@Inject
	private SecurityHelper securityHelper;
	
	@Produces
	@Named
	private Principal principal;
	
	public Principal getPrincipal() {
		return principal;
	}

	public String register() {
		String password = principal.getPassword();
		try {
			principalService.create(principal);
		} catch (EJBTransactionRolledbackException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login name already exists", "This login is taken, please try another"));
			return "";
		}
		loginModule.setLoginName(principal.getLoginName());
		loginModule.setPassword(password);
		return loginModule.login();
	}

	public String save() {
		principalService.update(principal);
		return "index?faces-redirect=true";
	}
	
	@PostConstruct
	public void populatePrincipal() {
		String remote = securityHelper.getRemoteUser();
		if (remote != null && !"".equals(remote.trim())) {
			principal = principalService.findPrincipalByLoginName(remote);
		}
		else {
			User user = new User();
			principal = new Principal();
			principal.setUser(user);
			principal.setRole(RolesEnum.PLAYER.toString());
		}
	}
}
