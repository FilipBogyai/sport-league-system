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
	
	private List<LeagueWrapper> leagues;
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
	public List<LeagueWrapper> getLeagues() {
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
	
	@PostConstruct
	public void populateLeagues() {
		Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap();
		sportId = params.get("sportID");
		String leagueId = params.get("leagueID");
		
		createLeagueList();
		
		if (leagueId != null)
			newLeague = leagueService.getById(Long.parseLong(leagueId));
		else
			newLeague = new League();
	}

	private void createLeagueList() {
		String remote = securityHelper.getRemoteUser();
		principal = principalService.findPrincipalByLoginName(remote);
		
		leagues = new ArrayList<LeagueWrapper>();
		
		if (sportId != null) {
			sport = sportService.getById(Long.parseLong(sportId));
			Map<League, Boolean> principalLeagues = leagueService.findByUser(principal.getUser(), sport);
			for (League league : principalLeagues.keySet()) {
				LeagueWrapper wrapper = new LeagueWrapper();
				wrapper.setLeague(league);
				wrapper.setIsUserLogged(principalLeagues.get(league).booleanValue());
				leagues.add(wrapper);
			}
		}
	}
	
	public class LeagueWrapper {
		private League league;
		private boolean isUserLogged;
		
		@Produces
		public League getLeague() {
			return league;
		}
		
		public void setLeague(League league) {
			this.league = league;
		}
		
		@Produces
		public boolean getIsUserLogged() {
			return isUserLogged;
		}
		
		public void setIsUserLogged(boolean isUserLogged) {
			this.isUserLogged = isUserLogged;
		}
	}
}
