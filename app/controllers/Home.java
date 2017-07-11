package controllers;

import models.User;
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

        Form<User> userForm = formGen.form(User.class);
        return ok(welcome.render(userForm, null));
    }

    public Result submit() {

        Form<User> userForm = formGen.form(User.class).bindFromRequest();
        if (userForm.hasErrors()) {
            return badRequest(welcome.render(userForm, validationErrorMsg));
        }
        User user = userForm.get();
        try {
            uploadUserDetailsToS3(user);
        }
        catch (AmazonClientException ae)
        {
            return internalServerError(welcome.render(userForm, serverErrorMsg));
        }
        return ok(success.render(userForm.get()));
    }

    private void uploadUserDetailsToS3(User user) {

        AWSCredentials credentials = new ProfileCredentialsProvider().getCredentials();
        AmazonS3 s3client = new AmazonS3Client(credentials);
        String fileName = String.format("%s-%s-registration.txt", user.getFirstname().toLowerCase(), user.getLastname().toLowerCase());
        ByteArrayInputStream content = new ByteArrayInputStream(createContent(user).getBytes());
        s3client.putObject(new PutObjectRequest(bucketName, fileName, content , new ObjectMetadata()));
    }

    private String createContent(User user) {

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date today = Calendar.getInstance().getTime();
        String nowString = df.format(today);
        return String.format("%s %s accepted t&c on %s", user.getFirstname().toLowerCase(), user.getLastname().toLowerCase(), nowString);
    }

    @Inject
    FormFactory formGen;

    private final String bucketName = "snaithm-testapp1";
    private final String validationErrorMsg = "Correct the errors below to continue.";
    private final String serverErrorMsg = "An error has occurred saving your registration details. The data store could not be reached.";
}