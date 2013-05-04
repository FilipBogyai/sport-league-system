package cz.muni.fi.pv243.sportleaguesystem.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import cz.muni.fi.pv243.sportleaguesystem.entities.Sport;
import cz.muni.fi.pv243.sportleaguesystem.service.interfaces.SportService;

@Model
public class SportController {
	
	@Inject
	private FacesContext facesContext;
	
	@Inject
	private SportService sportService;
	
	private List<Sport> sports;
	private Sport newSport;

	@Produces
	@Named
	public Sport getNewSport() {
		return newSport;
	}

	@Produces
	@Named
	public List<Sport> getSports() {
		return sports;
	}
	
	public void add() throws IOException {
		sportService.createSport(newSport);
		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Added!", "New Sport added successfully"));
		facesContext.getExternalContext().redirect("index.xhtml");
		retrieveAllSports();
	}
	
	@PostConstruct
	public void retrieveAllSports() {
		sports = sportService.getAll();
		newSport = new Sport();
	}
}
