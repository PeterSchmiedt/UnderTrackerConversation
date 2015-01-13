package dao;

import models.Conversation;

import java.util.Set;

public interface ConversationDAO {

    public void addConversation(Conversation conversation);

    public void updateConversation(Conversation conversation);

    public void deleteConversation(int id);

    public Conversation getConversationById(int id);

    public Set<Conversation> getAll();
}
