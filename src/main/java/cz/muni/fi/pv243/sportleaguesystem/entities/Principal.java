package cz.muni.fi.pv243.sportleaguesystem.entities;

import java.util.Objects;

import cz.muni.fi.pv243.sportleaguesystem.RolesEnum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
	@Size(min = 1, max = 16)
	@Column(unique = true)
	private String loginName;
	
	@NotNull
    private String password;
    
    @Enumerated(EnumType.STRING)
	private RolesEnum role;
    
    @OneToOne
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
	public RolesEnum getRole() {
		return role;
	}
	public void setRole(RolesEnum role) {
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
