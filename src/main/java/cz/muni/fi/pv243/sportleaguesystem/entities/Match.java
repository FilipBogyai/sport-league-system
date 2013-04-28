package cz.muni.fi.pv243.sportleaguesystem.entities;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import cz.fi.muni.pa165.hotelbookingmanagerpersistence.entities.Hotel;

/**
*
* @author Filip Bogyai
*/
@Entity
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    
    @NotNull
    @ManyToOne
	private User player1;
    
    @NotNull
    @ManyToOne
	private User player2;
    
    private String location;
    
    private Date startTime;
    
    private Date endTime;
    
    private int scorePlayer1;
    
    private int scorePlayer2;
    
    private boolean approved;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getPlayer1() {
		return player1;
	}
	public void setPlayer1(User player1) {
		this.player1 = player1;
	}
	public User getPlayer2() {
		return player2;
	}
	public void setPlayer2(User player2) {
		this.player2 = player2;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public int getScorePlayer1() {
		return scorePlayer1;
	}
	public void setScorePlayer1(int scorePlayer1) {
		this.scorePlayer1 = scorePlayer1;
	}
	public int getScorePlayer2() {
		return scorePlayer2;
	}
	public void setScorePlayer2(int scorePlayer2) {
		this.scorePlayer2 = scorePlayer2;
	}
	public boolean isApproved() {
		return approved;
	}
	public void setApproved(boolean approved) {
		this.approved = approved;
	}
    
	@Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Match)) {
            return false;
        }
        Match other = (Match) object;
        if (this.id != null && !Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
}
