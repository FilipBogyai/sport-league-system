package cz.muni.fi.pv243.sportleaguesystem.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import cz.muni.fi.pv243.sportleaguesystem.entities.League;
import cz.muni.fi.pv243.sportleaguesystem.entities.Principal;
import cz.muni.fi.pv243.sportleaguesystem.entities.Sport;
import cz.muni.fi.pv243.sportleaguesystem.service.interfaces.LeagueService;
import cz.muni.fi.pv243.sportleaguesystem.service.interfaces.PrincipalService;
import cz.muni.fi.pv243.sportleaguesystem.service.interfaces.SportService;

@Model
public class UserLeaguesController {

	@Inject
	private FacesContext facesContext;
	
	@Inject
	private LeagueService leagueService;
	
	@Inject
	private SportService sportService;
	
	@Inject
	private SecurityHelper securityHelper;
	
	@Inject
	private PrincipalService principalService;
	
	private List<League> leagues;
	private Principal principal;

	@Produces
	@Named
	public List<League> getUserLeagues() {
		return leagues;
	}
	
	@PostConstruct
	public void populateLeagues() {
		String remote = securityHelper.getRemoteUser();
		principal = principalService.findPrincipalByLoginName(remote);
		
		leagues = principal.getUser().getLeagues();
	}
}
