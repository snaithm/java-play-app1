package controllers;

import models.Person;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.*;
import javax.inject.Inject;
import views.html.home.*;
import java.io.ByteArrayInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class Home extends Controller {

    public Result index() {

        Form<Person> personForm = formGen.form(Person.class);
        return ok(welcome.render(personForm, null));
    }

    public Result submit() {

        Form<Person> personForm = formGen.form(Person.class).bindFromRequest();
        if (personForm.hasErrors()) {
            return badRequest(welcome.render(personForm, "Correct the errors below to continue"));
        }
        Person person = personForm.get();
        try {
            uploadUserDetailsToS3(person);
        }
        catch (AmazonClientException ae)
        {
            return internalServerError(welcome.render(personForm, "An error has occurred saving your registration details. The data store could not be reached."));
        }
        return ok(success.render(personForm.get()));
    }

    private void uploadUserDetailsToS3(Person person) {

        AWSCredentials credentials = new ProfileCredentialsProvider().getCredentials();
        AmazonS3 s3client = new AmazonS3Client(credentials);
        String fileName = String.format("%s-%s-registration.txt", person.getFirstname().toLowerCase(), person.getLastname().toLowerCase());
        ByteArrayInputStream content = new ByteArrayInputStream(createContent(person).getBytes());
        s3client.putObject(new PutObjectRequest(bucketName, fileName, content , new ObjectMetadata()));
    }

    private String createContent(Person person) {

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date today = Calendar.getInstance().getTime();
        String nowString = df.format(today);
        return String.format("%s %s accepted t&c on %s", person.getFirstname().toLowerCase(), person.getLastname().toLowerCase(), nowString);
    }

    @Inject
    FormFactory formGen;

    private final String bucketName = "snaithm-testapp1";
}