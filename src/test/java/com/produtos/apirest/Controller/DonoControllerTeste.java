package com.produtos.apirest.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.produtos.apirest.models.Animal;
import com.produtos.apirest.models.DTO.DonoDTO;
import com.produtos.apirest.models.Dono;
import com.produtos.apirest.service.DonoService;
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

import static org.mockito.ArgumentMatchers.isA;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
public class DonoControllerTeste {

    private final String API = "/api/dono";

    @MockBean
    public DonoService donoService;

    @Autowired
    MockMvc mvc;

    @WithUserDetails("Admin")
    @Test
    public void deveSalvarController() throws Exception{
        //Cenário
        DonoDTO donodto = DonoDTO.builder()
                .nome("Nome")
                .cpf("cpf")
                .telefone("telefone")
                .build();

        //Dono Mockado
        Dono dono = Dono.builder()
                .donoId(Long.valueOf(1))
                .nome(donodto.getNome())
                .telefone(donodto.getTelefone())
                .cpf(donodto.getCpf())
                .build();

        //Mock Serviços
        Mockito.when(donoService.salvar(Mockito.any(Dono.class))).thenReturn(dono);

        //Serializar donodto para Json
        String json = new ObjectMapper().writeValueAsString(donodto);

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
    public void deveAtualizarController() throws Exception{
        //Cenário
        DonoDTO donodto = DonoDTO.builder()
                .id(Long.valueOf(1))
                .nome("nome")
                .telefone("telefone")
                .cpf("cpf")
                .build();

        //Dono Mockado
        Dono dono = Dono.builder()
                .donoId(Long.valueOf(1))
                .nome(donodto.getNome())
                .telefone(donodto.getTelefone())
                .cpf(donodto.getCpf())
                .build();

        //Mock Serviços
        Mockito.when(donoService.atualizar(Mockito.any(Dono.class))).thenReturn(dono);

        //Serializar donodto para Json
        String json = new ObjectMapper().writeValueAsString(donodto);

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

    @WithUserDetails("Admin")
    @Test
    public void deveRemoverController() throws Exception{
        //Cenário
        Long id = Long.valueOf(1);

        //Mock do Serviço
        Mockito.doNothing().when(donoService).removerPorId(isA(Long.class));

        //Montando a Requisição
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(API.concat("/remover/").concat(String.valueOf(id)));

        //Ação e Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("\"NO_CONTENT\""));
    }

    @WithUserDetails("Admin")
    @Test
    public void deveBuscarTodosController() throws Exception{
        //Lista Mockada
        List<Dono> donos = new ArrayList<>();
        donos.add(
                Dono.builder().build()
        );

        //Mock do Serviço
        Mockito.when(donoService.buscarTodos()).thenReturn(donos);

        //Montando a Requisição
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(API.concat("/buscarTodos"));

        //Ação e Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveBuscarPorId() throws Exception{
        //Cenário
        Long id = Long.valueOf(1);

        //Dono mockado
        Dono dono = Dono.builder()
                .donoId(Long.valueOf(1))
                .nome("nome")
                .cpf("cpf")
                .telefone("telefone")
                .build();

        //Mock do Serviço
        Mockito.when(donoService.buscarDonoPorId(Mockito.anyLong())).thenReturn(dono);

        //Montando a Requisição
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(API.concat("/buscar/").concat(String.valueOf(id)));

        //Ação e Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveRemoverComFeedback() throws Exception{
        //Cenário
        Long id = Long.valueOf(1);

        //Dono Mockado
        Dono dono = Dono.builder()
                .donoId(Long.valueOf(1))
                .nome("nome")
                .telefone("telefone")
                .cpf("cpf")
                .build();

        //Mock do Serviço
        Mockito.when(donoService.removerFeedback(Mockito.anyLong())).thenReturn(dono);

        //Montando a Requisição
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(API.concat("/remover/feedback/").concat(String.valueOf(id)));

        //Ação e Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveBuscarComFiltro() throws Exception{
        //Cenário
        DonoDTO donodto = DonoDTO.builder()
                .id(Long.valueOf(1))
                .nome("nome")
                .cpf("cpf")
                .telefone("telefone")
                .build();

        //Dono mockado
        List<Dono> donos = new ArrayList<>();
        donos.add(
                Dono.builder()
                .donoId(Long.valueOf(1))
                .nome(donodto.getNome())
                .cpf(donodto.getCpf())
                .telefone(donodto.getTelefone())
                .build()
        );
        //Mock dos Serviços
        Mockito.when(donoService.buscar(Mockito.any(Dono.class))).thenReturn(donos);

        //Serializar donodto para Json
        String json = new ObjectMapper().writeValueAsString(donodto);

        //Montando a Requisição
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(API.concat("/buscar/filtro"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        //Ação e Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveBuscarTodosAnimaisController() throws Exception{
        //Cenário
        Long id = Long.valueOf(1);
        //Lista de Animais Mockados
        List<Animal> animais = new ArrayList<>();
        animais.add(Animal.builder()
                .animalId(Long.valueOf(1))
                .nome("nome")
                .build());

        //Mock do Serviço
        Mockito.when(donoService.buscarTodosAnimais(Mockito.anyLong())).thenReturn(animais);

        //Montando a Requisição
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(API.concat("/animais/".concat(String.valueOf(id))));

        //Ação e Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
