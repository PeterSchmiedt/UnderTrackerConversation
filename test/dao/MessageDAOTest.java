package dao;

import dao.impl.MessageDAOImpl;
import models.Message;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import play.test.WithApplication;

import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;

public class MessageDAOTest extends WithApplication {
    MessageDAO mdao;

    @Before
    public void setUp() {
        fakeApplication(inMemoryDatabase());

        mdao = new MessageDAOImpl();
    }

    @Test
    public void addTest() {
        Message m = new Message("message", null, new DateTime());
        //mdao.addMessage(m);
        //assertEquals(m.getId(), Message.finder.byId(m.getId()).getId());
    }

    @Test
    public void updateTes() {
        Message m = new Message("message", null, new DateTime());
        //mdao.addMessage(m);
        m.setMessage("newmessage");
        //mdao.updateMessage(m);
        //assertEquals(m.getMessage(), Message.finder.byId(m.getId()).getMessage());
    }

    @Test
    public void deleteTest() {
        Message m = new Message("message", null, new DateTime());
        //mdao.addMessage(m);
        //mdao.deleteMessage(m.getId());
        //assertNull(Message.finder.byId(m.getId()));
    }

    @Test
    public void getByIdTest() {
        Message m = new Message("message", null, new DateTime());
        //mdao.addMessage(m);
        //assertEquals(m.getId(), mdao.getMessageById(m.getId()).getId());
    }
}
