package com.example.zuzex.model;

import com.example.zuzex.entity.HouseEntity;
import com.example.zuzex.entity.UserEntity;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserModel {

    private String Name;
    private String Age;
    private List<String> livingPalcel;
    private List<HouseModel> houseEntity;

    public UserModel() {
    }

    public static UserModel toModel(UserEntity user) {
       UserModel userModel = new UserModel();
       userModel.setAge(user.getAge());
       userModel.setName(user.getName());
        userModel.setLivingPalcel(user.getLiving_place().stream().map(HouseEntity::getAdress).collect(Collectors.toList()));// не дописал добавление дома пользователю
        userModel.setHouseEntity(user.getHouseEntity().stream().map(HouseModel::toModel).collect(Collectors.toList()));// не дописал добавление дома пользователю
        return userModel;
    }
}
