package ar.com.gepp.examples.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.com.gepp.examples.entity.Person;
import ar.com.gepp.examples.repository.PersonRepository;
import ar.com.gepp.examples.repository.specificaition.PersonSpecification;
import ar.com.gepp.examples.services.PersonService;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Transactional(rollbackFor = Exception.class)
    public Person save(Person person) {
        return personRepository.save(person);
    }

    @Transactional(readOnly = true)
    public Optional<Person> getById(Long id) {
        return personRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Person> getAll() {
        return personRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<Person> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return personRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Person> getAll(Pageable pageable) {
        return personRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public List<Person> getAllBySurename(String surename) {
        return personRepository.findPersonBySurename(surename);
    }

    @Transactional(readOnly = true)
    public List<Person> getAllGTAgeAndFirstname(Short age, String firstname) {
        return personRepository.findAll(PersonSpecification.gtAge(age).and(PersonSpecification.likeName(firstname)));
    }
}
