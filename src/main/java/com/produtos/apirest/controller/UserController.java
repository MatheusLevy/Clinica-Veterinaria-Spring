package com.produtos.apirest.controller;

import com.produtos.apirest.models.DTO.UserDTO;
import com.produtos.apirest.models.User;
import com.produtos.apirest.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/authenticate")
    public ResponseEntity<?> autenticar(@RequestBody UserDTO dto){
        try{
            User authenticatedUser = userService.authenticate(dto.getUsername(), dto.getPassword());
            UserDTO dtoResponse = authenticatedUser.toUserDTO();
            return ResponseEntity.ok(dtoResponse);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody UserDTO dto){
        try{
            User user =  dto.toUser();
            User userSaved = userService.save(user);
            UserDTO dtoResponse = userSaved.toUserDTO();
            return new ResponseEntity<>(dtoResponse, HttpStatus.CREATED);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeById(@PathVariable(value = "id") Long id){
        try{
            User userFind = userService.findById(id);
            userService.remove(userFind);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @DeleteMapping("/feedback/{id}")
    public ResponseEntity<?> removeWithFeedback(@PathVariable(value = "id") Long id){
        try{
            User userRemoved = userService.removeByIdWithFeedback(id);
            UserDTO dtoResponse = userRemoved.toUserDTO();
            return ResponseEntity.ok(dtoResponse);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable(value = "id") Long id){
        try{
            User userFind = userService.findById(id);
            UserDTO dtoResponse = userFind.toUserDTO();
            return ResponseEntity.ok(dtoResponse);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/username/{username}")
    public ResponseEntity<?> findByUsername(@PathVariable(value = "username") String username){
        try {
            User userFind = userService.findByUsername(username);
            UserDTO dtoResponse = userFind.toUserDTO();
            return ResponseEntity.ok(dtoResponse);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PutMapping
    public ResponseEntity<?> update(@RequestBody UserDTO dto){
        try{
            User user = dto.toUser();
            User userUpdated = userService.update(user);
            UserDTO dtoResponse = userUpdated.toUserDTO();
            return ResponseEntity.ok(dtoResponse);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}