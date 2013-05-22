package cz.muni.fi.pv243.sportleaguesystem.entities;

/**
 * @author Marian Rusnak
 */
public class PlayerResult {
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
}