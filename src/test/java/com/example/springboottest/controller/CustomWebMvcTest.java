package com.example.springboottest.controller;


import com.example.springboottest.entity.TddEntity;
import com.example.springboottest.service.TddTestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TestController.class)
public class CustomWebMvcTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    // @WebMvcTest 는 Controller Bean만 가져오므로 그 이외의 Bean은 MockBean 이라는 가상 객채를 만들어 사용해야한다
    @MockBean
    TddTestService service;


    @Test
    @DisplayName("파라미터 리턴 테스트")
    public void parameterTest() throws Exception {

        // when
        ResultActions actions = mockMvc.perform(
                get("/api/v1/tdd/basicTest").param("name", "sun")
        );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, "application/json"))
                .andDo(print());
    }



    @Test
    @DisplayName("전체 데이터 가져오기")
    public void getAllData() throws Exception {

        // given : MockBean 이 수행할 기능을 정의. MockBean은 가상 객채이기 때문에 해당 정의를 하지 않으면 NullPointException 발생
        List<TddEntity> list = new ArrayList<>();
        list.add(TddEntity.builder().id(1).email("lowlow@gmail.com").password("1234").build());
        given(service.getAll()).willReturn(list);

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

        // given
        Integer id = 1;
        TddEntity entity = TddEntity.builder().id(id).email("lowlow@gmail.com").password("1234").build();
        given(service.getOne(id)).willReturn(entity);

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
        given(service.createEntity(any())).willReturn(entity);


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