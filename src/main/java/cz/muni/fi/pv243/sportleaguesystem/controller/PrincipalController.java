package cz.muni.fi.pv243.sportleaguesystem.controller;

import javax.annotation.PostConstruct;
import javax.ejb.EJBTransactionRolledbackException;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Size;

import org.apache.log4j.Logger;

import cz.muni.fi.pv243.sportleaguesystem.RolesEnum;
import cz.muni.fi.pv243.sportleaguesystem.entities.Principal;
import cz.muni.fi.pv243.sportleaguesystem.entities.User;
import cz.muni.fi.pv243.sportleaguesystem.service.interfaces.PrincipalService;

@Model
public class PrincipalController {

    @Inject
    private Logger logger;

    @Inject
    private FacesContext facesContext;

    @Inject
    private PrincipalService principalService;

    @Inject
    private LoginController loginModule;

    @Inject
    private SecurityHelper securityHelper;

    @Produces
    @Named
    private Principal principal;

    @Produces
    private String temporaryPassword;

    public String getTemporaryPassword() {
        return temporaryPassword;
    }

    public void setTemporaryPassword(String temporaryPassword) {
        this.temporaryPassword = temporaryPassword;
    }

    public Principal getPrincipal() {
        return principal;
    }

    public String register() {
        logger.info("Preparing to register user.");
        temporaryPassword = principal.getPassword();
        try {
            principalService.create(principal);
            logger.info("Created principal");
        } catch (EJBTransactionRolledbackException e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login name already exists", "This login is taken, please try another"));
            logger.info("Creating a principal failed - login already exists.");
            return "";
        }
        loginModule.setLoginName(principal.getLoginName());
        loginModule.setPassword(temporaryPassword);
        logger.info("Logging in with loginName=" + principal.getLoginName());
        return loginModule.login();
    }

    public String save() {
        if (temporaryPassword != null && !"".equals(temporaryPassword.trim())) {
            if (temporaryPassword.length() < 3 || temporaryPassword.length() > 32) {
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Password must contain between 3 to 32 characters", "Password must contain between 3 to 32 characters"));
                logger.info("Password saving failed - password shorter than 3 characters or longer than 32 characters.");
                return "";
            }
            principal.setPassword(temporaryPassword);
        }
        principalService.update(principal);
        return "index?faces-redirect=true";
    }

    @PostConstruct
    public void populatePrincipal() {
        String remote = securityHelper.getRemoteUser();
        if (remote != null && !"".equals(remote.trim())) {
            principal = principalService.findPrincipalByLoginName(remote);
        } else {
            User user = new User();
            principal = new Principal();
            principal.setUser(user);
            principal.setRole(RolesEnum.PLAYER.toString());
        }
    }
}
