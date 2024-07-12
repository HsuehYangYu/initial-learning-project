package com.demo.person.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.person.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
    
}
