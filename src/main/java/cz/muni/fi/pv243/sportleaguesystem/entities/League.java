package cz.muni.fi.pv243.sportleaguesystem.entities;

import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.NotEmpty;


/**
 *
 * @author Filip Bogyai
 */
@Entity
public class League {
	
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    
    @NotNull(message="Name cannot be null")
    @NotEmpty(message="Name cannot be empty")
    @Size(min = 2 , max = 30, message="A league's name must contain between 2 and 30 characters")
    private String name;
    
    @NotNull(message="Description cannot be null")
    @NotEmpty(message="Description cannot be empty")
    @Size(min = 2 , max = 120, message="A league's decription must contain between 2 and 120 characters")
    private String description;
    
    @NotNull
    @ManyToOne    
    @Valid
	private Sport sport;
    
    @ManyToMany
    @JoinTable(name = "league_players",
    		   joinColumns={@JoinColumn(name="league_id")},
    		   inverseJoinColumns={@JoinColumn(name="player_id")})
    @LazyCollection(LazyCollectionOption.FALSE)
	private List<User> players;
    
    @OneToMany(mappedBy = "league", cascade= CascadeType.ALL )
    @LazyCollection(LazyCollectionOption.FALSE)
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
	@Override
	public String toString() {
		return "League [id=" + id + ", name=" + name + ", description="
				+ description + ", sport=" + sport + "]";
	}
    
	
    
}
