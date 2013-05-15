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
public class LeagueController {

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
	private Map<League, Boolean> principalLeagues;
	private League newLeague;
	private String sportId;
	private Sport sport;	
	private Principal principal;
	
	public String getSportId() {
		return sportId;
	}

	@Produces
	@Named
	public League getNewLeague() {
		return newLeague;
	}

	@Produces
	@Named
	public List<League> getLeagues() {
		return leagues;
	}

	public String add() {
		newLeague.setSport(sport);
		leagueService.createLeague(newLeague);
		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Added!", "New League added successfully"));
		return "index?faces-redirect=true&sportID=" + sportId;
	}
	
	public String save() {
		leagueService.updateLeague(newLeague);
		return "index?faces-redirect=true&sportID=" + sportId;
	}
	
	public String remove() {
		leagueService.deleteLeague(newLeague);
		return "index?faces-redirect=true&sportID=" + sportId;
	}
	
	public String signIn() {
		leagueService.addPlayer(principal.getUser(), newLeague);
		return "index?faces-redirect=true&sportID=" + sportId;
	}
	
	public Boolean userInLeague(String currentLeague) {
		League currLeague = leagueService.getById(Long.parseLong(currentLeague));
		return principalLeagues.get(currLeague);
	}
	
	@PostConstruct
	public void populateLeagues() {
		Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap();
		sportId = params.get("sportID");
		String leagueId = params.get("leagueID");
		
		String remote = securityHelper.getRemoteUser();
		principal = principalService.findPrincipalByLoginName(remote);
		leagues = new ArrayList<League>();
		
		if (sportId != null) {
			sport = sportService.getById(Long.parseLong(sportId));
			principalLeagues = leagueService.findByUser(principal.getUser(), sport);
			leagues.addAll(principalLeagues.keySet());
		}
		
		if (leagueId != null)
			newLeague = leagueService.getById(Long.parseLong(leagueId));
		else
			newLeague = new League();
	}
}
