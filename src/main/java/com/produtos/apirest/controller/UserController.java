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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/authenticate")
    public ResponseEntity<?> autenticar(@Valid @RequestBody UserDTO dto){
        User user = dto.toUser();
        User authenticatedUser = userService.authenticate(user.getUsername(), user.getPassword());
        UserDTO dtoResponse = authenticatedUser.toUserDTO();
        return ResponseEntity.ok(dtoResponse);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public UserDTO save(@RequestBody UserDTO dto){
        User user =  dto.toUser();
        User userSaved = userService.save(user);
        return userSaved.toUserDTO();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeById(@PathVariable(value = "id") Long id){
        User userFind = userService.findById(id);
        userService.remove(userFind);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/feedback/{id}")
    public ResponseEntity<?> removeWithFeedback(@PathVariable(value = "id") Long id){
        User userRemoved = userService.removeByIdWithFeedback(id);
        UserDTO dtoResponse = userRemoved.toUserDTO();
        return ResponseEntity.ok(dtoResponse);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable(value = "id") Long id){
        User userFind = userService.findById(id);
        UserDTO dtoResponse = userFind.toUserDTO();
        return ResponseEntity.ok(dtoResponse);
    }


    @GetMapping("/username/{username}")
    public ResponseEntity<?> findByUsername(@PathVariable(value = "username") String username){
        User userFind = userService.findByUsername(username);
        UserDTO dtoResponse = userFind.toUserDTO();
        return ResponseEntity.ok(dtoResponse);
    }


    @PutMapping
    public ResponseEntity<?> update(@RequestBody UserDTO dto){
        User user = dto.toUser();
        User userUpdated = userService.update(user);
        UserDTO dtoResponse = userUpdated.toUserDTO();
        return ResponseEntity.ok(dtoResponse);
    }
}