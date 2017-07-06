package controllers;

import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;
import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.BAD_REQUEST;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.GET;
import static play.test.Helpers.POST;
import static play.test.Helpers.route;

public class HomeTests extends WithApplication {

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }

    @Test
    public void Home_GET_ReturnsOK() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/");
        Result result = route(app, request);
        assertEquals(OK, result.status());
    }

    @Test
    public void Home_POST_ReturnsBADREQUEST_NoInput() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(POST)
                .uri("/submit");
        Result result = route(app, request);
        assertEquals(BAD_REQUEST, result.status());
    }
}