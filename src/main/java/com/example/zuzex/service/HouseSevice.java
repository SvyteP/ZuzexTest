package com.example.zuzex.service;

import com.example.zuzex.entity.HouseEntity;
import com.example.zuzex.entity.UserEntity;
import com.example.zuzex.exception.HouseAlreadyExistException;
import com.example.zuzex.exception.HouseIsNotFoundException;
import com.example.zuzex.model.HouseModel;
import com.example.zuzex.repository.HouseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HouseSevice {
    @Autowired
    private HouseRepo houseRepo;

    public HouseEntity createHouse(HouseEntity house) throws HouseAlreadyExistException {
        if (houseRepo.findByAdress(house.getAdress()) != null) {
            throw new HouseAlreadyExistException("Такой дом уже существует");
        }
        return houseRepo.save(house);
    }

    public HouseModel readHouse(Long id) throws HouseIsNotFoundException {
        if (houseRepo.findById(id).isEmpty()) {
            throw new HouseIsNotFoundException("Такого дома не существует");
        }
        return HouseModel.toModel(houseRepo.findById(id).get());
    }


        public  HouseEntity updateHouse (HouseEntity house) throws HouseIsNotFoundException {
            if (houseRepo.findById(house.getId()).isEmpty()) {
                throw new HouseIsNotFoundException("Такого дома не существует,создайте его с помощью create :)");
            }
        HouseEntity houseEntity = houseRepo.findById(house.getId()).get();
        houseEntity.setUser(house.getUser());
        houseEntity.setAdress(house.getAdress());
        return houseRepo.save(houseEntity);
        }
    public void deleteHouse(Long id) throws HouseIsNotFoundException {
        if (houseRepo.findById(id).isEmpty()) {
            throw new HouseIsNotFoundException("Такого дома не существует");
        }
        houseRepo.deleteById(id);

    }
}