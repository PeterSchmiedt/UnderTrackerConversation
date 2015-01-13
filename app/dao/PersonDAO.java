package dao;

import models.Person;

import java.util.Set;

public interface PersonDAO {

    public void addPerson(Person person);

    public void updatePerson(Person person);

    public void deletePerson(int id);

    public Person getPersonById(int id);

    public Set<Person> getAll();
}
