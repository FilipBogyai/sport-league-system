package cz.muni.fi.pv243.sportleaguesystem.dao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.validator.cfg.context.ReturnValueConstraintMappingContext;

import cz.muni.fi.pv243.sportleaguesystem.dao.interfaces.LeagueDAO;
import cz.muni.fi.pv243.sportleaguesystem.entities.League;
import cz.muni.fi.pv243.sportleaguesystem.entities.Sport;
import cz.muni.fi.pv243.sportleaguesystem.entities.User;

@Stateless
public class LeagueDAOImpl implements LeagueDAO{

	@Inject
	private EntityManager em;
	
	@Override
	public void create(League league) {
		em.persist(league);		
	}

	@Override
	public League get(Long id) {
		return em.find(League.class, id);
	}

	@Override
	public void update(League league) {
		em.merge(league);
		
	}

	@Override
	public void delete(League league) {
		em.remove(em.merge(league));
		
	}

	@Override
	public List<League> findAll() {
		return (List<League>) em.createQuery("SELECT l FROM League l").getResultList();		
	}

	@Override
	public List<League> findLeaguesByName(String name) {
		Query query = em.createQuery("SELECT l FROM League l WHERE UPPER(name) LIKE UPPER(:name)");
		query.setParameter("name", name + "%");
		return (List<League>) query.getResultList();
	}	
}
