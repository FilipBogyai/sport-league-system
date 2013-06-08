package cz.muni.fi.pv243.sportleaguesystem.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.SecurityDomain;

import cz.muni.fi.pv243.sportleaguesystem.dao.interfaces.LeagueDAO;
import cz.muni.fi.pv243.sportleaguesystem.dao.interfaces.MatchDAO;
import cz.muni.fi.pv243.sportleaguesystem.dao.interfaces.PrincipalDAO;
import cz.muni.fi.pv243.sportleaguesystem.dao.interfaces.UserDAO;
import cz.muni.fi.pv243.sportleaguesystem.entities.League;
import cz.muni.fi.pv243.sportleaguesystem.entities.Match;
import cz.muni.fi.pv243.sportleaguesystem.entities.Principal;
import cz.muni.fi.pv243.sportleaguesystem.entities.User;
import cz.muni.fi.pv243.sportleaguesystem.service.interfaces.PrincipalService;

@SecurityDomain("sport")
@RolesAllowed({"ADMIN", "LEAGUE_SUPERVISOR", "PLAYER"})
@Stateless
public class PrincipalServiceImpl implements PrincipalService {

    @Inject
    private Logger logger;

    @Inject
    private PrincipalDAO principalDAO;

    @Inject
    private MatchDAO matchDAO;

    @Inject
    private UserDAO userDAO;

    @Inject
    LeagueDAO leagueDAO;

    @PermitAll
    @Override
    public void create(Principal principal) {
        if (principal == null) {
            logger.error("Creating null principal.");
            throw new IllegalArgumentException("null principal");
        }
        if (principal.getLoginName() == null) {
            logger.error("Creating principal with null loginName.");
            throw new IllegalArgumentException("null loginName");
        }
        if (principal.getPassword() == null) {
            logger.error("Creating principal with null password.");
            throw new IllegalArgumentException("null password");
        }
        principal.setPassword(hashPassword(principal.getPassword()));
        principalDAO.create(principal);
        logger.info("Created principal. " + principal);
    }

    @Override
    public void update(Principal principal) {
        if (principal == null) {
            logger.error("Updating null principal");
            throw new IllegalArgumentException("null principal");
        }
        if (principal.getLoginName() == null) {
            logger.error("Updating principal with null loginName.");
            throw new IllegalArgumentException("null loginName");
        }
        if (principal.getPassword() == null) {
            logger.error("Updating principal with null password.");
            throw new IllegalArgumentException("null password");
        }
        if (principalDAO.get(principal.getLoginName()) == null) {
            logger.error("Updating nonexistent principal.");
            throw new IllegalArgumentException("nonexistent principal");
        }
        Principal tempPrincipal = findPrincipalByLoginName(principal.getLoginName());
        if (!tempPrincipal.getPassword().equals(principal.getPassword()))
            principal.setPassword(hashPassword(principal.getPassword()));
        principalDAO.update(principal);
        logger.info("Updated principal with loginName=" + principal.getLoginName());

    }

    @RolesAllowed("ADMIN")
    @Override
    public void delete(Principal principal) {
        if (principal == null) {
            logger.error("Deleting null principal");
            throw new IllegalArgumentException("null principal");
        }
        User user = principal.getUser();
        List<Match> userMatches = matchDAO.findMatchesByUser(user);
        for (Match match : userMatches) {
            matchDAO.delete(match);
        }
        user = userDAO.get(user.getId());
        List<League> registeredLeagues = user.getLeagues();
        for (League league : registeredLeagues) {
            league.getPlayers().remove(user);
            leagueDAO.update(league);
        }
        principalDAO.delete(principal);
        logger.info("Deleted principal with loginName=" + principal.getLoginName());
    }

    @Override
    public Principal findPrincipalByUser(User user) {
        if (user == null) {
            logger.error("Trying to find principal by null user.");
            throw new IllegalArgumentException("null user");
        }
        logger.info("Returning all principals found by user with id=" + user.getId());
        return principalDAO.findPrincipalByUser(user);
    }

    @Override
    public Principal findPrincipalByLoginName(String loginName) {
        if (loginName == null || "".equals(loginName.trim())) {
            logger.error("Trying to find principal by null loginName.");
            throw new IllegalArgumentException("null loginName");
        }
        logger.info("Returning all principals found by loginName=" + loginName);
        return principalDAO.get(loginName);
    }

    @PermitAll
    public String hashPassword(String password) {

        StringBuilder hexString = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte byteData[] = md.digest();

            for (byte aByteData : byteData) {
                String hex = Integer.toHexString(0xff & aByteData);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
        } catch (NoSuchAlgorithmException ex) {
            // shouldn't occur
        }
        logger.info("Returning hashed password.");
        return hexString.toString();
    }

}
