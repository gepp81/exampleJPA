package ar.com.gepp.examples.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.com.gepp.examples.dto.entity.PersonDTO;
import ar.com.gepp.examples.entity.Person;
import ar.com.gepp.examples.repository.PersonRepository;
import ar.com.gepp.examples.repository.specificaition.PersonSpecification;
import ar.com.gepp.examples.services.PersonService;

@Service
public class PersonServiceImpl implements PersonService {

    private static final Class<PersonDTO> DESTINATION_CLASS = PersonDTO.class;

    @Autowired
    private PersonRepository personRepository;

    private final static DozerBeanMapper mapper = new DozerBeanMapper();

    @Transactional(rollbackFor = Exception.class)
    public PersonDTO save(PersonDTO person) {
        Person personEntity = mapper.map(person, Person.class);
        personEntity = personRepository.save(personEntity);
        return mapper.map(personEntity, DESTINATION_CLASS);
    }

    @Transactional(readOnly = true)
    public Optional<PersonDTO> getById(Long id) {
        Optional<Person> optional = personRepository.findById(id);
        return optional.isPresent() ? Optional.of(mapper.map(optional, DESTINATION_CLASS)) : Optional.empty();
    }

    @Transactional(readOnly = true)
    public List<PersonDTO> getAll() {
        List<Person> persons = personRepository.findAll();
        return convertToListDTO(persons);
    }

    private List<PersonDTO> convertToListDTO(List<Person> persons) {
        List<PersonDTO> personsDTO = new ArrayList<>(persons.size());
        for (Person person : persons) {
            personsDTO.add(mapper.map(person, DESTINATION_CLASS));
        }
        return personsDTO;
    }

    private Page<PersonDTO> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Person> pagePersons = personRepository.findAll(pageable);
        return new PageImpl<PersonDTO>(convertToListDTO(pagePersons.getContent()), pagePersons.getPageable(),
                pagePersons.getTotalElements());
    }

    @Transactional(readOnly = true)
    public Page<PersonDTO> getAll(Pageable pageable) {
        if (pageable == null)
            return getAll(0, 5);
        Page<Person> pagePersons = personRepository.findAll(pageable);
        return new PageImpl<PersonDTO>(convertToListDTO(pagePersons.getContent()), pagePersons.getPageable(),
                pagePersons.getTotalElements());
    }

    @Transactional(readOnly = true)
    public List<PersonDTO> getAllBySurename(String surename) {
        List<Person> list = personRepository.findPersonsBySurename(surename);
        return convertToListDTO(list);
    }

    @Transactional(readOnly = true)
    public List<PersonDTO> getAllGTAgeAndFirstname(Short age, String firstname) {
        List<Person> list = personRepository
                .findAll(PersonSpecification.gtAge(age).and(PersonSpecification.likeName(firstname)));
        return convertToListDTO(list);
    }

    @Override
    public Optional<PersonDTO> getByUsername(String username) {
        Optional<Person> optional = personRepository.findTop1ByUsername(username);
        return optional.isPresent() ? Optional.of(mapper.map(optional, DESTINATION_CLASS)) : Optional.empty();
    }
}
