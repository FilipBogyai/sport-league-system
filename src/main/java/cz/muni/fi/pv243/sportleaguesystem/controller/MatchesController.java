package cz.muni.fi.pv243.sportleaguesystem.controller;

import cz.muni.fi.pv243.sportleaguesystem.RolesEnum;
import cz.muni.fi.pv243.sportleaguesystem.entities.League;
import cz.muni.fi.pv243.sportleaguesystem.entities.Match;
import cz.muni.fi.pv243.sportleaguesystem.entities.Principal;
import cz.muni.fi.pv243.sportleaguesystem.entities.User;
import cz.muni.fi.pv243.sportleaguesystem.service.interfaces.LeagueService;
import cz.muni.fi.pv243.sportleaguesystem.service.interfaces.MatchService;
import cz.muni.fi.pv243.sportleaguesystem.service.interfaces.PrincipalService;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Model
public class MatchesController {

    @Inject
    private FacesContext facesContext;

    @Inject
    private MatchService matchService;

    @Inject
    private LeagueService leagueService;

    @Inject
    private PrincipalService principalService;

    @Inject
    private SecurityHelper securityHelper;

    private Map<String, String> params;

    private Map<String, List<MatchWrapper>> matches;
    private Match match;
    private League league;
    private Principal principal;

    @Produces
    @Named
    public Match getMatch() {
        return match;
    }

    @Produces
    @Named
    public String getPlayer1Name() {
        return match.getPlayer1().getFirstName() + " " + match.getPlayer1().getLastName();
    }

    @Produces
    @Named
    public String getPlayer2Name() {
        return match.getPlayer2().getFirstName() + " " + match.getPlayer2().getLastName();
    }

    @Produces
    @Named
    public League getLeague() {
        return league;
    }

    @Produces
    @Named
    public Map<String, List<MatchWrapper>> getMatches() {
        return matches;
    }

    public String generateMatches() {
        leagueService.generateMatches(league);
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Matches generated successfully.", "Matches generated successfully"));
        return "index?faces-redirect=true&leagueID=" + league.getId();
    }

    @PostConstruct
    public void init() throws ParseException {
        params = facesContext.getExternalContext().getRequestParameterMap();
        String leagueId = params.get("leagueID");
        String matchId = params.get("matchID");

        String remote = securityHelper.getRemoteUser();
        principal = principalService.findPrincipalByLoginName(remote);

        if (leagueId != null) {
            league = leagueService.getById(Long.parseLong(leagueId));
        }

        if (matchId == null) {
            if (league != null) {
                createMatchesList(league.getMatches());
            } else {
                createMatchesList(matchService.findByUser(principal.getUser()));
            }
        } else {
            Match tmpMatch = matchService.getById(Long.parseLong(matchId));
            if (league != null && !tmpMatch.getLeague().equals(league)) return;
            if (!securityHelper.isUserAuthorizedForMatch(principal.getUser(), tmpMatch)) return;

            match = tmpMatch;
        }
    }

    public String save() {
        // get data from HTML input
        match.setScorePlayer1(getScoreFromParameter("match-edit:scorePlayer1"));
        match.setScorePlayer2(getScoreFromParameter("match-edit:scorePlayer2"));

        matchService.updateMatch(match);

        String urlParams = (league != null) ? "&leagueID=" + league.getId() : "";
        return "index?faces-redirect=true" + urlParams;
    }

    private void createMatchesList(List<Match> matchesList) throws ParseException {
        matches = new TreeMap<String, List<MatchWrapper>>();

        for (Match match : matchesList) {
            MatchWrapper wrapper = new MatchWrapper();
            wrapper.setMatch(match);
            wrapper.setDate(getMatchDateString(match));
            wrapper.setCanEdit(securityHelper.isUserAuthorizedForMatch(principal.getUser(), match));

            String key = getDateKey(match.getStartTime());
            if (!matches.containsKey(key)) {
                List<MatchWrapper> dayMatches = new ArrayList<MatchWrapper>();
                dayMatches.add(wrapper);
                matches.put(key, dayMatches);
            } else {
                matches.get(key).add(wrapper);
            }
        }
    }

    private String getMatchDateString(Match match) throws ParseException {
        Date startTime = match.getStartTime();
        Date endTime = match.getEndTime();

        DateFormat timeFormat = new SimpleDateFormat("HH:mm");

        if (startTime == null && endTime == null)
            return "";
        if (endTime == null)
            return timeFormat.format(startTime);

        StringBuilder builder = new StringBuilder();
        if (getDateKey(startTime).equals(getDateKey(endTime))) {
            builder.append(timeFormat.format(startTime));
            builder.append(" - ");
            builder.append(timeFormat.format(endTime));
        } else {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            builder.append(dateFormat.format(startTime));
            builder.append(" - ");
            builder.append(dateFormat.format(endTime));
        }
        return builder.toString();
    }

    private String getDateKey(Date date) throws ParseException {
        if (date == null) return "Date not set";
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }

    private Integer getScoreFromParameter(String param) {
        String scoreStr = params.get(param);
        return (!scoreStr.trim().equals("")) ? new Integer(scoreStr) : null;
    }

    public class MatchWrapper {
        private Match match;
        private String date;
        private boolean canEdit;

        @Produces
        public Match getMatch() {
            return match;
        }

        public void setMatch(Match match) {
            this.match = match;
        }

        @Produces
        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        @Produces
        public boolean isCanEdit() {
            return canEdit;
        }

        public void setCanEdit(boolean canEdit) {
            this.canEdit = canEdit;
        }
    }
}
