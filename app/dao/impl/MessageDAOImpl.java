package dao.impl;

import dao.MessageDAO;
import models.Message;

import java.util.List;

public class MessageDAOImpl implements MessageDAO {
    @Override
    public void addMessage(Message message) {
        message.save();
    }

    @Override
    public void updateMessage(Message message) {
        message.update();
    }

    @Override
    public void deleteMessage(int id) {
        Message.finder.byId(id).delete();
    }

    @Override
    public Message getMessageById(int id) {
        return Message.finder.byId(id);
    }

    @Override
    public List<Message> getAll() {
        return Message.finder.all();
    }
}
