package com.example.springboottest.repository;

import com.example.springboottest.entity.TddEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TddEntityRepository extends JpaRepository<TddEntity, Integer> {
}
