package com.example.zuzex.service;


import com.example.zuzex.entity.HouseEntity;
import com.example.zuzex.entity.UserEntity;

import com.example.zuzex.exception.UserAlreadyExistException;
import com.example.zuzex.exception.UserIsNotFoundException;
import com.example.zuzex.exception.UserIsTheOwnerHouseException;
import com.example.zuzex.model.HouseModel;
import com.example.zuzex.model.UserModel;
import com.example.zuzex.repository.HouseRepo;
import com.example.zuzex.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;



@Service

public class UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private HouseRepo houseRepo;
    private AuthenticationManager authenticationManager;

/*
   @Autowired
    private JwtGenerator jwtGenerator;
*/
    public UserEntity createUser(UserEntity user) throws UserAlreadyExistException {
        Authentication authentication;
        if(userRepo.findByName(user.getName()) != null ){
            throw new UserAlreadyExistException("Такой пользователь уже существует");
        }
      //  user.setJwtToken(jwtGenerator.generateToken(user.getName()));
        authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getName(),user.getPassword()));
       /* System.out.println("JWT: " +jwtGenerator.getKey());
        System.out.println(jwtGenerator);*/

        return userRepo.save(user);
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
        //System.out.println(jwtGenerator.parseToken(user.getJwtToken()));

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
            UserEntity user = userRepo.findById(user_id).get();
            HouseEntity house = houseRepo.findById(house_id).get();
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
        catch (Exception e) {
            throw new Exception("Exception buyHouse:\n" + e.getMessage());
        }
    }
    public HouseModel delResid(Long user_id, Long house_id ) throws Exception {
        try {
            UserEntity user = userRepo.findById(user_id).get();
            HouseEntity house = houseRepo.findById(house_id).get();

            if (!userRepo.findById(house.getId()).isPresent() || !houseRepo.findById(house.getId()).isPresent()) {
                throw new UserIsNotFoundException("Такого пользователя/дома не существует");
            }

            if (house.getResidents().stream().anyMatch(UserEntity -> UserEntity.equals(user))) {
                house.getResidents().remove(user);

            }
            else {
                throw new UserIsNotFoundException("Данный пользователь не резидент дома");
            }


            return HouseModel.toModel(houseRepo.save(house));
        }
        catch (Exception e) {
            throw new Exception("Exception buyHouse:\n" + e.getMessage());
        }
    }


}
