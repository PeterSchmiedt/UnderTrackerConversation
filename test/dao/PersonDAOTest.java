package dao;

import dao.impl.PersonDAOImpl;
import models.Conversation;
import models.Message;
import models.Person;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import play.test.WithApplication;

import static org.junit.Assert.assertEquals;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;

public class PersonDAOTest extends WithApplication {

    Person p1;
    Person p2;

    Message m1;
    Message m2;
    Message m3;
    Message m4;

    Conversation c1;

    PersonDAO pdao;


    @Before
    public void setUp() {
        fakeApplication(inMemoryDatabase());

        pdao = new PersonDAOImpl();

        p1 = new Person("person1");
        p2 = new Person("person2");
        m1 = new Message("message1", p1, new DateTime());
        m2 = new Message("message2", p1, new DateTime());
        m3 = new Message("message3", p2, new DateTime());
        m4 = new Message("message4", p2, new DateTime());
        c1 = new Conversation("conversation1");


        c1.getMessages().add(m1);
        c1.getMessages().add(m2);
        c1.getMessages().add(m3);
        c1.getMessages().add(m4);

        p1.getConversations().add(c1);
        p2.getConversations().add(c1);

        p1.save();
        p2.save();
    }

    @Test
    public void findByNameTest() {
        Person person = pdao.findByName(p1.getName());

        Person personDB = Person.finder.byId(p1.getId());

        assertEquals(personDB.getId(), person.getId());
    }
}
