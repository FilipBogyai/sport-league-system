package cz.muni.fi.pv243.sportleaguesystem.entities;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
*
* @author Filip Bogyai
*/
@Entity
public class Principal {

	@Id
	@NotNull
	@Size(min = 1, max = 32)
	@Column(unique = true)
	private String loginName;
	
	@NotNull
	@Size(min = 1, max = 32)
    private String password;
    
	@NotNull
	@Size(min = 1, max = 32)
	private String role;
        
    @OneToOne(cascade=CascadeType.ALL)
	private User user;
    
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
    
	@Override
    public int hashCode() {
        int hash = 0;
        hash += (loginName != null ? loginName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Principal)) {
            return false;
        }
        Principal other = (Principal) object;
        if (this.loginName != null && !Objects.equals(this.loginName, other.loginName)) {
            return false;
        }
        return true;
    }
	
}
