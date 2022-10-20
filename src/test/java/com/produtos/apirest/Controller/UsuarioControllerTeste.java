package com.produtos.apirest.Controller;

import com.produtos.apirest.models.DTO.UsuarioDTO;
import com.produtos.apirest.models.Usuario;
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

    public static UsuarioDTO getUsuarioDTOInstance(){
        return UsuarioDTO.builder()
                .id(Long.valueOf(1))
                .username("username")
                .senha("senha")
                .build();
    }

    public static Usuario getUsuarioInstance(){
        return Usuario.builder()
                .usuarioId(Long.valueOf(1))
                .username("username")
                .senha("senha")
                .build();
    }

    @Test
    public void deveAutenticarController() throws Exception{
        Mockito.when(usuarioService.autenticar(Mockito.anyString(), Mockito.anyString())).thenReturn(getUsuarioInstance());
        String json = toJson(getUsuarioDTOInstance());
        MockHttpServletRequestBuilder request = request(HttpMethod.GET, API.concat("/autenticar"), json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveSalvarController() throws Exception{
        Mockito.when(usuarioService.salvar(Mockito.any(Usuario.class))).thenReturn(getUsuarioInstance());
        String json = toJson(getUsuarioDTOInstance());
        MockHttpServletRequestBuilder request = request(HttpMethod.POST, API.concat("/salvar"), json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveAtualizarController() throws Exception{
        Mockito.when(usuarioService.atualizar(Mockito.any(Usuario.class))).thenReturn(getUsuarioInstance());
        String json = toJson(getUsuarioDTOInstance());
        MockHttpServletRequestBuilder request = request(HttpMethod.PUT, API.concat("/atualizar"), json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveRemoverComIdController() throws Exception{
        Long id = Long.valueOf(1);
        Mockito.when(usuarioService.buscarPorId(Mockito.anyLong())).thenReturn(getUsuarioInstance());
        Mockito.doNothing().when(usuarioService).remover(Mockito.any(Usuario.class));
        MockHttpServletRequestBuilder request = request(HttpMethod.DELETE, API.concat("/remover/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveRemoverComFeedback() throws Exception{
        Long id = Long.valueOf(1);
        Mockito.when(usuarioService.removerComFeedback(Mockito.anyLong())).thenReturn(getUsuarioInstance());
        MockHttpServletRequestBuilder request = request(HttpMethod.DELETE, API.concat("/remover/feedback/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveBuscarPorIdController() throws Exception{
        Long id = Long.valueOf(1);
        Mockito.when(usuarioService.buscarPorId(Mockito.anyLong())).thenReturn(getUsuarioInstance());
        MockHttpServletRequestBuilder request = request(HttpMethod.GET, API.concat("/buscar/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveBuscarPorUsernameController() throws Exception{
        String username = "username";
        Mockito.when(usuarioService.buscarPorUsername(Mockito.anyString())).thenReturn(getUsuarioInstance());
        MockHttpServletRequestBuilder request = request(HttpMethod.GET, API.concat("/buscarPorUsername/").concat(username));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}