package com.example.zuzex.controller;

import com.example.zuzex.entity.HouseEntity;
import com.example.zuzex.entity.UserEntity;
import com.example.zuzex.exception.UserAlreadyExistException;
import com.example.zuzex.exception.UserIsNotFoundException;
import com.example.zuzex.service.HouseService;
import com.example.zuzex.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserServiceImpl userService;
    @Autowired
    HouseService houseSevice;

    @PostMapping("/create")
    private ResponseEntity createUser (@RequestBody UserEntity user)
    {
        try {

            return ResponseEntity.ok().body("User created "  + userService.createUser(user));

        }
        catch (UserAlreadyExistException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(" |ExceptionCreatUser| ");
        }
    }
    @GetMapping("/read")
    private  ResponseEntity readUser(@RequestParam Long id_user)
    {
        try {
            return ResponseEntity.ok().body(userService.readUser(id_user));
        }
        catch (UserIsNotFoundException e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage() + " |ExceptionReadUser| ");
        }

    }
    @PutMapping("/update")
    private ResponseEntity updateUser(@RequestBody UserEntity user)
    {
        try {
            userService.updateUser(user);
            return  ResponseEntity.ok().body("User updated");
        }
        catch (UserIsNotFoundException e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e.getMessage()+ " |ExceptionPutUser| ");
        }
    }

    @DeleteMapping("/delete")
    private ResponseEntity deleteUser(Long id_user)
    {
        try {
            userService.deleteUser(id_user);
            return ResponseEntity.ok().body("User deleted");
        }
        catch (UserIsNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage()+ " |ExceptionDeleteUser| ");

        }
    }

    @PutMapping("/buyHouse")
    private ResponseEntity buyHouse(@RequestParam Long owner_id,
                                    @RequestBody HouseEntity house)
    {
        try {
            return ResponseEntity.ok().body(userService.buyHouse(owner_id,house));
        }
        catch (UserIsNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        catch (Exception e){
            System.out.println("|ExceptionBuyUser| ");
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage()+ " |ExceptionBuyUser| ");

        }
    }
    @PutMapping("/addResid/{house_id}")
    private ResponseEntity addResid(@PathVariable Long house_id,
                                    @RequestParam Long user_id)
    {
        try {

            return ResponseEntity.ok().body(userService.addResid(user_id,house_id));
        }
        catch (UserIsNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e){
            System.out.println("|ExceptionBuyUser| ");
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage()+ " |ExceptionBuyUser| ");

        }

    }
    @PutMapping("/delResid/{house_id}")
    private ResponseEntity delResid(@PathVariable Long house_id,
                                    @RequestParam Long user_id)
    {
        try {

            return ResponseEntity.ok().body(userService.delResid(user_id,house_id));
        }
        catch (UserIsNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e){
            System.out.println("|ExceptionBuyUser| ");
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage()+ " |ExceptionBuyUser| ");

        }
    }

}
