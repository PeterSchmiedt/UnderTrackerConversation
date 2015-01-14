package service;

import models.Conversation;
import models.Person;

public interface PersonService {

    public boolean leaveConversation(Conversation conversation, Person person);
}
