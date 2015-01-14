package dao.impl;

import dao.PersonDAO;
import models.Person;

import java.util.Set;

public class PersonDAOImpl implements PersonDAO {
    @Override
    public void addPerson(Person person) {
        person.save();
    }

    @Override
    public void updatePerson(Person person) {
        person.update();
    }

    @Override
    public void deletePerson(int id) {
        Person.finder.byId(id).delete();
    }

    @Override
    public Person getPersonById(int id) {
        return Person.finder.byId(id);
    }

    @Override
    public Set<Person> getAll() {
        return Person.finder.findSet();
    }

    @Override
    public Person findByName(String name) {
        return Person.finder.where().eq("name", name).findUnique();
    }
}
