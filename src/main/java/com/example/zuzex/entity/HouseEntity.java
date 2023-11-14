package com.example.zuzex.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "houses")
public class HouseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "adress")
    private String adress;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private UserEntity User;

    public HouseEntity() {
    }
}
