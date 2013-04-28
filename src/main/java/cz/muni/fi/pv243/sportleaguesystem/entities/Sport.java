package cz.muni.fi.pv243.sportleaguesystem.entities;

import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import cz.fi.muni.pa165.hotelbookingmanagerpersistence.entities.Hotel;

/**
*
* @author Filip Bogyai
*/
@Entity
public class Sport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    
    @NotNull
    private String name;
    
    @OneToMany(mappedBy = "sport")
	private List<League> leagues;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<League> getLeagues() {
		return leagues;
	}
	public void setLeagues(List<League> leagues) {
		this.leagues = leagues;
	}
    
	@Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Sport)) {
            return false;
        }
        Sport other = (Sport) object;
        if (this.id != null && !Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
}
