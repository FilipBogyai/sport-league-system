package cz.muni.fi.pv243.sportleaguesystem.dao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import cz.muni.fi.pv243.sportleaguesystem.dao.interfaces.PrincipalDAO;
import cz.muni.fi.pv243.sportleaguesystem.entities.Principal;
import cz.muni.fi.pv243.sportleaguesystem.entities.User;

@Stateless
public class PrincipalDAOImpl implements PrincipalDAO{

	@Inject
	private EntityManager em;

	@Override
	public void create(Principal principal) {		
		em.persist(principal);		
	}

	@Override
	public Principal get(String loginName) {
		return em.find(Principal.class, loginName);
	}

	@Override
	public void update(Principal principal) {
		em.merge(principal);
		
	}

	@Override
	public void delete(Principal principal) {
		em.remove(em.merge(principal));
		
	}

	@Override
	public List<Principal> findAll() {
		return (List<Principal>) em.createQuery("SELECT p FROM Principal p").getResultList();		
	}

	@Override
	public Principal findPrincipalByUser(User user) {
		Query query = em.createQuery("SELECT p FROM Principal p WHERE p.user = :User");		
        query.setParameter("User" , user);		
        return (Principal) query.getResultList().get(0);	
	}
	
}