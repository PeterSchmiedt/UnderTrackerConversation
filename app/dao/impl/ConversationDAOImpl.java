package dao.impl;

import dao.ConversationDAO;
import models.Conversation;

import java.util.Set;

public class ConversationDAOImpl implements ConversationDAO {
    @Override
    public void addConversation(Conversation conversation) {
        conversation.save();
    }

    @Override
    public void updateConversation(Conversation conversation) {
        conversation.update();
    }

    @Override
    public void deleteConversation(int id) {
        Conversation.finder.byId(id).delete();
    }

    @Override
    public Conversation getConversationById(int id) {
        return Conversation.finder.byId(id);
    }

    @Override
    public Set<Conversation> getAll() {
        return Conversation.finder.findSet();
    }
}
