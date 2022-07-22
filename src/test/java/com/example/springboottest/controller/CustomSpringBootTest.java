package com.example.springboottest.controller;

import com.example.springboottest.entity.TddEntity;
import com.example.springboottest.repository.TddEntityRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CustomSpringBootTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    TddEntityRepository repository;

    @Test
    @DisplayName("jpa 데이터 로드 테스트")
    public void jpaGetOneTest () {

        //given
        TddEntity entity = TddEntity.builder().id(1).email("lowlow@gmail.com").password("1234").build();

        // when
        TddEntity save = repository.save(entity);

        // then
        Assertions.assertEquals("lowlow@gmail.com", save.getEmail());
        Assertions.assertEquals("1234", save.getPassword());
    }

    @Test
    @DisplayName("jpa 데이터 세이브 테스트")
    public void jpaSaveTest() {

        // when
        TddEntity save = repository.findById(1).orElseThrow(ArithmeticException::new);

        // then
        Assertions.assertEquals("lowlow@gmail.com", save.getEmail());
        Assertions.assertEquals("1234", save.getPassword());
    }


    @Test
    @DisplayName("전체 데이터 가져오기")
    public void getAllData() throws Exception {

        // when
        ResultActions actions = mockMvc.perform(get("/api/v1/tdd"));

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, "application/json"))
                .andDo(print());
    }


    @Test
    @DisplayName("하나의 데이터 가져오기")
    public void getOne() throws Exception {


        // when
        ResultActions actions = mockMvc.perform(get("/api/v1/tdd/1"));

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, "application/json"))
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("email").exists())
                .andExpect(jsonPath("password").exists())
                .andDo(print());
    }



    @Test
    @DisplayName("저장하기")
    public void createData() throws Exception {

        // given
        TddEntity entity = TddEntity.builder().id(2).email("test@gmail.com").password("1234").build();

        // when
        ResultActions actions =
                mockMvc.perform(
                        post("/api/v1/tdd")
                                .contentType(MediaType.APPLICATION_JSON) // RequestBody Type (APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON)      // Response Type    (HAL_JSON)
                                .content(objectMapper.writeValueAsString(entity))
                );

        // then
        actions
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, "application/json"))
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("email").exists())
                .andExpect(jsonPath("password").exists())
                .andDo(print());
    }
}
