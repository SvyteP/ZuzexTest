package com.example.zuzex.service.impl;

import com.example.zuzex.entity.HouseEntity;
import com.example.zuzex.exception.HouseAlreadyExistException;
import com.example.zuzex.exception.HouseIsNotFoundException;
import com.example.zuzex.model.HouseModel;
import com.example.zuzex.repository.HouseRepo;
import com.example.zuzex.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HouseSeviceImpl implements HouseService {
    @Autowired
    private HouseRepo houseRepo;

    public HouseEntity createHouse(HouseEntity house) throws HouseAlreadyExistException {
        if (houseRepo.findByAdress(house.getAdress()) != null) {
            throw new HouseAlreadyExistException("Такой дом уже существует");
        }
        return houseRepo.save(house);
    }

    public HouseModel readHouse(Long id) throws HouseIsNotFoundException {
        if (!houseRepo.findById(id).isPresent()) {
            throw new HouseIsNotFoundException("Такого дома не существует");
        }
        return HouseModel.toModel(houseRepo.findById(id).get());
    }


        public  HouseEntity updateHouse (HouseEntity house) throws HouseIsNotFoundException {
            if (!houseRepo.findById(house.getId()).isPresent()) {
                throw new HouseIsNotFoundException("Такого дома не существует,создайте его с помощью create :)");
            }
        HouseEntity houseEntity = houseRepo.findById(house.getId()).get();
        houseEntity.setUserEntity(house.getUserEntity());
        houseEntity.setAdress(house.getAdress());
        return houseRepo.save(houseEntity);
        }
    public void deleteHouse(Long id) throws HouseIsNotFoundException {
        if (!houseRepo.findById(id).isPresent()) {
            throw new HouseIsNotFoundException("Такого дома не существует");
        }
        houseRepo.deleteById(id);
    }
}