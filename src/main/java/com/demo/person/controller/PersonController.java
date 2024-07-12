package com.demo.person.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.person.exception.ResourceNotFoundException;
import com.demo.person.model.Person;
import com.demo.person.repository.PersonRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")
public class PersonController {
    
    @Autowired
    private PersonRepository personRepository;

    // get all people info
    @GetMapping("/person")
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    // create person rest api
    @PostMapping("/person")
    // @RequestBody is used to convert the body of HTTP request to the java class object
    public Person creatPerson(@RequestBody Person person) {
        return personRepository.save(person);
    }

    // get person by id rest api
    @GetMapping("/person/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable Long id) {
        Person person = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
        return ResponseEntity.ok(person);
    }

    // update person rest api
    @PutMapping("/person/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable Long id, @RequestBody Person personDetails) {
        Person person = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));

        person.setFirstName(personDetails.getFirstName());
        person.setLastName(personDetails.getLastName());
        person.setAddress(personDetails.getAddress());
        person.setPhoneNumber(personDetails.getPhoneNumber());
        person.setHobby(personDetails.getHobby());

        Person updatedPerson = personRepository.save(person);
        return ResponseEntity.ok(updatedPerson);
    }

    //  delete person rest api
    @DeleteMapping("person/{id}")
    public ResponseEntity<Map<String, Boolean>> deletePerson(@PathVariable Long id) {
        Person person = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));

        personRepository.delete(person);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

}
