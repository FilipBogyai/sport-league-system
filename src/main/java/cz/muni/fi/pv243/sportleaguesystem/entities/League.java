package cz.muni.fi.pv243.sportleaguesystem.entities;

import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import cz.fi.muni.pa165.hotelbookingmanagerpersistence.entities.Hotel;

/**
 *
 * @author Filip Bogyai
 */
@Entity
public class League {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    
    @NotNull
    private String name;
    
    @NotNull
    private String description;
    
    @ManyToOne
	private Sport sport;
    
    @OneToMany
	private List<User> players;
    
    @OneToMany
	private List<Match> matches;
    
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Sport getSport() {
		return sport;
	}
	public void setSport(Sport sport) {
		this.sport = sport;
	}
	public List<User> getPlayers() {
		return players;
	}
	public void setPlayers(List<User> players) {
		this.players = players;
	}
	public List<Match> getMatches() {
		return matches;
	}
	public void setMatches(List<Match> matches) {
		this.matches = matches;
	}
	
	@Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof League)) {
            return false;
        }
        League other = (League) object;
        if (this.id != null && !Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
	
    
}
