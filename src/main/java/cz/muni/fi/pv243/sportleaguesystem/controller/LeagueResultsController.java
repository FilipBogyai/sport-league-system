package cz.muni.fi.pv243.sportleaguesystem.controller;

import cz.muni.fi.pv243.sportleaguesystem.entities.League;
import cz.muni.fi.pv243.sportleaguesystem.entities.PlayerResult;
import cz.muni.fi.pv243.sportleaguesystem.entities.User;
import cz.muni.fi.pv243.sportleaguesystem.service.interfaces.LeagueService;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;

@Model
public class LeagueResultsController {

    @Inject
    private FacesContext facesContext;

    @Inject
    private LeagueService leagueService;

    @Inject
    private SecurityHelper securityHelper;

    private List<PlayerResult> playerResults;
    private League resultsLeague;

    @Produces
    @Named
    public List<PlayerResult> getPlayerResults() {
        return playerResults;
    }

    @Produces
    @Named
    public League getResultsLeague() {
        return resultsLeague;
    }

    @PostConstruct
    public void init() {
        Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap();
        String leagueId = params.get("leagueID");

        if (leagueId == null) return;

        resultsLeague = leagueService.getById(Long.parseLong(leagueId));

        if (resultsLeague == null) return;

        playerResults = leagueService.evaluateLeague(resultsLeague);
    }
}
