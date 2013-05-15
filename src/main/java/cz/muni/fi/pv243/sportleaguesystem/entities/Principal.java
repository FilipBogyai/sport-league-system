package cz.muni.fi.pv243.sportleaguesystem.entities;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

/**
*
* @author Filip Bogyai
*/
@Entity
public class Principal {

	@Id
	@NotNull(message = "Login Name cannot be null")
	@NotEmpty(message = "Login Name cannot be empty")
	@Size(min = 3, max = 32, message = "Login Name must contain between 3 to 32 characters")
	@Column(unique = true)
	private String loginName;
	
	@NotNull(message = "Password cannot be null")
	@NotEmpty(message = "Password cannot be empty")
	@Size(min = 3, max = 32, message = "Password must contain between 3 to 32 characters")
    private String password;
    
	@NotNull(message = "Role cannot be null")
	@NotEmpty(message = "Role cannot be empty")
	@Size(min = 3, max = 32, message = "Role must contain between 3 to 32 characters")
	private String role;
        
    @OneToOne(cascade=CascadeType.ALL)
    @Valid
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
