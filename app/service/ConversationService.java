package service;

import models.Conversation;
import models.Message;
import models.Person;

public interface ConversationService {

    public boolean createConversation(Conversation conversation, Person creator);

    public boolean addUserToConversation(Conversation conversation, Person personToAdd);

    public boolean addMessage(Conversation conversation, Message message);

    public boolean leaveConversation(Conversation conversation, Person person);

    public boolean deleteMessage(Person person, Message message);
}
