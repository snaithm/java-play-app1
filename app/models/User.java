package models;

import play.data.validation.Constraints.*;

public class User {

    //attributes
    @Required(message = "Please enter a username")
    @MaxLength(value = 10, message = "Username must not exceed 10 characters")
    @MinLength(value = 2, message = "Username must contain at least 2 characters")
    private String username;

    @Required(message = "Please enter a first name(s)")
    @MaxLength(value = 100, message = "First name(s) must not exceed 100 characters")
    @MinLength(value = 2, message = "First name(s) must contain at least 2 characters")
    private String firstname;

    @Required(message = "Please enter a last name")
    @MaxLength(value = 100, message = "Last name must not exceed 100 characters")
    @MinLength(value = 2, message = "Last name must contain at least 2 characters")
    private String lastname;

    //methods
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username.toLowerCase().trim();
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname.toLowerCase().trim();
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname.toLowerCase().trim();
    }

    @Override
    public String toString() {
        return this.getFirstname() + " " + this.getLastname() + " username: " + this.getUsername();
    }

    //constructors
    public User() {
    } //used for creating a blank form

    public User(String firstname, String lastname, String username) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
    }
}