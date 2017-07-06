package models;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.Before;
import org.junit.Test;

public class PersonTests {

    private static Validator validator;
    private Person person = new Person();

    @Before
    public void setUp() {
        person.setFirstname("Test first name");
        person.setLastname("Test last name");
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void PersonModel_HappyPath() { //default values used from setup
        List<ConstraintViolation<Person>> vErrors = new ArrayList<ConstraintViolation<Person>>(validator.validate(person));
        assertEquals(0, vErrors.size());
    }

    @Test
    public void PersonModel_SadPath_FirstnameEmptyString() {
        person.setFirstname("");
        List<ConstraintViolation<Person>> vErrors = new ArrayList<ConstraintViolation<Person>>(validator.validate(person));
        assertEquals(1, vErrors.size());
        assertEquals("Please enter a first name(s)", vErrors.get(0).getMessage());
    }

    @Test
    public void PersonModel_SadPath_FirstnameUnderMinLength() {
        person.setFirstname("a");
        List<ConstraintViolation<Person>> vErrors = new ArrayList<ConstraintViolation<Person>>(validator.validate(person));
        assertEquals(1, vErrors.size());
        assertEquals("First name(s) must contain at least 2 characters", vErrors.get(0).getMessage());
    }

    @Test
    public void PersonModel_HappyPath_FirstnameEqualsMinLength() {
        person.setFirstname("aa");
        List<ConstraintViolation<Person>> vErrors = new ArrayList<ConstraintViolation<Person>>(validator.validate(person));
        assertEquals(0, vErrors.size());
    }

    @Test
    public void PersonModel_SadPath_FirstnameOverMaxLength() {
        String firstName = new String(new char[101]).replace('\0', 'a');
        person.setFirstname(firstName);
        List<ConstraintViolation<Person>> vErrors = new ArrayList<ConstraintViolation<Person>>(validator.validate(person));
        assertEquals(1, vErrors.size());
        assertEquals("First name(s) must not exceed 100 characters", vErrors.get(0).getMessage());
    }

    @Test
    public void PersonModel_HappyPath_FirstnameOnMaxLength() {
        String firstName = new String(new char[100]).replace('\0', 'a');
        person.setFirstname(firstName);
        List<ConstraintViolation<Person>> vErrors = new ArrayList<ConstraintViolation<Person>>(validator.validate(person));
        assertEquals(0, vErrors.size());
    }

    @Test
    public void PersonModel_SadPath_LastnameEmptyString() {
        person.setLastname("");
        List<ConstraintViolation<Person>> vErrors = new ArrayList<ConstraintViolation<Person>>(validator.validate(person));
        assertEquals(1, vErrors.size());
        assertEquals("Please enter a last name", vErrors.get(0).getMessage());
    }

    @Test
    public void PersonModel_SadPath_LastnameUnderMinLength() {
        person.setLastname("a");
        List<ConstraintViolation<Person>> vErrors = new ArrayList<ConstraintViolation<Person>>(validator.validate(person));
        assertEquals(1, vErrors.size());
        assertEquals("Last name must contain at least 2 characters", vErrors.get(0).getMessage());
    }

    @Test
    public void PersonModel_HappyPath_LastnameEqualsMinLength() {
        person.setLastname("aa");
        List<ConstraintViolation<Person>> vErrors = new ArrayList<ConstraintViolation<Person>>(validator.validate(person));
        assertEquals(0, vErrors.size());
    }

    @Test
    public void PersonModel_SadPath_LastnameOverMaxLength() {
        String lastName = new String(new char[101]).replace('\0', 'a');
        person.setLastname(lastName);
        List<ConstraintViolation<Person>> vErrors = new ArrayList<ConstraintViolation<Person>>(validator.validate(person));
        assertEquals(1, vErrors.size());
        assertEquals("Last name must not exceed 100 characters", vErrors.get(0).getMessage());
    }

    @Test
    public void PersonModel_HappyPath_LastnameOnMaxLength() {
        String lastName = new String(new char[100]).replace('\0', 'a');
        person.setLastname(lastName);
        List<ConstraintViolation<Person>> vErrors = new ArrayList<ConstraintViolation<Person>>(validator.validate(person));
        assertEquals(0, vErrors.size());
    }
}
