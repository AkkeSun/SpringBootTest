package com.example.springboottest.controller;

import com.example.springboottest.entity.TddEntity;
import com.example.springboottest.repository.TddEntityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


/*
@DataJpaTest 는 내장 메모리 데이터베이스로만 테스트가 가능하도록 설정되어있다.
기존에 사용하고 있는 데이터베이스로 테스트를 하려면 @AutoConfigureTestDatabase 를 재정의 해주어야 한다
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CustomJpaRepositoryTest {

    @Autowired
    private TddEntityRepository repository;

    @Test
    public void save () {

        //given
        TddEntity entity = TddEntity.builder().id(1).email("lowlow@gmail.com").password("1234").build();

        // when
        TddEntity save = repository.save(entity);

        // then
        Assertions.assertEquals("lowlow@gmail.com", save.getEmail());
        Assertions.assertEquals("1234", save.getPassword());
    }

    @Test
    public void findOne() {

        // when
        TddEntity save = repository.findById(1).orElseThrow(ArithmeticException::new);

        // then
        Assertions.assertEquals("lowlow@gmail.com", save.getEmail());
        Assertions.assertEquals("1234", save.getPassword());
    }


}
