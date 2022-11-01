package com.produtos.apirest.Controller;

import com.produtos.apirest.models.DTO.UserDTO;
import com.produtos.apirest.models.User;
import com.produtos.apirest.service.UsuarioService;
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

import static com.produtos.apirest.Util.Util.request;
import static com.produtos.apirest.Util.Util.toJson;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
public class UsuarioControllerTeste {

    private final String API = "/api/usuario";

    @MockBean
    private UsuarioService usuarioService;

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
        Mockito.when(usuarioService.autenticar(Mockito.anyString(), Mockito.anyString())).thenReturn(generateUsuarioInstance());
        String json = toJson(generateUsuarioDTOInstance());
        MockHttpServletRequestBuilder request = request(HttpMethod.GET, API.concat("/autenticar"), json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveSalvar() throws Exception{
        Mockito.when(usuarioService.salvar(Mockito.any(User.class))).thenReturn(generateUsuarioInstance());
        String json = toJson(generateUsuarioDTOInstance());
        MockHttpServletRequestBuilder request = request(HttpMethod.POST, API.concat("/salvar"), json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveAtualizar() throws Exception{
        Mockito.when(usuarioService.atualizar(Mockito.any(User.class))).thenReturn(generateUsuarioInstance());
        String json = toJson(generateUsuarioDTOInstance());
        MockHttpServletRequestBuilder request = request(HttpMethod.PUT, API.concat("/atualizar"), json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveRemoverComId() throws Exception{
        Long id = 1L;
        Mockito.when(usuarioService.buscarPorId(Mockito.anyLong())).thenReturn(generateUsuarioInstance());
        Mockito.doNothing().when(usuarioService).remover(Mockito.any(User.class));
        MockHttpServletRequestBuilder request = request(HttpMethod.DELETE, API.concat("/remover/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveRemoverComFeedback() throws Exception{
        Long id = 1L;
        Mockito.when(usuarioService.removerComFeedback(Mockito.anyLong())).thenReturn(generateUsuarioInstance());
        MockHttpServletRequestBuilder request = request(HttpMethod.DELETE, API.concat("/remover/feedback/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveBuscarPorId() throws Exception{
        Long id = 1L;
        Mockito.when(usuarioService.buscarPorId(Mockito.anyLong())).thenReturn(generateUsuarioInstance());
        MockHttpServletRequestBuilder request = request(HttpMethod.GET, API.concat("/buscar/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveBuscarPorUsername() throws Exception{
        String username = "username";
        Mockito.when(usuarioService.buscarPorUsername(Mockito.anyString())).thenReturn(generateUsuarioInstance());
        MockHttpServletRequestBuilder request = request(HttpMethod.GET, API.concat("/buscarPorUsername/").concat(username));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}