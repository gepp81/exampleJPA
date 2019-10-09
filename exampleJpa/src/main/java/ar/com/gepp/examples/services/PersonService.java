package ar.com.gepp.examples.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ar.com.gepp.examples.entity.Person;

public interface PersonService {

    Person save(Person person);

    Optional<Person> getById(Long id);

    Page<Person> getAll(Pageable page);

    List<Person> getAllBySurename(String surename);

    List<Person> getAllGTAgeAndFirstname(Short age, String firstname);
}
