package ar.com.gepp.examples.repository.specificaition;

import org.springframework.data.jpa.domain.Specification;

import ar.com.gepp.examples.entity.Person;

public interface PersonSpecification {

    public static Specification<Person> likeName(String name) {
        return (person, cq, cb) -> cb.like(person.get("firstname"), String.format("%%%s%%", name));
    }

    public static Specification<Person> gtAge(Short age) {
        return (person, cq, cb) -> cb.gt(person.get("age"), age);
    }

}
