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
import java.util.List;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public class Home extends Controller {

    public Result index() {

        Form<User> userForm = formGen.form(User.class);
        return ok(welcome.render(userForm, null));
    }

    public Result submit() {

        Form<User> userForm = formGen.form(User.class).bindFromRequest();
        if (userForm.hasErrors()) return badRequest(welcome.render(userForm, validationErrorMsg));

        try {
            AWSCredentials credentials = new ProfileCredentialsProvider().getCredentials();
            AmazonS3 s3client = new AmazonS3Client(credentials);
            if (usernameExists(userForm.get().getUsername(), s3client)) return badRequest(welcome.render(userForm, usernameExistsMsg));
            uploadUserDetailsToS3(userForm.get(), s3client);
            return ok(success.render(userForm.get()));
        }
        catch (AmazonClientException ae)
        {
            return internalServerError(welcome.render(userForm, serverErrorMsg));
        }
    }

    private void uploadUserDetailsToS3(User user, AmazonS3 s3client) {

        String fileName = String.format("%s.txt", user.getUsername());
        ByteArrayInputStream content = new ByteArrayInputStream(createContent(user).getBytes());
        s3client.putObject(new PutObjectRequest(bucketName, fileName, content , new ObjectMetadata()));
    }

    private Boolean usernameExists(String username , AmazonS3 s3client) {
        List<S3ObjectSummary>bucketItems =  s3client.listObjects(bucketName).getObjectSummaries();
        for (S3ObjectSummary item : bucketItems) {
            String itemName = item.getKey().replace(".txt","");
            if (itemName.equals(username)) return true;
        }
        return false;
    }

    private String createContent(User user) {

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date today = Calendar.getInstance().getTime();
        String nowString = df.format(today);
        return String.format("%s %s %s accepted t&c on %s", user.getUsername(), user.getFirstname(), user.getLastname(), nowString);
    }

    @Inject
    FormFactory formGen;

    private final String bucketName = "pf-testapp1";
    private final String validationErrorMsg = "Correct the errors below to continue.";
    private final String serverErrorMsg = "An error has occurred saving your registration details. The data store could not be reached.";
    private final String usernameExistsMsg = "Your chosen username already exists. Please enter a different username";
}