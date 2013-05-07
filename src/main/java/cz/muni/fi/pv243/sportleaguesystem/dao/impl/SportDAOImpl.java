package cz.muni.fi.pv243.sportleaguesystem.dao.impl;

import java.util.List;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jboss.ejb3.annotation.SecurityDomain;

import cz.muni.fi.pv243.sportleaguesystem.dao.interfaces.SportDAO;
import cz.muni.fi.pv243.sportleaguesystem.entities.Sport;


@SecurityDomain("sport")
@Stateless
public class SportDAOImpl implements SportDAO{

	@Inject
	private EntityManager em;
	
	//@RolesAllowed("ADMIN")
	@Override
	public void create(Sport sport) {
		em.persist(sport);
		
	}

	@Override
	public Sport get(Long id) {
		return em.find(Sport.class, id);
	}
	
	//@RolesAllowed("ADMIN")
	@Override
	public void update(Sport sport) {
		em.merge(sport);		
	}
	
	//@RolesAllowed("ADMIN")
	@Override
	public void delete(Sport sport) {
		em.remove(em.merge(sport));		
	}

	@Override
	public List<Sport> findAll() {
		return (List<Sport>) em.createQuery("SELECT s FROM Sport s").getResultList();		
	}

	@Override
	public List<Sport> findSportsByName(String name) {
		Query query = em.createQuery("SELECT s FROM Sport s WHERE UPPER(name) LIKE UPPER(:name)");
		query.setParameter("name", name + "%");
		return (List<Sport>) query.getResultList();
	}	
}
