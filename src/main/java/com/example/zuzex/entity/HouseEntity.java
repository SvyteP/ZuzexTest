package com.example.zuzex.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "house")
public class HouseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String adress;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public HouseEntity() {
    }

}
