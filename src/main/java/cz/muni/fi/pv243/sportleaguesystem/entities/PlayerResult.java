package cz.muni.fi.pv243.sportleaguesystem.entities;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Marian Rusnak
 */
@XmlRootElement
public class PlayerResult implements Comparable<PlayerResult> {
    private User user;
    private int points;
    private int wonCount;
    private int drawnCount;
    private int lostCount;
    private int playerScore;
    private int opponentsScore;

    public User getUser() {
        return user;
    }

    public PlayerResult() {
    	super();
    }

    public PlayerResult(User user, int points, int wonCount, int drawnCount,
                        int lostCount, int playerScore, int opponentsScore) {
        super();
        this.user = user;
        this.points = points;
        this.wonCount = wonCount;
        this.drawnCount = drawnCount;
        this.lostCount = lostCount;
        this.playerScore = playerScore;
        this.opponentsScore = opponentsScore;
    }


    public void setUser(User user) {
        this.user = user;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getWonCount() {
        return wonCount;
    }

    public void setWonCount(int wonCount) {
        this.wonCount = wonCount;
    }

    public int getDrawnCount() {
        return drawnCount;
    }

    public void setDrawnCount(int drawnCount) {
        this.drawnCount = drawnCount;
    }

    public int getLostCount() {
        return lostCount;
    }

    public void setLostCount(int lostCount) {
        this.lostCount = lostCount;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }

    public int getOpponentsScore() {
        return opponentsScore;
    }

    public void setOpponentsScore(int opponentsScore) {
        this.opponentsScore = opponentsScore;
    }

    @Override
    public int compareTo(PlayerResult o) {
        return (o.getPoints() - points);
    }


    @Override
    public String toString() {
        return "PlayerResult [user=" + user + ", points=" + points
                + ", wonCount=" + wonCount + ", drawnCount=" + drawnCount
                + ", lostCount=" + lostCount + ", playerScore=" + playerScore
                + ", opponentsScore=" + opponentsScore + "]";
    }

}