package cz.muni.fi.pv243.sportleaguesystem.entities;

import java.util.List;

/**
 *
 * @author Marian Rusnak
 */
public class League {
    private Long id;
    private String name;
    private String description;
    private Sport sport;
    private List<User> players;
    private List<Match> matches;
}
