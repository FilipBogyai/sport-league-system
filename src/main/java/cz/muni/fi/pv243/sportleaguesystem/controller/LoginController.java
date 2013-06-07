package cz.muni.fi.pv243.sportleaguesystem.controller;

import javax.enterprise.inject.Model;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

@Model
public class LoginController {
	
	@Inject
	private Logger logger;
	
	@Inject
	private FacesContext facesContext;
	
	private String loginName;
	private String password;
	
	public String getLoginName() {
		return loginName;
	}
	
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public String login() {
		HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
		try {
			request.login(loginName, password);
			logger.info("The user with loginName=" + loginName + " has logged in.");
		} catch (ServletException e) {
			logger.error("There was an error while logging in.");
			return "/login?faces-redirect=true&error=true";
		}
		return "/index?faces-redirect=true";
	}

	public String logout() throws ServletException {
		HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
		request.logout();
		logger.info("User " + request.getRemoteUser() + " has logged out.");
		return "/login?faces-redirect=true";
	}
}
