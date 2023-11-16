package com.example.zuzex.service;

import com.example.zuzex.entity.HouseEntity;
import com.example.zuzex.entity.UserEntity;
import com.example.zuzex.exception.HouseIsNotFoundException;
import com.example.zuzex.exception.UserAlreadyExistException;
import com.example.zuzex.exception.UserIsNotFoundException;
import com.example.zuzex.model.HouseModel;
import com.example.zuzex.model.UserModel;
import com.example.zuzex.repository.HouseRepo;
import com.example.zuzex.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service

public class UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private HouseRepo houseRepo;
    public UserEntity createUser(UserEntity user) throws UserAlreadyExistException {
        if(userRepo.findByName(user.getName()) != null ){
            throw new UserAlreadyExistException("Такой пользователь уже существует");
        }
        return userRepo.save(user);
    }
    public UserModel readUser(Long id) throws UserIsNotFoundException {
        if (userRepo.findById(id).isEmpty())
        {
            throw new UserIsNotFoundException("Такого пользователя не существует");
        }
        return UserModel.toModel(userRepo.findById(id).get());
    }
    public  UserEntity updateUser (UserEntity user) throws  UserIsNotFoundException {
        if (userRepo.findById(user.getId()).isEmpty()) {
            throw new UserIsNotFoundException("Такого пользователя не существует");
        }
        UserEntity userEntity = userRepo.findById(user.getId()).get();
        userEntity.setName(user.getName());
        userEntity.setAge(user.getAge());
        userEntity.setHouseEntity(user.getHouseEntity());
        return userRepo.save(userEntity);
    }
    public void deleteUser (Long id) throws UserIsNotFoundException {
        if (userRepo.findById(id).isEmpty())
        {
            throw new UserIsNotFoundException("Такого пользователя не существует");
        }
        userRepo.deleteById(id);
    }

    public HouseModel buyHouse(Long user_id, HouseEntity house ) throws UserIsNotFoundException {
       try {
           if (userRepo.findById(house.getId()).isEmpty() || houseRepo.findById(house.getId()).isEmpty() ) {
               throw new UserIsNotFoundException("Такого пользователя/дома не существует");
           }
           house.setUser(userRepo.findById(user_id).get());
           return HouseModel.toModel(houseRepo.save(house));
       }
       catch (Exception e){
           System.out.println(e.getMessage());
           return null;
       }
    }


}
