package cz.muni.fi.pv243.sportleaguesystem.entities;

import java.util.Date;

/**
 *
 * @author Marian Rusnak
 */
public class Match {
    private Long id;
    private User player1;
    private User player2;
    private String location;
    private Date startTime;
    private Date endTime;
    private int scorePlayer1;
    private int scorePlayer2;
    private boolean approved;
}
