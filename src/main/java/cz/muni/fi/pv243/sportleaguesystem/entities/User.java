package cz.muni.fi.pv243.sportleaguesystem.entities;

import java.util.Objects;

import cz.fi.muni.pa165.hotelbookingmanagerpersistence.entities.Hotel;
import cz.muni.fi.pv243.sportleaguesystem.RolesEnum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
*
* @author Filip Bogyai
*/
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
    
    @NotNull
    @Size(min = 1, max = 16)
    @Pattern(regexp = "[A-Za-z ]*", message = "must contain only letters and spaces")
    private String firstName;
    
    @NotNull
    @Size(min = 1, max = 16)
    @Pattern(regexp = "[A-Za-z ]*", message = "must contain only letters and spaces")
    private String lastName;
    
    @NotNull
    @Size(min = 1, max = 12)
    @Digits(fraction = 0, integer = 12)
    @Column(name = "phone_number")
    private String phoneNumber;
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if (this.id != null && !Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
