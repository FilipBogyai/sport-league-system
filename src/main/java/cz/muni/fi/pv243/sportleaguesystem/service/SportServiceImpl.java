package cz.muni.fi.pv243.sportleaguesystem.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import cz.muni.fi.pv243.sportleaguesystem.dao.interfaces.SportDAO;
import cz.muni.fi.pv243.sportleaguesystem.entities.Sport;
import cz.muni.fi.pv243.sportleaguesystem.service.interfaces.SportService;

@ApplicationScoped
public class SportServiceImpl implements SportService {

	@Inject
	private SportDAO sportDAO;
	
	@Override
	public void createSport(Sport sport) {
		if (sport == null) {
			throw new IllegalArgumentException("null sport");
		}
		if (sport.getId() != null) {
			throw new IllegalArgumentException("id already set");
		}
		sportDAO.create(sport);
	}

	@Override
	public void updateSport(Sport sport) {
		if (sport == null) {
			throw new IllegalArgumentException("null sport");
		}
		if (sport.getId() == null || sportDAO.get(sport.getId()) == null) {
			throw new IllegalArgumentException("nonexistent sport");
		}
		sportDAO.update(sport);
	}

	@Override
	public Sport getById(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("null id");
		}
		return sportDAO.get(id);
	}

	@Override
	public List<Sport> getAll() {
		return sportDAO.findAll();
	}

	@Override
	public void deleteSport(Sport sport) {
		if (sport == null) {
			throw new IllegalArgumentException("null sport");
		}
		sportDAO.delete(sport);
	}

	@Override
	public List<Sport> findSportsByName(String name) {
		if (name == null) {
			throw new IllegalArgumentException("null name");
		}
		return sportDAO.findSportsByName(name);
	}

}
