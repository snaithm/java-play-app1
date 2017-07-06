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
}
