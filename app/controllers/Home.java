package controllers;

import models.Person;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.*;
import javax.inject.Inject;
import views.html.home.*;

public class Home extends Controller {

    public Result index() {

        Form<Person> personForm = formGen.form(Person.class);
        return ok(welcome.render(personForm));
    }

    public Result submit() {

        Form<Person> personForm = formGen.form(Person.class).bindFromRequest();
        if (personForm.hasErrors()) {
            return ok(welcome.render(personForm));
        }

        return ok(success.render(personForm.get()));
    }

    @Inject
    FormFactory formGen;
}