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

public class UserTests {

    private static Validator validator;
    private User user = new User();

    @Before
    public void setUp() {
        user.setUsername("username");
        user.setFirstname("Test first name");
        user.setLastname("Test last name");
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void UserModel_HappyPath() { //default values used from setup
        List<ConstraintViolation<User>> vErrors = new ArrayList<ConstraintViolation<User>>(validator.validate(user));
        assertEquals(0, vErrors.size());
    }

    @Test
    public void UserModel_SadPath_UsernameEmptyString() {
        user.setUsername("");
        List<ConstraintViolation<User>> vErrors = new ArrayList<ConstraintViolation<User>>(validator.validate(user));
        assertEquals(1, vErrors.size());
        assertEquals("Please enter a username", vErrors.get(0).getMessage());
    }

    @Test
    public void UserModel_SadPath_UsernameUnderMinLength() {
        user.setUsername("a");
        List<ConstraintViolation<User>> vErrors = new ArrayList<ConstraintViolation<User>>(validator.validate(user));
        assertEquals(1, vErrors.size());
        assertEquals("Username must contain at least 2 characters", vErrors.get(0).getMessage());
    }

    @Test
    public void UserModel_HappyPath_UsernameEqualsMinLength() {
        user.setUsername("aa");
        List<ConstraintViolation<User>> vErrors = new ArrayList<ConstraintViolation<User>>(validator.validate(user));
        assertEquals(0, vErrors.size());
    }

    @Test
    public void UserModel_SadPath_UsernameOverMaxLength() {
        String username = new String(new char[11]).replace('\0', 'a');
        user.setUsername(username);
        List<ConstraintViolation<User>> vErrors = new ArrayList<ConstraintViolation<User>>(validator.validate(user));
        assertEquals(1, vErrors.size());
        assertEquals("Username must not exceed 10 characters", vErrors.get(0).getMessage());
    }

    @Test
    public void UserModel_HappyPath_UsernameOnMaxLength() {
        String username = new String(new char[10]).replace('\0', 'a');
        user.setUsername(username);
        List<ConstraintViolation<User>> vErrors = new ArrayList<ConstraintViolation<User>>(validator.validate(user));
        assertEquals(0, vErrors.size());
    }

    @Test
    public void UserModel_SadPath_FirstnameEmptyString() {
        user.setFirstname("");
        List<ConstraintViolation<User>> vErrors = new ArrayList<ConstraintViolation<User>>(validator.validate(user));
        assertEquals(1, vErrors.size());
        assertEquals("Please enter a first name(s)", vErrors.get(0).getMessage());
    }

    @Test
    public void UserModel_SadPath_FirstnameUnderMinLength() {
        user.setFirstname("a");
        List<ConstraintViolation<User>> vErrors = new ArrayList<ConstraintViolation<User>>(validator.validate(user));
        assertEquals(1, vErrors.size());
        assertEquals("First name(s) must contain at least 2 characters", vErrors.get(0).getMessage());
    }

    @Test
    public void UserModel_HappyPath_FirstnameEqualsMinLength() {
        user.setFirstname("aa");
        List<ConstraintViolation<User>> vErrors = new ArrayList<ConstraintViolation<User>>(validator.validate(user));
        assertEquals(0, vErrors.size());
    }

    @Test
    public void UserModel_SadPath_FirstnameOverMaxLength() {
        String firstName = new String(new char[101]).replace('\0', 'a');
        user.setFirstname(firstName);
        List<ConstraintViolation<User>> vErrors = new ArrayList<ConstraintViolation<User>>(validator.validate(user));
        assertEquals(1, vErrors.size());
        assertEquals("First name(s) must not exceed 100 characters", vErrors.get(0).getMessage());
    }

    @Test
    public void UserModel_HappyPath_FirstnameOnMaxLength() {
        String firstName = new String(new char[100]).replace('\0', 'a');
        user.setFirstname(firstName);
        List<ConstraintViolation<User>> vErrors = new ArrayList<ConstraintViolation<User>>(validator.validate(user));
        assertEquals(0, vErrors.size());
    }

    @Test
    public void UserModel_SadPath_LastnameEmptyString() {
        user.setLastname("");
        List<ConstraintViolation<User>> vErrors = new ArrayList<ConstraintViolation<User>>(validator.validate(user));
        assertEquals(1, vErrors.size());
        assertEquals("Please enter a last name", vErrors.get(0).getMessage());
    }

    @Test
    public void UserModel_SadPath_LastnameUnderMinLength() {
        user.setLastname("a");
        List<ConstraintViolation<User>> vErrors = new ArrayList<ConstraintViolation<User>>(validator.validate(user));
        assertEquals(1, vErrors.size());
        assertEquals("Last name must contain at least 2 characters", vErrors.get(0).getMessage());
    }

    @Test
    public void UserModel_HappyPath_LastnameEqualsMinLength() {
        user.setLastname("aa");
        List<ConstraintViolation<User>> vErrors = new ArrayList<ConstraintViolation<User>>(validator.validate(user));
        assertEquals(0, vErrors.size());
    }

    @Test
    public void UserModel_SadPath_LastnameOverMaxLength() {
        String lastName = new String(new char[101]).replace('\0', 'a');
        user.setLastname(lastName);
        List<ConstraintViolation<User>> vErrors = new ArrayList<ConstraintViolation<User>>(validator.validate(user));
        assertEquals(1, vErrors.size());
        assertEquals("Last name must not exceed 100 characters", vErrors.get(0).getMessage());
    }

    @Test
    public void UserModel_HappyPath_LastnameOnMaxLength() {
        String lastName = new String(new char[100]).replace('\0', 'a');
        user.setLastname(lastName);
        List<ConstraintViolation<User>> vErrors = new ArrayList<ConstraintViolation<User>>(validator.validate(user));
        assertEquals(0, vErrors.size());
    }
}