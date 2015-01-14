package controllers;

import dao.ConversationDAO;
import dao.PersonDAO;
import dao.impl.ConversationDAOImpl;
import dao.impl.PersonDAOImpl;
import models.Conversation;
import models.Person;
import play.Logger;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import service.ConversationService;
import service.impl.ConversationServiceImpl;
import views.html.create;
import views.html.login;

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
        return
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
