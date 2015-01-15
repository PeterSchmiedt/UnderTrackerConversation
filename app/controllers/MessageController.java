package controllers;

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
import play.mvc.Controller;
import play.mvc.Result;
import service.ConversationService;
import service.impl.ConversationServiceImpl;

public class MessageController extends Controller {

    static PersonDAO pdao = new PersonDAOImpl();
    static MessageDAO mdao = new MessageDAOImpl();
    static ConversationDAO cdao = new ConversationDAOImpl();
    static ConversationService conversationService = new ConversationServiceImpl();

    public static Result deleteMessage(int id, int messageId) {
        Conversation conversation = cdao.getConversationById(id);
        if (conversation == null) {
            Logger.debug("MessageController -> deleteMessage -> Conversation not found.");
            return redirect(routes.ConversationController.conversationDetail(id));
        }

        Person person = pdao.findByName(session("username"));
        if (person == null) {
            Logger.debug("MessageController -> deleteMessage -> Person not found.");
            return redirect(routes.ConversationController.conversationDetail(id));
        }

        Message message = mdao.getMessageById(messageId);
        if (message == null) {
            Logger.debug("MessageController -> deleteMessage -> Message not found.");
            return redirect(routes.ConversationController.conversationDetail(id));
        }

        if (!conversationService.deleteMessage(person, conversation, message)) {
            Logger.debug("MessageController -> deleteMessage -> Could not delete message");
            return redirect(routes.ConversationController.conversationDetail(id));
        }

        return redirect(routes.ConversationController.conversationDetail(id));
    }
}
