package models;


import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Message extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String message;

    //TODO if hibernate version changes you need to recompile
    @Column
    @Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
    private DateTime date;

    @ManyToOne(cascade = CascadeType.ALL)
    private Person author;

    //TODO i need to have a set of persons to which the message wont show (because it has been deleted)
    private Set<Person> messageDeleted;

    public static final Finder<Integer, Message> finder = new Finder<Integer, Message>(Integer.class, Message.class);


    public Message(String message, Person author, DateTime date) {
        this.message = message;
        this.author = author;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public Person getAuthor() {
        return author;
    }

    public void setAuthor(Person author) {
        this.author = author;
    }
}
