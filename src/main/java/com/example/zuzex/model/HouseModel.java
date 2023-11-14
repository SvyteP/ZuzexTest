package com.example.zuzex.model;

import com.example.zuzex.entity.HouseEntity;
import com.example.zuzex.entity.UserEntity;
import lombok.Data;

@Data
public class HouseModel {
    private String adress;
    private UserEntity User;

    public static HouseModel toModel(HouseEntity house){
        HouseModel houseModel = new HouseModel();
            houseModel.setAdress(house.getAdress());
            houseModel.setUser(house.getUser());
        return houseModel;
    }
    public HouseModel() {
    }
}
