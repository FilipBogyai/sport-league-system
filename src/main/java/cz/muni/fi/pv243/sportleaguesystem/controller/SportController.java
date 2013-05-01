package cz.muni.fi.pv243.sportleaguesystem.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import cz.muni.fi.pv243.sportleaguesystem.entities.Sport;
import cz.muni.fi.pv243.sportleaguesystem.service.interfaces.SportService;

@Named
public class SportController {
	
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
	
	public void add() {
		sportService.createSport(newSport);
	}
	
	@PostConstruct
	public void retrieveAllSports() {
		sports = sportService.getAll();
		newSport = new Sport();
	}
}
