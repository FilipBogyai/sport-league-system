package cz.muni.fi.pv243.sportleaguesystem.service;

import cz.muni.fi.pv243.sportleaguesystem.entities.Sport;

import java.util.List;

/**
 *
 * @author Marian Rusnak
 */
public interface SportService {
    void createSport(Sport sport);

    void updateSport(Sport sport);

    Sport getById(Long id);

    List<Sport> getAll();

    void deleteSport(Sport sport);
}
