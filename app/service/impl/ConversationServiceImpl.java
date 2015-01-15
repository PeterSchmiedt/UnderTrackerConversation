package service.impl;

import dao.ConversationDAO;
import dao.MessageDAO;
import dao.PersonDAO;
import dao.impl.ConversationDAOImpl;
import dao.impl.MessageDAOImpl;
import dao.impl.PersonDAOImpl;
import models.Conversation;
import models.Message;
import models.Person;
import play.Logger;

public class ConversationServiceImpl implements service.ConversationService {

    private final ConversationDAO cdao;
    private final PersonDAO pdao;
    private final MessageDAO mdao;

    public ConversationServiceImpl() {
        cdao = new ConversationDAOImpl();
        pdao = new PersonDAOImpl();
        mdao = new MessageDAOImpl();
    }

    @Override
    public boolean createConversation(Conversation conversation, Person creator) {
        if (cdao.getConversationById(conversation.getId()) != null) {
            Logger.debug("ConversationServiceImpl -> createConversation -> Conversation already exists.");
            return false;
        }

        Person creatorDB = pdao.getPersonById(creator.getId());
        if (creatorDB == null) {
            Logger.debug("ConversationServiceImpl -> createConversation -> Creator Person does not exist.");
            return false;
        }

        creatorDB.getConversations().add(conversation);

        cdao.addConversation(conversation);
        pdao.updatePerson(creatorDB);

        return true;
    }

    @Override
    public boolean addUserToConversation(Conversation conversation, Person personToAdd) {
        Person personToAddDB = pdao.getPersonById(personToAdd.getId());
        if (personToAddDB == null) {
            Logger.debug("ConversationServiceImpl -> addUserToConversation -> Person does not exist");
            return false;
        }

        Conversation conversationDB = cdao.getConversationById(conversation.getId());
        if (conversationDB == null) {
            Logger.debug("ConversationServiceImpl -> addUserToConversation -> Conversation does not exist");
            return false;
        }

        personToAddDB.getConversations().add(conversationDB);

        cdao.updateConversation(conversationDB);
        pdao.updatePerson(personToAddDB);

        return true;
    }

    @Override
    public boolean addMessage(Conversation conversation, Message message) {
        Conversation conversationDB = cdao.getConversationById(conversation.getId());
        if (conversationDB == null) {
            Logger.debug("ConversationServiceImpl -> addMessage -> Conversation does not exist");
            return false;
        }

        conversationDB.getMessages().add(message);

        cdao.updateConversation(conversationDB);

        return true;
    }

    @Override
    public boolean leaveConversation(Conversation conversation, Person person) {
        Conversation conversationDB = cdao.getConversationById(conversation.getId());
        if (conversationDB == null) {
            Logger.debug("ConversationServiceImpl -> leaveConversation -> Conversation does not exist");
            return false;
        }

        Person personDB = pdao.getPersonById(person.getId());
        if (personDB == null) {
            Logger.debug("ConversationServiceImpl -> leaveConversation -> Person does not exist");
            return false;
        }

        //person must be a member of conversation
        if (!personDB.getConversations().contains(conversationDB)) {
            Logger.debug("ConversationServiceImpl -> leaveConversation -> Person does not belong to a conversation");
            return false;
        }

        personDB.getConversations().remove(conversationDB);

        pdao.updatePerson(personDB);

        return true;
    }

    @Override
    public boolean deleteMessage(Person person, Conversation conversation, Message message) {
        Person personDB = pdao.getPersonById(person.getId());
        if (personDB == null) {
            Logger.debug("ConversationServiceImpl -> deleteMessage -> Person does not exist");
            return false;
        }

        Conversation conversationDB = cdao.getConversationById(conversation.getId());
        if (conversationDB == null) {
            Logger.debug("ConversationServiceImpl -> deleteMessage -> Conversation does not exist");
            return false;
        }

        Message messageDB = mdao.getMessageById(message.getId());
        if (messageDB == null) {
            Logger.debug("ConversationServiceImpl -> deleteMessage -> Message does not exist");
            return false;
        }

        //does person belong to conversation?
        if (!personDB.getConversations().contains(conversationDB)) {
            Logger.debug("ConversationServiceImpl -> deleteMessage -> Person does not belong to the conversation");
            return false;
        }

        //does message belong to conversation?
        if (!conversationDB.getMessages().contains(messageDB)) {
            Logger.debug("ConversationServiceImpl -> deleteMessage -> Message does not belong to the conversation");
            return false;
        }

        messageDB.getMessageDeleted().add(personDB);
        mdao.updateMessage(messageDB);

        return true;
    }
}
