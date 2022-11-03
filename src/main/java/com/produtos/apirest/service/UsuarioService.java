package com.produtos.apirest.service;

import com.produtos.apirest.models.User;
import com.produtos.apirest.repository.UserRepo;
import com.produtos.apirest.service.excecoes.AuthenticationFailedExpection;
import com.produtos.apirest.service.excecoes.RegraNegocioRunTime;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UsuarioService {

    private final UserRepo userRepo;

    public UsuarioService(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    private BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public static void isNotNull(User user){
        if(user == null)
            throw new NullPointerException("User must not be null!");
    }

    public static void hasUsername(User user){
        if (user.getUsername().equals(""))
            throw  new RegraNegocioRunTime("The user should have a username!");
    }

    public static void hasPassword(User user){
        if (user.getPassword().equals(""))
            throw new RegraNegocioRunTime("The user should have a password!");
    }

    public static void hasRoles(User user){
        if (user.getRoles().isEmpty())
            throw new RegraNegocioRunTime("The user should have roles!");
    }

    public static void hasId(User user){
        if (user.getUserId() <= 0){
            throw new RegraNegocioRunTime("The user should have a id!");
        }
    }

    public static void hasId(Long id){
        if (id <= 0){
            throw new RegraNegocioRunTime("The user should have a id!");
        }
    }

    public static void verifyAllRules(User user){
        isNotNull(user);
        hasId(user);
        hasUsername(user);
        hasPassword(user);
        hasRoles(user);
    }

    public static void IsNotNullHasUsernameHasPasswordHasRoles(User user){
        isNotNull(user);
        hasUsername(user);
        hasPassword(user);
        hasRoles(user);
    }

    private User matchPasswordWithUser(String rawPassword, User user){
        if(passwordEncoder().matches(rawPassword, user.getPassword()))
            return user;
        else
            throw new AuthenticationFailedExpection("Username or password incorrect!");
    }

    private void encodePassword(User user){
        String password = user.getPassword();
        String encodedPassword = passwordEncoder().encode(password);
        user.setPassword(encodedPassword);
    }

    @Transactional
    public User authenticate(String username, String password){
        User user = userRepo.findByUsername(username);
        return matchPasswordWithUser(password, user);
    }

    @Transactional
    public User save(User user){
        IsNotNullHasUsernameHasPasswordHasRoles(user);
        encodePassword(user);
        return userRepo.save(user);
    }

    @Transactional
    public User update(User user){
        verifyAllRules(user);
        return userRepo.save(user);
    }

    @Transactional
    public void remove(User user){
        verifyAllRules(user);
        userRepo.delete(user);
    }

    @Transactional
    public User removeByIdWithFeedback(Long id){
        hasId(id);
        User feedback = userRepo.findByUserId(id);
        userRepo.delete(feedback);
        return feedback;
    }

    @Transactional
    public User findById(Long id){
        hasId(id);
        return userRepo.findByUserId(id);
    }

    @Transactional
    public User findByUsername(String username){
        return userRepo.findByUsername(username);
    }

    @Transactional
    public List<User> findAll(){
        return userRepo.findAll();
    }
}