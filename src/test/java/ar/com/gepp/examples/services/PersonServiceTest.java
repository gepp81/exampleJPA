package ar.com.gepp.examples.services;

import java.util.List;
import java.util.Optional;

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

import ar.com.gepp.examples.dto.entity.PersonDTO;
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
        PersonDTO person = generatePerson();
        person.setUsername("jperez");
        person = personService.save(person);
        Assertions.assertNotNull(person);
    }

    private PersonDTO generatePerson() {
        PersonDTO person = new PersonDTO();
        person.setAge(Short.valueOf("25"));
        person.setFirstname("Juan");
        person.setSurename("Perez");
        return person;
    }

    @DisplayName("Get by Id")
    @Test
    public void getById() {
        Optional<PersonDTO> person = personService.getById(1l);
        Assertions.assertNotNull(person.get());
    }

    @DisplayName("Get by Id no exists")
    @Test
    public void getByIdNotExist() {
        Optional<PersonDTO> person = personService.getById(-1l);
        Assertions.assertFalse(person.isPresent());
    }

    @DisplayName("Get by username no exists")
    @Test
    public void getByUsernameNotExist() {
        Optional<PersonDTO> person = personService.getByUsername("-1");
        Assertions.assertFalse(person.isPresent());
    }

    @DisplayName("Get all")
    @Test
    public void getAll() {
        List<PersonDTO> persons = personService.getAll();
        Assertions.assertFalse(persons.isEmpty());
    }

    @DisplayName("Save a person with duplicate username")
    @Test
    public void testSavePersonDuplicateUsername() {
        PersonDTO person = generatePerson();
        person.setUsername("jperez");
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> personService.save(person));
    }

    @DisplayName("Save a person with duplicate username")
    @Test
    public void testSavePersonWithoutUsername() {
        PersonDTO person = generatePerson();
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> personService.save(person));
    }

    @Test
    @DisplayName("Get all with surname perez")
    public void testGetAllBySurename() {
        List<PersonDTO> persons = personService.getAllBySurename("Perez");
        Assertions.assertTrue(persons.size() > 4);
    }

    @Test
    @DisplayName("Get all gt Age and like firstname")
    public void testGetAllByFilter() {
        List<PersonDTO> persons = personService.getAllGTAgeAndFirstname((short) 40, "iana");
        Assertions.assertTrue(persons.size() > 5);
    }

    @Test
    @DisplayName("Find with page")
    public void testGetAllFirstPage() {
        Page<PersonDTO> page = personService.getAll(null);
        List<PersonDTO> persons = page.getContent();
        Assertions.assertEquals(persons.size(), 5);
        PersonDTO p1 = persons.get(0);
        PersonDTO p2 = persons.get(4);

        Assertions.assertEquals("peperez", p1.getUsername());
        Assertions.assertEquals("hperez", p2.getUsername());
        Assertions.assertTrue(page.hasNext());
    }

    @Test
    @DisplayName("Find with another page")
    public void testGetAllAnotherPage() {
        Page<PersonDTO> page = personService.getAll(null);
        Assertions.assertTrue(page.hasNext());

        page = personService.getAll(page.nextPageable());
        List<PersonDTO> persons = page.getContent();

        PersonDTO p1 = persons.get(0);
        PersonDTO p2 = persons.get(4);
        Assertions.assertEquals("pelopez", p1.getUsername());
        Assertions.assertEquals("hclopez", p2.getUsername());
    }

    @Test
    @DisplayName("Find by username")
    public void getPersonByUsername() {
        Optional<PersonDTO> person = personService.getByUsername("pelopez");
        Assertions.assertTrue(person.isPresent());
    }
}
