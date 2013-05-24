package cz.muni.fi.pv243.sportleaguesystem.controller;

import java.util.Iterator;

import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import cz.muni.fi.pv243.sportleaguesystem.RolesEnum;
import cz.muni.fi.pv243.sportleaguesystem.entities.Match;
import cz.muni.fi.pv243.sportleaguesystem.entities.User;

@Model
public class SecurityHelper {

	@Inject
	private FacesContext facesContext;

	public String getRemoteUser() {
		return facesContext.getExternalContext().getRemoteUser();
	}

	public boolean isInRole(String role) {
		return hasRoles(role);
	}

	public boolean isInRoles(String role1, String role2) {
		return hasRoles(role1, role2);
	}

	public boolean isAuthorized() {
		return hasRoles(RolesEnum.ADMIN.toString(),
				RolesEnum.LEAGUE_SUPERVISOR.toString(),
				RolesEnum.PLAYER.toString());
	}

	public boolean isUserAuthorizedForMatch(User user, Match match) {
        return isInRoles(RolesEnum.ADMIN.toString(), RolesEnum.LEAGUE_SUPERVISOR.toString()) ||
                user.equals(match.getPlayer1()) || user.equals(match.getPlayer2());
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
