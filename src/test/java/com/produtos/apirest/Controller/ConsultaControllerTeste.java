package com.produtos.apirest.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.produtos.apirest.models.*;
import com.produtos.apirest.models.DTO.ConsultaDTO;
import com.produtos.apirest.service.AnimalService;
import com.produtos.apirest.service.ConsultaService;
import com.produtos.apirest.service.TipoConsultaService;
import com.produtos.apirest.service.VeterinarioService;
import org.junit.jupiter.api.Test;
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
public class ConsultaControllerTeste {

    private final String API = "/api/consulta";

    @MockBean
    private ConsultaService consultaService;

    @MockBean
    private AnimalService animalService;

    @MockBean
    private TipoConsultaService tipoConsultaService;

    @MockBean
    private VeterinarioService veterinarioService;

    @Autowired
    public MockMvc mvc;

    @Test
    @WithUserDetails("Admin")
    public void deveSalvar() throws Exception{
        //Cenário
        ConsultaDTO consultadto = ConsultaDTO.builder()
                .animal(new Animal())
                .veterinario(new Veterinario())
                .tipo(new TipoConsulta())
                .descricao("desc")
                .build();

        //Tipo Consulta Mockado
        TipoConsulta tipoConsulta = TipoConsulta.builder()
                .tipoConsultaId(Long.valueOf(1))
                .nome("nome")
                .build();

        //Veterinario Mockado
        Veterinario veterinario = Veterinario.builder()
                .veterinarioId(Long.valueOf(1))
                .nome("Nome")
                .cpf("cpf")
                .especialidade(new Especialidade())
                .telefone("telefone")
                .build();

        //Animal Mockado
        Animal animal = Animal.builder()
                .animalId(Long.valueOf(1))
                .nome("nome")
                .dono(new Dono())
                .tipoAnimal(new TipoAnimal())
                .build();

        //Consulta Mockado
        Consulta consulta = Consulta.builder()
                .tipoConsulta(tipoConsulta)
                .veterinario(veterinario)
                .animal(animal)
                .descricao("desc")
                .build();

        //Mock dos Serviços
        Mockito.when(consultaService.salvar(Mockito.any(Consulta.class))).thenReturn(consulta);
        Mockito.when(tipoConsultaService.buscarPorId(Mockito.anyLong())).thenReturn(tipoConsulta);
        Mockito.when(animalService.buscarPorId(Mockito.anyLong())).thenReturn(animal);
        Mockito.when(veterinarioService.buscarPorId(Mockito.anyLong())).thenReturn(veterinario);

        // Serializar consultaDTO para Json
        String json = new ObjectMapper().writeValueAsString(consultadto);

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
        ConsultaDTO consultaDTORequest = ConsultaDTO.builder()
                .animalId(Long.valueOf(1))
                .tipoConsultaId(Long.valueOf(1))
                .veterinarioId(Long.valueOf(1))
                .descricao("desc")
                .build();

        //Consulta Mockada
        Consulta consulta = Consulta.builder()
                .consultaId(Long.valueOf(1))
                .animal(new Animal())
                .veterinario(new Veterinario())
                .tipoConsulta(new TipoConsulta())
                .descricao("desc")
                .build();

        //Mock Serviços
        Mockito.when(consultaService.atualizar(Mockito.any(Consulta.class))).thenReturn(consulta);

        // Serializar AnimalDTO para Json
        String json = new ObjectMapper().writeValueAsString(consultaDTORequest);

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
    public void deveRemoverPorIdController() throws Exception{
        //Cenário
        Long id = Long.valueOf(1);

        //Mock Serviço
        Mockito.doNothing().when(consultaService).removerPorId(isA(Long.class));

        //Montando a Requisiçaõ
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(API.concat("/remover/").concat(String.valueOf(id)));

        //Ação e Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("\"NO_CONTENT\""));
    }

    @WithUserDetails("Admin")
    @Test
    public void deveBuscarTodos() throws Exception{
        //Cenário
        List<Consulta> consultas = new ArrayList<>();
        consultas.add(
                Consulta.builder()
                        .consultaId(Long.valueOf(1))
                        .descricao("desc")
                        .veterinario(new Veterinario())
                        .tipoConsulta(new TipoConsulta())
                        .animal(new Animal())
                        .build()
        );

        //Mock Serviço
        Mockito.when(consultaService.buscarTodos()).thenReturn(consultas);

        //Montando Requisição
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(API.concat("/buscarTodos"));

        //Ação e Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveRemoverComFeedback() throws Exception{
        //Cenário
        Long id = Long.valueOf(1);

        //Consulta Mockada
        Consulta consulta = Consulta.builder()
                .consultaId(Long.valueOf(1))
                .animal(new Animal())
                .veterinario(new Veterinario())
                .tipoConsulta(new TipoConsulta())
                .descricao("desc")
                .build();

        //Mock Serviço
        Mockito.when(consultaService.removerComFeedback(Mockito.anyLong())).thenReturn(consulta);

        //Requisição
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(API.concat("/remover/feedback/").concat(String.valueOf(id)));

        //Ação e Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
