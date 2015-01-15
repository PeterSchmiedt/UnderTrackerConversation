package dao;

import dao.impl.PersonDAOImpl;
import models.Person;
import org.junit.Before;
import org.junit.Test;
import play.test.WithApplication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;

public class PersonDAOTest extends WithApplication {

    PersonDAO pdao;

    @Before
    public void setUp() {
        fakeApplication(inMemoryDatabase());

        pdao = new PersonDAOImpl();
    }


    @Test
    public void add() {
        Person p = new Person("meno");
        pdao.addPerson(p);
        assertEquals(Person.finder.byId(p.getId()).getId(), p.getId());
    }

    @Test
    public void update() {
        Person p = new Person("meno");
        pdao.addPerson(p);
        p.setName("name");
        pdao.updatePerson(p);
        assertEquals(Person.finder.byId(p.getId()).getName(), p.getName());
    }

    @Test
    public void delete() {
        Person p = new Person("meno");
        pdao.addPerson(p);
        pdao.deletePerson(p.getId());
        assertNull(Person.finder.byId(p.getId()));
    }

    @Test
    public void getById() {
        Person p = new Person("meno");
        pdao.addPerson(p);
        assertEquals(pdao.getPersonById(p.getId()).getId(), p.getId());
    }

    @Test
    public void findByNameTest() {
        Person p1 = new Person("meno");
        pdao.addPerson(p1);
        Person person = pdao.findByName(p1.getName());
        Person personDB = Person.finder.byId(p1.getId());
        assertEquals(personDB.getId(), person.getId());
    }
}
