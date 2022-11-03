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
public class UsuarioControllerTeste {

    private final String API = "/api/usuario";

    @MockBean
    private UserService userService;

    @Autowired
    MockMvc mvc;

    public static UserDTO generateUsuarioDTOInstance(){
        return UserDTO.builder()
                .id(1L)
                .username("username")
                .password("senha")
                .build();
    }

    public static User generateUsuarioInstance(){
        return User.builder()
                .userId(1L)
                .username("username")
                .password("passsword")
                .build();
    }

    @Test
    public void deveAutenticar() throws Exception{
        Mockito.when(userService.authenticate(Mockito.anyString(), Mockito.anyString())).thenReturn(generateUsuarioInstance());
        String json = toJson(generateUsuarioDTOInstance());
        MockHttpServletRequestBuilder request = Util.buildRequest(HttpMethod.GET, API.concat("/autenticar"), json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveSalvar() throws Exception{
        Mockito.when(userService.save(Mockito.any(User.class))).thenReturn(generateUsuarioInstance());
        String json = toJson(generateUsuarioDTOInstance());
        MockHttpServletRequestBuilder request = Util.buildRequest(HttpMethod.POST, API.concat("/salvar"), json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveAtualizar() throws Exception{
        Mockito.when(userService.update(Mockito.any(User.class))).thenReturn(generateUsuarioInstance());
        String json = toJson(generateUsuarioDTOInstance());
        MockHttpServletRequestBuilder request = Util.buildRequest(HttpMethod.PUT, API.concat("/atualizar"), json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveRemoverComId() throws Exception{
        Long id = 1L;
        Mockito.when(userService.findById(Mockito.anyLong())).thenReturn(generateUsuarioInstance());
        Mockito.doNothing().when(userService).remove(Mockito.any(User.class));
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.DELETE, API.concat("/remover/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveRemoverComFeedback() throws Exception{
        Long id = 1L;
        Mockito.when(userService.removeByIdWithFeedback(Mockito.anyLong())).thenReturn(generateUsuarioInstance());
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.DELETE, API.concat("/remover/feedback/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveBuscarPorId() throws Exception{
        Long id = 1L;
        Mockito.when(userService.findById(Mockito.anyLong())).thenReturn(generateUsuarioInstance());
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.GET, API.concat("/buscar/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveBuscarPorUsername() throws Exception{
        String username = "username";
        Mockito.when(userService.findByUsername(Mockito.anyString())).thenReturn(generateUsuarioInstance());
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.GET, API.concat("/buscarPorUsername/").concat(username));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}