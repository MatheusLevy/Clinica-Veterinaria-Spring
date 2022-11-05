package com.produtos.apirest.Service;

import com.produtos.apirest.models.User;
import com.produtos.apirest.repository.RoleRepo;
import com.produtos.apirest.repository.UserRepo;
import com.produtos.apirest.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.produtos.apirest.Service.RoleServiceTest.genereteRolesList;
import static com.produtos.apirest.Service.RoleServiceTest.rollbackRolesList;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    public RoleRepo roleRepo;

    @Autowired
    public UserRepo userRepo;

    @Autowired
    public UserService userService;

    protected static User generateUser(Boolean initRole, RoleRepo roleRepo){
        return User.builder()
                .username("test")
                .password("password")
                .roles(genereteRolesList(initRole, roleRepo))
                .build();
    }

    private User generateUser(Boolean initRole){
        return User.builder()
                .username("test")
                .password("password")
                .roles(genereteRolesList(initRole, roleRepo))
                .build();
    }

    private void rollback(User user){
        userRepo.delete(user);
        rollbackRolesList(user.getRoles(), roleRepo);
    }

    protected void rollbackUser(User user, RoleRepo roleRepo){
        userRepo.delete(user);
        rollbackRolesList(user.getRoles(), roleRepo);
    }

    @Test
    public void save(){
        User userSaved = userService.save(generateUser(true));
        Assertions.assertNotNull(userSaved);
        Assertions.assertNotNull(userSaved.getUserId());
        rollback(userSaved);
    }

    @Test
    public void update(){
        User userSaved = userService.save(generateUser(true));
        userSaved.setUsername("New Username");
        User userUpdated = userService.update(userSaved);
        Assertions.assertNotNull(userUpdated);
        Assertions.assertEquals(userUpdated.getUserId(), userSaved.getUserId());
        rollback(userSaved);
    }

    @Test
    public void authenticate(){
        User userSaved = userService.save(generateUser(true));
        User autenticado = userService.authenticate(userSaved.getUsername(), "password");
        Assertions.assertNotNull(autenticado.getUserId());
        rollback(userSaved);
    }

    @Test
    public void remove(){
        User userSaved = userService.save(generateUser(true));
        Long id = userSaved.getUserId();
        userService.remove(userSaved);
        Assertions.assertNull(userRepo.findByUserId(id));
        rollbackRolesList(userSaved.getRoles(), roleRepo);
    }

    @Test
    public void removeByIdWithFeedback(){
        User userSaved = userService.save(generateUser(true));
        User feedback = userService.removeByIdWithFeedback(userSaved.getUserId());
        Assertions.assertNotNull(feedback);
        Assertions.assertEquals(feedback.getUserId(), userSaved.getUserId());
        rollbackRolesList(userSaved.getRoles(), roleRepo);
    }

    @Test
    public void findByUsername(){
        User userSaved = userService.save(generateUser(true));
        User userFind = userService.findByUsername(userSaved.getUsername());
        Assertions.assertNotNull(userFind);
        Assertions.assertEquals(userFind.getUserId(), userSaved.getUserId());
        rollback(userSaved);
    }

    @Test
    public void findById(){
        User userSaved = userService.save(generateUser(true));
        Long id = userSaved.getUserId();
        User userFind = userService.findById(id);
        Assertions.assertNotNull(userFind);
        Assertions.assertEquals(userFind.getUserId(), userSaved.getUserId());
        rollback(userSaved);
    }

    @Test
    public void findAll(){
        User userSaved = userService.save(generateUser(true));
        Assertions.assertFalse(userService.findAll().isEmpty());
        rollback(userSaved);
    }
}