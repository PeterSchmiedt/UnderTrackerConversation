package dao;

import models.Message;

import java.util.List;

public interface MessageDAO {

    public void addMessage(Message message);

    public void updateMessage(Message message);

    public void deleteMessage(int id);

    public void getMessageById(int id);

    public List<Message> getAll();
}
