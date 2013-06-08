package cz.muni.fi.pv243.sportleaguesystem.controller;

import cz.muni.fi.pv243.sportleaguesystem.entities.League;
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
public class LeaguePlayersController {

    @Inject
    private FacesContext facesContext;

    @Inject
    private LeagueService leagueService;

    @Inject
    private SecurityHelper securityHelper;

    private List<User> players;
    private League playersLeague;

    @Produces
    @Named
    public List<User> getPlayers() {
        return players;
    }

    @Produces
    @Named
    public League getPlayersLeague() {
        return playersLeague;
    }

    @PostConstruct
    public void init() {
        Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap();
        String leagueId = params.get("leagueID");

        if (leagueId == null) return;

        playersLeague = leagueService.getById(Long.parseLong(leagueId));
        players = playersLeague.getPlayers();
    }
}
