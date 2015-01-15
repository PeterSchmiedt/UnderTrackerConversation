package controllers;

import dao.PersonDAO;
import dao.impl.PersonDAOImpl;
import models.Person;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import views.html.login;

public class Application extends Controller {

    private static final String title = "UnderTrackerConversations";
    private static final PersonDAO pdao = new PersonDAOImpl();

    public static Result index() {
        /*
        Person p1 = new Person("person1");
        Person p2 = new Person("person2");
        Message m1 = new Message("message1", p1, new DateTime());
        Message m2 = new Message("message2", p1, new DateTime());
        Message m3 = new Message("message3", p2, new DateTime());
        Message m4 = new Message("message4", p2, new DateTime());
        Conversation c1 = new Conversation("conversation1");


        c1.getMessages().add(m1);
        c1.getMessages().add(m2);
        c1.getMessages().add(m3);
        c1.getMessages().add(m4);

        p1.getConversations().add(c1);
        p2.getConversations().add(c1);

        p1.save();
        p2.save();
        */

        if (session().containsKey("username")) {

            Person person = pdao.findByName(session().get("username"));

            if (person == null) {
                Person p = new Person(session().get("username"));
                pdao.addPerson(p);
                person = pdao.getPersonById(p.getId());
            }

            return ok(index.render(title, person.getName(), person.getConversations()));

        } else {
            return ok(index.render(title, "Guest User", null));
        }
    }

    public static Result login() {
        return ok(login.render(title, session("username")));
    }

    public static Result doLogin() {
        Form<Login> loginForm = Form.form(Login.class).bindFromRequest(new String[0]);
        if (loginForm.hasErrors()) {
            return badRequest(login.render(title, session().get("username")));
        }

        session().clear();
        session("username", loginForm.get().getName());

        return redirect(routes.Application.index());
    }

    public static Result logout() {
        session().clear();
        flash("success", "You've been logged out");
        return redirect(routes.Application.index());
    }

    public static class Login {
        public String name;

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
