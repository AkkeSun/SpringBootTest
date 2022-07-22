package com.example.springboottest.entity;


import lombok.*;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
public class TddEntity {

    @Id
    private Integer id;

    private String email;

    private String password;

}
