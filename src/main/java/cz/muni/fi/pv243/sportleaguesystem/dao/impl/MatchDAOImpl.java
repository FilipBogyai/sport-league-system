package cz.muni.fi.pv243.sportleaguesystem.dao.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import cz.muni.fi.pv243.sportleaguesystem.dao.interfaces.MatchDAO;
import cz.muni.fi.pv243.sportleaguesystem.entities.League;
import cz.muni.fi.pv243.sportleaguesystem.entities.Match;
import cz.muni.fi.pv243.sportleaguesystem.entities.User;

@Stateless
public class MatchDAOImpl implements MatchDAO{

	@Inject
	private EntityManager em;
	
	@Override
	public void create(Match match) {
		if (match != null && match.getId() != null) {
			throw new IllegalArgumentException("Cannot create entity with set id.");
		}
		em.persist(match);
	}

	@Override
	public Match get(Long id) {
		return em.find(Match.class, id);
	}

	@Override
	public void update(Match match) {
		em.merge(match);
		
	}

	@Override
	public void delete(Match match) {
		em.remove(em.merge(match));
		
	}

	@Override
	public List<Match> findAllMatches() {
		return (List<Match>) em.createQuery("SELECT m FROM Match m").getResultList();		
	}

	@Override
	public List<Match> findMatchesByUser(User user) {
		Query query = em.createQuery("SELECT m FROM Match m WHERE m.User = :User");		
        query.setParameter("User" , user);		
        return (List<Match>) query.getResultList();	
	}	

	@Override
	public List<Match> findMatchesByDate(Date from, Date to) {
		Query query = em.createQuery("SELECT m FROM Match m WHERE m.startTime >= :from AND m.endTime <= :to");
        query.setParameter("from" , from);
        query.setParameter("to" , to);
        return (List<Match>) query.getResultList();
	}

	@Override
	public List<Match> findMatchesByDate(Date from, Date to, League league) {
		Query query = em.createQuery("SELECT m FROM Match m WHERE m.startTime >= :from AND m.endTime <= :to AND m.league = :league");
        query.setParameter("from" , from);
        query.setParameter("to" , to);
        query.setParameter("league" , league);
        return (List<Match>) query.getResultList();
	}	
}
	