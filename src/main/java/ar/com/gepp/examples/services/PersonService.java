package ar.com.gepp.examples.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ar.com.gepp.examples.dto.entity.PersonDTO;

public interface PersonService {

    PersonDTO save(PersonDTO person);

    Optional<PersonDTO> getById(Long id);

    Optional<PersonDTO> getByUsername(String username);

    List<PersonDTO> getAll();

    Page<PersonDTO> getAll(Pageable pageable);

    List<PersonDTO> getAllBySurename(String surename);

    List<PersonDTO> getAllGTAgeAndFirstname(Short age, String firstname);
}
