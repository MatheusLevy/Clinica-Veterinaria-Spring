package com.produtos.apirest.Model;

import com.produtos.apirest.models.User;
import com.produtos.apirest.repository.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserTest {

    @Autowired
    public UserRepo userRepo;

    protected static User generateUser(){
        return User.builder()
                .username("username")
                .password("password")
                .build();
    }

    private void rollback(User user){
        userRepo.delete(user);
    }

    protected static void rollbackUser(User user, UserRepo userRepo){
        userRepo.delete(user);
    }

    @Test
    public void save(){
        User userSaved = userRepo.save(generateUser());
        Assertions.assertNotNull(userSaved);
        Assertions.assertNotNull(userSaved.getUserId());
        rollback(userSaved);
    }

    @Test
    public void update(){
        User userSaved = userRepo.save(generateUser());
        userSaved.setUsername("New Username");
        User userUpdated = userRepo.save(userSaved);
        Assertions.assertNotNull(userUpdated);
        Assertions.assertEquals(userUpdated.getUserId(), userSaved.getUserId());
        Assertions.assertEquals(userUpdated.getUsername(), "New Username");
        rollback(userSaved);
    }

    @Test
    public void removeById(){
        User userSaved = userRepo.save(generateUser());
        Long id = userSaved.getUserId();
        userRepo.deleteById(id);
        Assertions.assertNull(userRepo.findByUserId(id));
    }

    @Test
    public void findById(){
        User userSaved = userRepo.save(generateUser());
        Long id = userSaved.getUserId();
        Assertions.assertNotNull(userRepo.findByUserId(id));
        rollback(userSaved);
    }

    @Test
    public void findByUsername(){
        User userSaved = userRepo.save(generateUser());
        User userFind = userRepo.findByUsername(userSaved.getUsername());
        Assertions.assertNotNull(userFind);
        Assertions.assertEquals(userFind.getUserId(), userSaved.getUserId());
        rollback(userSaved);
    }
}