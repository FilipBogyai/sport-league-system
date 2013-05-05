package cz.muni.fi.pv243.sportleaguesystem.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import cz.muni.fi.pv243.sportleaguesystem.dao.interfaces.PrincipalDAO;
import cz.muni.fi.pv243.sportleaguesystem.entities.Principal;
import cz.muni.fi.pv243.sportleaguesystem.entities.User;
import cz.muni.fi.pv243.sportleaguesystem.service.interfaces.PrincipalService;

@ApplicationScoped
public class PrincipalServiceImpl implements PrincipalService {

	@Inject
	private PrincipalDAO principalDAO;
	
	@Override
	public void create(Principal principal) {
		if (principal == null) {
			throw new IllegalArgumentException("null principal");
		}
		if (principal.getLoginName() == null) {
			throw new IllegalArgumentException("null loginName");
		}
		if (principal.getPassword() == null) {
			throw new IllegalArgumentException("null password");
		}
		principal.setPassword(hashPassword(principal.getPassword()));
		principalDAO.create(principal);
	}

	@Override
	public void update(Principal principal) {
		if (principal == null) {
			throw new IllegalArgumentException("null principal");
		}
		if (principal.getLoginName() == null) {
			throw new IllegalArgumentException("null loginName");
		}
		if (principal.getPassword() == null) {
			throw new IllegalArgumentException("null password");
		}
		if (principalDAO.get(principal.getLoginName()) == null) {
			throw new IllegalArgumentException("nonexistent principal");
		}
		principalDAO.update(principal);

	}

	@Override
	public void delete(Principal principal) {
		if (principal == null) {
			throw new IllegalArgumentException("null principal");
		}
		principalDAO.delete(principal);
	}

	@Override
	public List<Principal> findAll() {
		return principalDAO.findAll();
	}

	@Override
	public Principal findUserByUser(User user) {
		if (user == null) {
			throw new IllegalArgumentException("null user");
		}
		return principalDAO.findPrincipalByUser(user);
	}
	
	@Override
	public Principal findPrincipalByLoginName(String loginName) {
		if (loginName == null || "".equals(loginName.trim())) {
			throw new IllegalArgumentException("loginName cannot be null");
		}
		return principalDAO.get(loginName); 
	}
	
	public String hashPassword(String password){
		
		StringBuffer hexString = new StringBuffer();
		try{
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes());		
			byte byteData[]= md.digest();		
						
	    	for (int i=0;i<byteData.length;i++) {
	    		String hex=Integer.toHexString(0xff & byteData[i]);
	   	     	if(hex.length()==1) hexString.append('0');
	   	     	hexString.append(hex);
	    	}
	    	}catch(NoSuchAlgorithmException ex){
	    		
	    	}    	
    	
		return hexString.toString();
	}

}
