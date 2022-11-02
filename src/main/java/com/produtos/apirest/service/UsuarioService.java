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

    public static void verifyIsNull(User user){
        if(user == null)
            throw new NullPointerException("User must not be null!");
    }

    public static void verifyHasUsername(User user){
        if (user.getUsername().equals(""))
            throw  new RegraNegocioRunTime("The user should have a username!");
    }

    public static void verifyHasPassword(User user){
        if (user.getPassword().equals(""))
            throw new RegraNegocioRunTime("The user should have a password!");
    }

    public static void verifyHasRoles(User user){
        if (user.getRoles().isEmpty())
            throw new RegraNegocioRunTime("The user should have roles!");
    }

    public static void verifyHasId(User user){
        if (user.getUserId() <= 0){
            throw new RegraNegocioRunTime("The user should have a id!");
        }
    }

    public static void verifyHasId(Long id){
        if (id <= 0){
            throw new RegraNegocioRunTime("The user should have a id!");
        }
    }

    public static void verifyAllRules(User user){
        verifyIsNull(user);
        verifyHasId(user);
        verifyHasUsername(user);
        verifyHasPassword(user);
        verifyHasRoles(user);
    }

    public static void verifyIsNullHasUsernameHasPasswordHasRoles(User user){
        verifyIsNull(user);
        verifyHasUsername(user);
        verifyHasPassword(user);
        verifyHasRoles(user);
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
        verifyIsNullHasUsernameHasPasswordHasRoles(user);
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
        verifyHasId(id);
        User feedback = userRepo.findByUserId(id);
        userRepo.delete(feedback);
        return feedback;
    }

    @Transactional
    public User findById(Long id){
        verifyHasId(id);
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