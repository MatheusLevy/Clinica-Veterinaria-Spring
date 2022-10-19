package com.produtos.apirest.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.produtos.apirest.models.DTO.VeterinarioDTO;
import com.produtos.apirest.models.Veterinario;
import com.produtos.apirest.service.VeterinarioService;
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

import java.util.ArrayList;
import java.util.List;

import static com.produtos.apirest.Controller.EspecialidadeControllerTeste.getEspecialidadeInstance;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
public class VeterinarioControllerTeste {

    private final String API = "/api/veterinario";

    @MockBean
    private VeterinarioService veterinarioService;

    @Autowired
    MockMvc mvc;

    public static Veterinario getVeterinarioInstance(){
        return Veterinario.builder()
                .veterinarioId(Long.valueOf(1))
                .nome("nome")
                .telefone("telefone")
                .cpf("cpf")
                .especialidade(getEspecialidadeInstance(true))
                .build();
    }

    public static VeterinarioDTO getVeterinarioDTOInstance(){
        return VeterinarioDTO.builder()
                .id(Long.valueOf(1))
                .nome("nome")
                .telefone("telefone")
                .cpf("cpf")
                .especialidade(getEspecialidadeInstance(true))
                .build();
    }


    @WithUserDetails("Admin")
    @Test
    public void deveSalvarController() throws Exception{
        //Cenário
        VeterinarioDTO veterinarioDTO = getVeterinarioDTOInstance();

        //Veterinario Mockado
        Veterinario veterinario = getVeterinarioInstance();

        //Mock do serviço
        Mockito.when(veterinarioService.salvar(Mockito.any(Veterinario.class))).thenReturn(veterinario);

        // Serializar veterinarioDTO para Json
        String json = new ObjectMapper().writeValueAsString(veterinarioDTO);

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
    public void deveRemoverPorId() throws Exception{
        //Cenário
        Long id = Long.valueOf(1);

        //Mock do Serviço
        Mockito.doNothing().when(veterinarioService).remover(Mockito.anyLong());

        //Motando Requisição
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

        //Veterinario Mockado
        Veterinario veterinario = getVeterinarioInstance();

        //Mock do Serviço
        Mockito.when(veterinarioService.removerComFeedback(Mockito.anyLong())).thenReturn(veterinario);

        //Motando Requisição
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(API.concat("/remover/feedback/").concat(String.valueOf(id)));

        //Ação e Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveBuscarPorId() throws Exception{
        //Cenário
        Long id = Long.valueOf(1);

        //Veterinario Mockado
        Veterinario veterinario = getVeterinarioInstance();

        //Mock do Serviço
        Mockito.when(veterinarioService.buscarPorId(Mockito.anyLong())).thenReturn(veterinario);

        //Motando Requisição
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(API.concat("/buscar/").concat(String.valueOf(id)));

        //Ação e Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveBuscarTodos() throws Exception{
        //Mock da Lista
        List<Veterinario> veterinarios = new ArrayList<>();
        veterinarios.add(getVeterinarioInstance());

        //Mock do Serviço
        Mockito.when(veterinarioService.buscarTodos()).thenReturn(veterinarios);

        //Motando Requisição
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(API.concat("/buscarTodos/"));

        //Ação e Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveAtualizar() throws Exception{
        //Cenário
        VeterinarioDTO veterinarioDTO = getVeterinarioDTOInstance();

        //Veterinario Mockado
        Veterinario veterinario = getVeterinarioInstance();

        //Mock do Serviço
        Mockito.when(veterinarioService.atualizar(Mockito.any(Veterinario.class))).thenReturn(veterinario);

        // Serializar veterinarioDTO para Json
        String json = new ObjectMapper().writeValueAsString(veterinarioDTO);

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
