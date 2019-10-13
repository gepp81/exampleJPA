package ar.com.gepp.examples.services;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import ar.com.gepp.examples.entity.Person;
import ar.com.gepp.examples.services.configuration.TestConfigurations;

@SpringBootTest(classes = TestConfigurations.class)
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = { "ar.com.gepp.examples.repository" })
@EntityScan(basePackages = "ar.com.gepp.examples.entity")
@ComponentScan(basePackages = "ar.com.gepp.examples.services")
public class PersonServiceTest {

    @Autowired
    private PersonService personService;

    @DisplayName("Save a person")
    @Test
    public void testSavePerson() {
        Person person = generatePerson();
        person.setUsername("jperez");
        person = personService.save(person);
        Assertions.assertNotNull(person.getId());
    }

    private Person generatePerson() {
        Person person = new Person();
        person.setId(null);
        person.setAge(Short.valueOf("25"));
        person.setFirstname("Juan");
        person.setSurename("Perez");
        return person;
    }

    @DisplayName("Save a person with duplicate username")
    @Test
    public void testSavePersonDuplicateUsername() {
        Person person = generatePerson();
        person.setUsername("jperez");
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> personService.save(person));
    }

    @DisplayName("Save a person with duplicate username")
    @Test
    public void testSavePersonWithoutUsername() {
        Person person = generatePerson();
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> personService.save(person));
    }

    @Test
    @DisplayName("Get all with surname perez")
    public void testGetAllBySurename() {
        List<Person> persons = personService.getAllBySurename("Perez");
        Assertions.assertTrue(persons.size() > 4);
    }

    @Test
    @DisplayName("Get all gt Age and like firstname")
    public void testGetAllByFilter() {
        List<Person> persons = personService.getAllGTAgeAndFirstname((short) 40, "iana");
        Assertions.assertTrue(persons.size() > 5);
    }

    @Test
    @DisplayName("Find with page")
    public void testGetAllFirstPage() {
        Page<Person> page = personService.getAll(0, 5);
        List<Person> persons = page.getContent();
        Assertions.assertEquals(persons.size(), 5);
        Person p1 = persons.get(0);
        Person p2 = persons.get(4);

        Assertions.assertEquals(1l, p1.getId());
        Assertions.assertEquals(5l, p2.getId());
        Assertions.assertTrue(page.hasNext());

    }

    @Test
    @DisplayName("Find with another page")
    public void testGetAllAnotherPage() {
        Page<Person> page = personService.getAll(0, 5);
        Assertions.assertTrue(page.hasNext());

        page = personService.getAll(page.nextPageable());
        List<Person> persons = page.getContent();
        
        Person p1 = persons.get(0);
        Person p2 = persons.get(4);
        Assertions.assertEquals(6l, p1.getId());
        Assertions.assertEquals(10l, p2.getId());

    }
}
