package com.example.zuzex.service.impl;


import com.example.zuzex.Security.jwt.JwtTokenProvider;
import com.example.zuzex.entity.HouseEntity;
import com.example.zuzex.entity.UserEntity;

import com.example.zuzex.exception.UserAlreadyExistException;
import com.example.zuzex.exception.UserIsNotFoundException;
import com.example.zuzex.exception.UserIsTheOwnerHouseException;
import com.example.zuzex.model.HouseModel;
import com.example.zuzex.model.UserModel;
import com.example.zuzex.repository.HouseRepo;
import com.example.zuzex.repository.UserRepo;
import com.example.zuzex.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class UserServiceImpl  implements UserService {

    private final UserRepo userRepo;

    private final HouseRepo houseRepo;


    JwtTokenProvider jwtTokenProvider;
    @Autowired
    public UserServiceImpl(UserRepo userRepo, HouseRepo houseRepo, JwtTokenProvider jwtTokenProvider) {
        this.userRepo = userRepo;
        this.houseRepo = houseRepo;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String createUser(UserEntity user) throws UserAlreadyExistException {

        if(userRepo.findByName(user.getName()) != null ){
            throw new UserAlreadyExistException("Такой пользователь уже существует");
        }

        String token = jwtTokenProvider.createToken(user.getName());


        user.setToken(token);

        userRepo.save(user);
        return token;
    }
    public UserEntity findByUserName (String name){
        return userRepo.findByName(name);
    }
    public UserModel readUser(Long id) throws UserIsNotFoundException {
        UserEntity user = userRepo.findById(id).get();

        if (!userRepo.findById(id).isPresent())
        {
            throw new UserIsNotFoundException("Такого пользователя не существует");
        }

        return UserModel.toModel(user);
    }
    public  UserEntity updateUser (UserEntity user) throws  UserIsNotFoundException {
        if (!userRepo.findById(user.getId()).isPresent()) {
            throw new UserIsNotFoundException("Такого пользователя не существует");
        }
        UserEntity userEntity = userRepo.findById(user.getId()).get();
        userEntity.setName(user.getName());
        userEntity.setAge(user.getAge());
        userEntity.setHouseEntity(user.getHouseEntity());
        return userRepo.save(userEntity);
    }
    public void deleteUser (Long id) throws UserIsNotFoundException {
        if (!userRepo.findById(id).isPresent())
        {
            throw new UserIsNotFoundException("Такого пользователя не существует");
        }
        userRepo.deleteById(id);
    }

    public HouseModel buyHouse(Long owner_id, HouseEntity house ) throws Exception {
        try {
            UserEntity user = userRepo.findById(owner_id).get();
            if (!userRepo.findById(house.getId()).isPresent() || !houseRepo.findById(house.getId()).isPresent()) {
                throw new UserIsNotFoundException("Такого пользователя/дома не существует");
            }
            house.setUserEntity(user);
            return HouseModel.toModel(houseRepo.save(house));
        }
        catch (Exception e) {
            throw new Exception("Exception buyHouse:\n" + e.getMessage());
        }
    }

    public HouseModel addResid(Long user_id, Long house_id ) throws Exception {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserEntity user = userRepo.findById(user_id).get();
            HouseEntity house = houseRepo.findById(house_id).get();
            if(userRepo.findByName(authentication.getName()).getHouseEntity().stream().map(HouseEntity::getId).anyMatch(Long ->Long.equals(house_id)))
            {
                if(user.equals(house.getUserEntity())){
                    throw new UserIsTheOwnerHouseException("Пользователь является хозяином дома");
                }

                if (!userRepo.findById(house.getId()).isPresent() || !houseRepo.findById(house.getId()).isPresent()) {
                    throw new UserIsNotFoundException("Такого пользователя/дома не существует");
                }

                if (house.getResidents().stream().anyMatch(UserEntity -> UserEntity.equals(user))) {
                    throw new UserAlreadyExistException("Данный пользователь уже резидент дома");
                }

                house.getResidents().add(user);
                return HouseModel.toModel(houseRepo.save(house));
            }
            else{
                throw new UserIsTheOwnerHouseException("Вы не владелец дома!");
            }

        }
        catch (Exception e) {
            throw new Exception("Exception addResid:\n" + e.getMessage());
        }
    }
    public HouseModel delResid(Long user_id, Long house_id ) throws Exception {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserEntity user = userRepo.findById(user_id).get();
            HouseEntity house = houseRepo.findById(house_id).get();

            if(userRepo.findByName(authentication.getName()).getHouseEntity().stream().map(HouseEntity::getId).anyMatch(Long ->Long.equals(house_id))) {
                if (!userRepo.findById(house.getId()).isPresent() || !houseRepo.findById(house.getId()).isPresent()) {
                    throw new UserIsNotFoundException("Такого пользователя/дома не существует");
                }

                if (house.getResidents().stream().anyMatch(UserEntity -> UserEntity.equals(user))) {
                    house.getResidents().remove(user);

                } else {
                    throw new UserIsNotFoundException("Данный пользователь не резидент дома");
                }
            }
            else {
                throw new UserIsTheOwnerHouseException("Вы не владелец дома!");
            }

            return HouseModel.toModel(houseRepo.save(house));
        }
        catch (Exception e) {
            throw new Exception("Exception delResid:\n" + e.getMessage());
        }
    }


}
