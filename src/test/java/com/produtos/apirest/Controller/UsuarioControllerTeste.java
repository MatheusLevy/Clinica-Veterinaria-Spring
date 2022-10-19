package com.produtos.apirest.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.produtos.apirest.models.DTO.UsuarioDTO;
import com.produtos.apirest.models.Usuario;
import com.produtos.apirest.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
public class UsuarioControllerTeste {

    private final String API = "/api/usuario";

    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    MockMvc mvc;

    public static UsuarioDTO getUsuarioDTOInstance(Boolean temId){
        UsuarioDTO usuarioDTO = UsuarioDTO.builder()
                .username("username")
                .senha("senha")
                .nivel("nivel")
                .build();
        if (temId)
            usuarioDTO.setId(Long.valueOf(1));
        return usuarioDTO;
    }

    public static Usuario getUsuarioInstance(Boolean temId){
        Usuario usuario = Usuario.builder()
                .username("username")
                .senha("senha")
                .nivel("nivel")
                .build();
        if (temId)
            usuario.setUsuarioId(Long.valueOf(1));
        return usuario;
    }

    @Test
    public void deveAutenticarController() throws Exception{
        //Cenário
        UsuarioDTO usuarioDTO = getUsuarioDTOInstance(false);

        //Usuario Mockado
        Usuario usuario = getUsuarioInstance(true);

        //Mock do Serviço
        Mockito.when(usuarioService.autenticar(Mockito.anyString(), Mockito.anyString())).thenReturn(usuario);

        // Serializar usuarioDTO para Json
        String json = new ObjectMapper().writeValueAsString(usuarioDTO);

        //Motando Requisição
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(API.concat("/autenticar"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        //Ação e Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @WithUserDetails("Admin")
    @Test
    public void deveSalvarController() throws Exception{
        //Cenário
        UsuarioDTO usuarioDTO = getUsuarioDTOInstance(false);

        //Usuario Mockado
        Usuario usuario = getUsuarioInstance(true);

        //Mock do Serviço
        Mockito.when(usuarioService.salvar(Mockito.any(Usuario.class))).thenReturn(usuario);

        // Serializar usuarioDTO para Json
        String json = new ObjectMapper().writeValueAsString(usuarioDTO);

        //Motando Requisição
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API.concat("/salvar"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        //Ação e Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveRemoverComIdController() throws Exception{
        //Cenário
        Long id = Long.valueOf(1);

        //Usuario Mockado
        Usuario usuario = getUsuarioInstance(true);

        //Mock do Serviço
        Mockito.when(usuarioService.buscarPorId(Mockito.anyLong())).thenReturn(usuario);
        Mockito.doNothing().when(usuarioService).remover(Mockito.any(Usuario.class));

        //Montando Requisição
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(API.concat("/remover/").concat(String.valueOf(id)));

        //Ação e Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @WithUserDetails("Admin")
    @Test
    public void deveRemoverComFeedback() throws Exception{
        //Cenário
        Long id = Long.valueOf(1);

        //Usuario Mockado
        Usuario usuario = getUsuarioInstance(true);

        //Mock do Serviço
        Mockito.when(usuarioService.removerComFeedback(Mockito.anyLong())).thenReturn(usuario);

        //Montando Requisição
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(API.concat("/remover/feedback/").concat(String.valueOf(id)));

        //Ação e Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveBuscarPorIdController() throws Exception{
        //Cenário
        Long id = Long.valueOf(1);

        //Usuario Mockado
        Usuario usuario = getUsuarioInstance(true);

        //Mock do Serviço
        Mockito.when(usuarioService.buscarPorId(Mockito.anyLong())).thenReturn(usuario);

        //Montando Requisição
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(API.concat("/buscar/").concat(String.valueOf(id)));

        //Ação e Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveBuscarPorUsernameController() throws Exception{
        //Cenario
        String username = "username";

        //Usuario Mockado
        Usuario usuario = getUsuarioInstance(true);

        //Mock do serviço
        Mockito.when(usuarioService.buscarPorUsername(Mockito.anyString())).thenReturn(usuario);

        //Montando Requisição
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(API.concat("/buscarPorUsername/").concat(username));

        //Ação e Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveAtualizarController() throws Exception{
        //Cenário
        UsuarioDTO usuarioDTO = getUsuarioDTOInstance(true);

        //Usario Mockado
        Usuario usuario = getUsuarioInstance(true);

        //Mock do serviço
        Mockito.when(usuarioService.atualizar(Mockito.any(Usuario.class))).thenReturn(usuario);

        // Serializar usuarioDTO para Json
        String json = new ObjectMapper().writeValueAsString(usuarioDTO);

        //Motando Requisição
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(API.concat("/atualizar"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        //Ação e Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
