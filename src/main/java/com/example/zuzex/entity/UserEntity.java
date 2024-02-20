package com.example.zuzex.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity

@Table(name = "users")
public class        UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String age;
    private String password;
    private String Token;
    @ManyToMany(cascade = CascadeType.ALL,
                mappedBy = "residents")
    private List<HouseEntity> living_place;
    @OneToMany(cascade = CascadeType.ALL,
                mappedBy = "userEntity")
    private List<HouseEntity> houseEntity;

    public UserEntity() {
    }

    public void addHouseToUser(HouseEntity house)
    {
        if(houseEntity == null)
        {
            houseEntity = new ArrayList<>();
        }

        houseEntity.add(house);
        house.setUserEntity(this);
    }
}
