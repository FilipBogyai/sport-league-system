package cz.muni.fi.pv243.sportleaguesystem.controller;

import java.util.Iterator;

import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import cz.muni.fi.pv243.sportleaguesystem.RolesEnum;

@Model
public class SecurityHelper {

	@Inject
	private FacesContext facesContext;

	public String getRemoteUser() {
		return facesContext.getExternalContext().getRemoteUser();
	}

	public boolean isInRole(String role) {
		String[] roles = { role };
		return hasRoles(roles);
	}

	public boolean isInRoles(String role1, String role2) {
		String[] roles = { role1, role2 };
		return hasRoles(roles);
	}

	public boolean isAuthorized() {
		return hasRoles(RolesEnum.ADMIN.toString(),
				RolesEnum.LEAGUE_SUPERVISOR.toString(),
				RolesEnum.PLAYER.toString());
	}

	private boolean hasRoles(String... roles) {
		HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
		if (request.getRemoteUser() != null) {
			for (String role : roles) {
				if (request.isUserInRole(role))
					return true;
			}
		}
		return false;
	}
}
