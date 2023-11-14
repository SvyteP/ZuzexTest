package com.example.zuzex.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private String age;
    @Column(name = "passowrd")
    private String password;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "User")
    private List<HouseEntity> houseEntity;

    public UserEntity() {
    }

}
