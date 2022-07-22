package com.example.springboottest.service;

import com.example.springboottest.entity.TddEntity;
import com.example.springboottest.repository.TddEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TddTestService {

    @Autowired
    private TddEntityRepository repository;

    @Transactional
    public TddEntity getOne(Integer id) {
        return repository.findById(id).orElseThrow(ArithmeticException::new);
    }

    @Transactional
    public List<TddEntity> getAll() {
        return repository.findAll();
    }

    @Transactional
    public TddEntity createEntity(TddEntity tddEntity) {
        return repository.save(tddEntity);
    }
}
