package models;

import play.data.validation.Constraints.*;

public class Person {

    //attributes
    @Required(message = "Please enter a first name(s)")
    @MaxLength(value = 100, message = "First name(s) must not exceed 100 characters")
    @MinLength(value = 2, message = "First name(s) must contain at least 2 characters")
    private String firstname;

    @Required(message = "Please enter a last name")
    @MaxLength(value = 100, message = "Last name must not exceed 100 characters")
    @MinLength(value = 2, message = "Last name must contain at least 2 characters")
    private String lastname;

    //methods
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String toString() {
        return "First name: " + this.getFirstname() + " Last name: " + this.getLastname();
    }

    //constructors
    public Person() {
    } //used for creating a blank form

    public Person(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }
}