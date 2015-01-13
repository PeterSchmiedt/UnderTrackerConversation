package controllers;

import models.Person;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller {

    static final String title = "Conversations";

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

        return ok(index.render(title, Person.finder.byId(1).getName(), Person.finder.byId(1).getConversations()));
    }

}
