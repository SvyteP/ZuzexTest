package com.example.zuzex.controller;

import com.example.zuzex.entity.UserEntity;
import com.example.zuzex.exception.UserAlreadyExistException;
import com.example.zuzex.exception.UserIsNotFoundException;
import com.example.zuzex.service.HouseSevice;
import com.example.zuzex.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/create")
    private ResponseEntity createUser (@RequestBody UserEntity user)
    {
        try {
            userService.createUser(user);
            return ResponseEntity.ok().body("User created");

        }
        catch (UserAlreadyExistException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage() + " |ExceptionCreatUser| ");
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

    @PostMapping("/buyHouse")
    private ResponseEntity buyHouse(Long id_user)
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

}
