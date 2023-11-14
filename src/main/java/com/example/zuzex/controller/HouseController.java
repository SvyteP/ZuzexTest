package com.example.zuzex.controller;

import com.example.zuzex.entity.HouseEntity;
import com.example.zuzex.entity.UserEntity;
import com.example.zuzex.exception.HouseAlreadyExistException;
import com.example.zuzex.exception.HouseIsNotFoundException;
import com.example.zuzex.service.HouseSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/house")
public class HouseController {
    @Autowired
    HouseSevice houseSevice;
    @PostMapping("/create")
    private ResponseEntity createHouse (@RequestBody HouseEntity house)
    {
        try {
            houseSevice.createHouse(house);
            return ResponseEntity.ok().body("House created");
        }
        catch (HouseAlreadyExistException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage() + " |ExceptionCreatUser| ");
        }
    }
    @GetMapping("/read")
    private  ResponseEntity readHouse(@RequestParam Long id_house)
    {
        try {

            return ResponseEntity.ok().body(houseSevice.readHouse(id_house));
        }
        catch (HouseIsNotFoundException e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage() + " |ExceptionReadUser| ");
        }

    }
    @PutMapping("/update")
    private ResponseEntity updateHouse(@RequestBody HouseEntity house)
    {
        try {
            houseSevice.updateHouse(house);
            return  ResponseEntity.ok().body("House updated");
        }
        catch (HouseIsNotFoundException e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e.getMessage()+ " |ExceptionPutUser| ");
        }
    }

    @DeleteMapping("/delete")
    private ResponseEntity deleteHouse(@RequestParam Long id_house)
    {
        try {
            houseSevice.deleteHouse(id_house);
            return ResponseEntity.ok().body("House deleted");
        }
        catch (HouseIsNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage()+ " |ExceptionDeleteUser| ");

        }
    }
}
