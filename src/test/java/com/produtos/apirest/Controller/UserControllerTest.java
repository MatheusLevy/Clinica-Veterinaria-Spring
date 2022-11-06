package com.produtos.apirest.Controller;

import com.produtos.apirest.Util.Util;
import com.produtos.apirest.models.DTO.UserDTO;
import com.produtos.apirest.models.User;
import com.produtos.apirest.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.produtos.apirest.Util.Util.buildRequest;
import static com.produtos.apirest.Util.Util.toJson;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
public class UserControllerTest {

    private final String API = "/api/user";

    @MockBean
    private UserService userService;

    @Autowired
    MockMvc mvc;

    public static UserDTO generateUserDTO(){
        return UserDTO.builder()
                .id(1L)
                .username("username")
                .password("password")
                .build();
    }

    public static User generateUser(){
        return User.builder()
                .userId(1L)
                .username("username")
                .password("passsword")
                .build();
    }

    @Test
    public void authenticate() throws Exception{
        Mockito.when(userService.authenticate(Mockito.anyString(), Mockito.anyString())).thenReturn(generateUser());
        String json = toJson(generateUserDTO());
        MockHttpServletRequestBuilder request = Util.buildRequest(HttpMethod.GET, API.concat("/authenticate"), json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void save() throws Exception{
        Mockito.when(userService.save(Mockito.any(User.class))).thenReturn(generateUser());
        String json = toJson(generateUserDTO());
        MockHttpServletRequestBuilder request = Util.buildRequest(HttpMethod.POST, API, json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @WithUserDetails("Admin")
    @Test
    public void update() throws Exception{
        Mockito.when(userService.update(Mockito.any(User.class))).thenReturn(generateUser());
        String json = toJson(generateUserDTO());
        MockHttpServletRequestBuilder request = Util.buildRequest(HttpMethod.PUT, API, json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void removeById() throws Exception{
        Long id = 1L;
        Mockito.when(userService.findById(Mockito.anyLong())).thenReturn(generateUser());
        Mockito.doNothing().when(userService).remove(Mockito.any(User.class));
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.DELETE, API.concat("/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void removeByIdWithFeedback() throws Exception{
        Long id = 1L;
        Mockito.when(userService.removeByIdWithFeedback(Mockito.anyLong())).thenReturn(generateUser());
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.DELETE, API.concat("/feedback/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void findById() throws Exception{
        Long id = 1L;
        Mockito.when(userService.findById(Mockito.anyLong())).thenReturn(generateUser());
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.GET, API.concat("/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void findByUsername() throws Exception{
        String username = "username";
        Mockito.when(userService.findByUsername(Mockito.anyString())).thenReturn(generateUser());
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.GET, API.concat("/username/").concat(username));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}