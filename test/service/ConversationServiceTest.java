package service;

import dao.ConversationDAO;
import dao.MessageDAO;
import dao.PersonDAO;
import dao.impl.ConversationDAOImpl;
import dao.impl.MessageDAOImpl;
import dao.impl.PersonDAOImpl;
import models.Conversation;
import models.Message;
import models.Person;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import play.test.WithApplication;
import service.impl.ConversationServiceImpl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;

public class ConversationServiceTest extends WithApplication {

    Person p1;
    Person p2;

    Message m1;
    Message m2;
    Message m3;
    Message m4;

    Conversation c1;

    ConversationService conversationService;

    ConversationDAO cdao;
    PersonDAO pdao;
    MessageDAO mdao;

    @Before
    public void setUp() {
        fakeApplication(inMemoryDatabase());

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

        conversationService = new ConversationServiceImpl();
        cdao = new ConversationDAOImpl();
        pdao = new PersonDAOImpl();
        mdao = new MessageDAOImpl();
    }

    @Test
    public void createConversationTest() {
        Conversation c = new Conversation("name");
        assertTrue(conversationService.createConversation(c, p1));
        assertTrue(pdao.getPersonById(p1.getId()).getConversations().contains(cdao.getConversationById(c.getId())));
    }

    @Test
    public void addUserToConversationTest() {
        Person p = new Person("person");
        pdao.addPerson(p);
        assertTrue(conversationService.addUserToConversation(c1, p));
        assertTrue(pdao.getPersonById(p.getId()).getConversations().contains(cdao.getConversationById(c1.getId())));
    }

    @Test
    public void addMessageTest() {
        Message m = new Message("message", p1, new DateTime());
        assertTrue(conversationService.addMessage(c1, m));
        assertTrue(cdao.getConversationById(c1.getId()).getMessages().contains(mdao.getMessageById(m.getId())));
    }

    @Test
    public void leaveConversationTest() {
        assertTrue(conversationService.leaveConversation(c1, p1));
        assertFalse(pdao.getPersonById(p1.getId()).getConversations().contains(cdao.getConversationById(c1.getId())));
    }

    @Test
    public void deleteMessageTest() {
        assertTrue(conversationService.deleteMessage(p1, c1, m1));
        assertTrue(mdao.getMessageById(m1.getId()).getMessageDeleted().contains(pdao.getPersonById(p1.getId())));
    }
}
