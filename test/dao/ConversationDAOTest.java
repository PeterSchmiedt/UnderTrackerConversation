package dao;

import dao.impl.ConversationDAOImpl;
import models.Conversation;
import org.junit.Before;
import org.junit.Test;
import play.test.WithApplication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;

public class ConversationDAOTest extends WithApplication {

    ConversationDAO cdao;

    @Before
    public void setUp() {
        fakeApplication(inMemoryDatabase());

        cdao = new ConversationDAOImpl();
    }

    @Test
    public void addTest() {
        Conversation c = new Conversation("conversation");
        cdao.addConversation(c);
        assertEquals(c.getId(), Conversation.finder.byId(c.getId()).getId());
    }

    @Test
    public void updateTest() {
        Conversation c = new Conversation("conversation");
        cdao.addConversation(c);
        c.setName("newname");
        cdao.updateConversation(c);
        assertEquals(c.getName(), Conversation.finder.byId(c.getId()).getName());
    }

    @Test
    public void deleteTest() {
        Conversation c = new Conversation("conversation");
        cdao.addConversation(c);
        cdao.deleteConversation(c.getId());
        assertNull(Conversation.finder.byId(c.getId()));
    }

    @Test
    public void getByIdTest() {
        Conversation c = new Conversation("conversation");
        cdao.addConversation(c);
        assertEquals(c.getId(), cdao.getConversationById(c.getId()).getId());
    }
}
