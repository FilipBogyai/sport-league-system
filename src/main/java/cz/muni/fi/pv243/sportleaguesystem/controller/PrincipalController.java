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
import javax.validation.constraints.Size;

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
	
	@Produces
	private String temporaryPassword;
	
	public String getTemporaryPassword() {
		return temporaryPassword;
	}
	
	public void setTemporaryPassword(String temporaryPassword) {
		this.temporaryPassword = temporaryPassword;
	}

	public Principal getPrincipal() {
		return principal;
	}

	public String register() {
		temporaryPassword = principal.getPassword();
		try {
			principalService.create(principal);
		} catch (EJBTransactionRolledbackException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login name already exists", "This login is taken, please try another"));
			return "";
		}
		loginModule.setLoginName(principal.getLoginName());
		loginModule.setPassword(temporaryPassword);
		return loginModule.login();
	}

	public String save() {
		if (temporaryPassword != null && !"".equals(temporaryPassword.trim())) {
			if (temporaryPassword.length() < 3 || temporaryPassword.length() > 32) {
				facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Password must contain between 3 to 32 characters", "Password must contain between 3 to 32 characters"));
				return "";
			}
			principal.setPassword(temporaryPassword);
		}
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
