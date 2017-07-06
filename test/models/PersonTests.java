package models;

import static org.junit.Assert.assertEquals;
import java.util.Set;
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
        Set<ConstraintViolation<Person>> violations = validator.validate(person);
        assertEquals(0, violations.size());
    }

    @Test
    public void PersonModel_SadPath_FirstnameEmptyString() {
        person.setFirstname("");
        Set<ConstraintViolation<Person>> violations = validator.validate(person);
        assertEquals(1, violations.size());
        assertEquals("Please enter a first name(s)", violations.iterator().next().getMessage());
    }
}
