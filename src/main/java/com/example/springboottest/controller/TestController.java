package com.example.springboottest.controller;

import com.example.springboottest.entity.TddEntity;
import com.example.springboottest.repository.TddEntityRepository;
import com.example.springboottest.service.TddTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/tdd", produces = MediaType.APPLICATION_JSON_VALUE) // produces = Response Type
public class TestController {

    @Autowired
    private TddTestService service;

    @GetMapping("/basicTest")
    public String basicTest(String name) {
        return "Hello, " + name;
    }


    @GetMapping("/{id}")
    public ResponseEntity getTddEntity(@PathVariable Integer id) {
        TddEntity tddEntity = service.getOne(id);
        return ResponseEntity.status(HttpStatus.OK).body(tddEntity);
    }

    @GetMapping
    public List<TddEntity> getTddEntities() {
        return service.getAll();
    }


    @PostMapping
    public ResponseEntity createTddEntity(@RequestBody TddEntity tddEntity) {

        TddEntity entity = service.createEntity(tddEntity);
        URI createdUri = URI.create("http://localhost:8080/api/v1/tdd");
        return ResponseEntity.created(createdUri).body(entity);
    }

}
