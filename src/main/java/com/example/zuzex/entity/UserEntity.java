package com.example.zuzex.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String age;
    private String password;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
    @JsonIgnore
    private List<HouseEntity> houseEntity;

    public UserEntity() {
    }

}
