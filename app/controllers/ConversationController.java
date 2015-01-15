package controllers;

import dao.ConversationDAO;
import dao.PersonDAO;
import dao.impl.ConversationDAOImpl;
import dao.impl.PersonDAOImpl;
import models.Conversation;
import models.Message;
import models.Person;
import org.joda.time.DateTime;
import play.Logger;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import service.ConversationService;
import service.impl.ConversationServiceImpl;
import views.html.add;
import views.html.create;
import views.html.detail;
import views.html.login;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ConversationController extends Controller {

    static final String title = "UnderTrackerConversations";
    static PersonDAO pdao = new PersonDAOImpl();
    static ConversationDAO cdao = new ConversationDAOImpl();
    static ConversationService conversationService = new ConversationServiceImpl();

    public static Result leaveConversation(int id) {

        Person person = pdao.findByName(session().get("username"));
        if (person == null) {
            return redirect(routes.Application.index());
        }

        Conversation conversation = cdao.getConversationById(id);
        if (conversation == null) {
            return redirect(routes.Application.index());
        }

        conversationService.leaveConversation(conversation, person);

        return redirect(routes.Application.index());
    }

    public static Result createConversation() {
        return ok(create.render(title, session("username")));
    }

    public static Result doCreateConversation() {
        Form<ConversationForm> conversationForm = Form.form(ConversationForm.class).bindFromRequest(new String[0]);
        if (conversationForm.hasErrors()) {
            return badRequest(login.render(title, session().get("username")));
        }

        Person person = pdao.findByName(session().get("username"));
        if (person == null) {
            Logger.debug("ConversationController -> doCreateConversation -> Person not found.");
            return redirect(routes.Application.index());
        }

        Conversation conversation = new Conversation(conversationForm.get().getName());

        if (!conversationService.createConversation(conversation, person)) {
            Logger.debug("ConversationController -> doCreateConversation -> Conversation could not be created.");
        }

        return redirect(routes.Application.index());
    }

    public static Result conversationDetail(int id) {
        Conversation conversation = cdao.getConversationById(id);
        if (conversation == null) {
            return redirect(routes.Application.index());
        }

        List<Message> messages = conversation.getMessages();
        messages.sort(new Comparator<Message>() {
            @Override
            public int compare(Message o1, Message o2) {
                if (o1.getDate().isAfter(o2.getDate())) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });

        Person currentPerson = pdao.findByName(session().get("username"));
        if (currentPerson == null) {
            Logger.debug("ConversationController -> conversationDetail -> Person not found.");
        }

        Set<Person> persons = Person.finder.where().eq("conversations.id", conversation.getId()).findSet();

        return ok(detail.render(title, session().get("username"), conversation, messages, persons, currentPerson));
    }

    public static Result addMessage(int id) {
        Form<AddMessageForm> addMessageForm = Form.form(AddMessageForm.class).bindFromRequest(new String[0]);
        if (addMessageForm.hasErrors()) {
            return badRequest(login.render(title, session().get("username")));
        }

        Conversation conversation = cdao.getConversationById(id);
        if (conversation == null) {
            Logger.debug("ConversationController -> addMessage -> Conversation not found.");
            return redirect(routes.Application.index());
        }

        Person person = pdao.findByName(session().get("username"));
        if (person == null) {
            Logger.debug("ConversationController -> addMessage -> Person not found.");
            return redirect(routes.Application.index());
        }

        if (addMessageForm.get().getMessage() == null || addMessageForm.get().getMessage().equals("")) {
            Logger.debug("ConversationController -> addMessage -> Empty message.");
            return redirect(routes.ConversationController.conversationDetail(id));
        }

        Message message = new Message(addMessageForm.get().getMessage(), person, new DateTime());

        if (!conversationService.addMessage(conversation, message)) {
            Logger.debug("ConversationController -> addMessage -> Message could not be added.");

        }

        return redirect(routes.ConversationController.conversationDetail(id));
    }

    public static Result addUser(int id) {
        Conversation conversation = cdao.getConversationById(id);
        if (conversation == null) {
            Logger.debug("ConversationController -> addUser -> Conversation not found.");
            return redirect(routes.Application.index());
        }

        Set<Person> personSet = new HashSet<>();
        //TODO treba upravit
        //Set<Person> persons = Person.finder.where().ne("conversations.id", conversation.getId()).findSet();
        for (Person p : pdao.getAll()) {
            if (!p.getConversations().contains(conversation)) {
                personSet.add(p);
            }
        }

        return ok(add.render(title, session().get("username"), conversation, personSet));
    }

    public static Result doAddUser(int id) {
        Form<AddPersonForm> addPersonForm = Form.form(AddPersonForm.class).bindFromRequest(new String[0]);
        if (addPersonForm.hasErrors()) {
            return badRequest(login.render(title, session().get("username")));
        }

        Conversation conversation = cdao.getConversationById(id);
        if (conversation == null) {
            Logger.debug("ConversationController -> doAddUser -> Conversation not found.");
            return redirect(routes.ConversationController.conversationDetail(id));
        }

        Person person = pdao.getPersonById(addPersonForm.get().getPerson());
        if (person == null) {
            Logger.debug("ConversationController -> doAddUser -> Person not found.");
            return redirect(routes.ConversationController.conversationDetail(id));
        }

        if (!conversationService.addUserToConversation(conversation, person)) {
            Logger.debug("ConversationController -> doAddUser -> COULD NOT ADD PERSON TO CONVERSATION");
        }

        return redirect(routes.ConversationController.conversationDetail(id));
    }

    //------------------------------------------------------------------------------------------------------------------

    public static class AddPersonForm {
        public int person;

        public String validate() {
            return null;
        }

        public int getPerson() {
            return person;
        }

        public void setPerson(int person) {
            this.person = person;
        }
    }

    public static class AddMessageForm {
        public String message;

        public String validate() {
            return null;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public static class ConversationForm {
        public String name;

        public String validate() {
            return null;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String paramString) {
            this.name = paramString;
        }
    }
}
