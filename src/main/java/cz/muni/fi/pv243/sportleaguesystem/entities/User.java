package cz.muni.fi.pv243.sportleaguesystem.entities;

import cz.muni.fi.pv243.sportleaguesystem.RolesEnum;

/**
 *
 * @author Marian Rusnak
 */
public class User {
    private Long id;
    private String loginName;
    private String password;
    private RolesEnum role;
    private String firstName;
    private String lastName;
    private String phoneNumber;
}
