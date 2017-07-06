package models;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.Before;
import org.junit.Test;

public class PersonTests {

    private static Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    @Test
    public void PersonModel_HappyPath() {
        Person person = new Person();
        person.setFirstname("Test first name");
        person.setLastname("Test last name");
        Set<ConstraintViolation<Person>> violations = validator.validate(person);
        assertTrue(violations.isEmpty());
    }
}