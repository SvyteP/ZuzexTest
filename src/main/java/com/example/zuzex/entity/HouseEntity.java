package com.example.zuzex.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "houses")
public class HouseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String adress;

    @ManyToMany(cascade = CascadeType.ALL)

    private List<UserEntity>  residents;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Owner_id")
    private UserEntity userEntity;

    public HouseEntity() {
    }
    public void addResid (UserEntity user){
        if(residents==null){
            residents = new ArrayList<>();
        }
        residents.add(user);
        setResidents(residents);
    }


}
