package com.produtos.apirest.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.produtos.apirest.models.Animal;
import com.produtos.apirest.models.DTO.AnimalDTO;
import com.produtos.apirest.models.Dono;
import com.produtos.apirest.models.TipoAnimal;
import com.produtos.apirest.service.AnimalService;
import com.produtos.apirest.service.DonoService;
import com.produtos.apirest.service.TipoAnimalService;
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
import java.util.Random;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
public class AnimalControllerTeste {

    private final String API = "/api/animal";

    @MockBean
    private AnimalService animalService;

    @Autowired
    public MockMvc mvc;

    @MockBean
    private DonoService donoService;

    @MockBean
    private TipoAnimalService tipoAnimalService;

    Random random = new Random();

    @Test
    @WithUserDetails("Admin")
    public void deveSalvarController() throws Exception{

        //# Cenário
        //Criando um dono
        Dono dono = Dono.builder()
                .nome("teste")
                .cpf(String.valueOf(random.nextInt(9999999)))
                .telefone("123")
                .build();

        //Criando Tipo Animal
        TipoAnimal tipo = TipoAnimal.builder()
                .nome("Teste")
                .build();

        //## Request AnimalDTO
        AnimalDTO animalDTORequest = AnimalDTO.builder()
                .nome("nome")
                .idDono(Long.valueOf(1))
                .idTipoAnimal(Long.valueOf(1))
                .build();

        //## Animal mockado
        Animal animal = Animal.builder()
                .animalId(Long.valueOf(1))
                .nome(animalDTORequest.getNome())
                .dono(new Dono())
                .tipoAnimal(new TipoAnimal())
                .build();

        //## Mock do Serviço dos serviços
        Mockito.when(animalService.salvar(Mockito.any(Animal.class))).thenReturn(animal);
        Mockito.when(donoService.buscarDonoPorId(Mockito.anyLong())).thenReturn(dono);
        Mockito.when(tipoAnimalService.buscarTipoAnimalPorId(Mockito.anyLong())).thenReturn(tipo);

        //## Serializar AnimalDTO para Json
        String json = new ObjectMapper().writeValueAsString(animalDTORequest);

        //## Motando Requisição para o Controlador
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API.concat("/salvar"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        //# Ação e Verificação | Enviando o Request e checando a Resposta
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @WithUserDetails("Admin")
    public void deveRemoverController() throws Exception{
        // Cenário
        Long id = Long.valueOf(1);

        //Mock Serviço de RemoverPorId
        doNothing().when(animalService).removerPorId(isA(Long.class));

        //Montando Requisição
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(API.concat("/remover/").concat(String.valueOf(id)));

        //Ação e Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("\"NO_CONTENT\""));

    }

    @Test
    @WithUserDetails("Admin")
    public void deveRemoverComFeedbackController() throws Exception{
        //Cenário
        Long id = Long.valueOf(1);

        // Animal Mockado
        Animal animal = Animal.builder()
                .animalId(Long.valueOf(1))
                .nome("Nome")
                .dono(new Dono())
                .tipoAnimal(new TipoAnimal())
                .build();

        //Mockando Serviço de RemoverComFeedback
        Mockito.when(animalService.removerFeedback(Mockito.anyLong())).thenReturn(animal);


        // Motando Requisição para o Controlador
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(API.concat("/remover/feedback/").concat(String.valueOf(id)));

        //Ação e Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithUserDetails("Admin")
    public void deveBuscarPorIdController() throws Exception{
        //Cenário
        Long id = Long.valueOf(1);

        //Animal Mockado
        Animal animal = Animal.builder()
                .animalId(Long.valueOf(1))
                .nome("Nome")
                .dono(new Dono())
                .tipoAnimal(new TipoAnimal())
                .build();

        //Mockando Serviço de BuscarPorId
        Mockito.when(animalService.buscarPorId(Mockito.anyLong())).thenReturn(animal);

        // Motando Requisição para o Controlador
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(API.concat("/buscarId/").concat(String.valueOf(id)));

        //Ação e Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithUserDetails("Admin")
    public void deveAtualizarController() throws Exception{
        //Cenário
        AnimalDTO animalDTORequest = AnimalDTO.builder()
                .id(Long.valueOf(1))
                .nome("nome")
                .dono(new Dono())
                .tipo(new TipoAnimal())
                .build();

        //Animal Mockado
        Animal animal = Animal.builder()
                .nome(animalDTORequest.getNome())
                .dono(animalDTORequest.getDono())
                .tipoAnimal(animalDTORequest.getTipo())
                .build();

        //Mockando Serviço de Atualizar
        Mockito.when(animalService.atualizar(Mockito.any(Animal.class))).thenReturn(animal);

        //## Serializar AnimalDTO para Json
        String json = new ObjectMapper().writeValueAsString(animalDTORequest);

        //Motando Requisição para o Controlador
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(API.concat("/atualizar"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        //Ação e Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithUserDetails("Admin")
    public void deveBuscarDonoController() throws Exception{
        //Cenário
        Long id = Long.valueOf(1);

        //Dono mockado
        Dono dono = Dono.builder()
                .donoId(Long.valueOf(1))
                .nome("nome")
                .telefone("telefone")
                .cpf("cpf")
                .build();

        //Mockand Serviço de Animal buscarDono
        Mockito.when(animalService.buscarDono(Mockito.anyLong())).thenReturn(dono);

        //Montando Requisição para o Controller
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(API.concat("/buscarDono/").concat(String.valueOf(id)));

        //Ação e Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithUserDetails("Admin")
    public void deveAtualizarDonoController() throws Exception{
        //Cenário
        Dono donoBuscado = Dono.builder()
                .donoId(Long.valueOf(1))
                .nome("dono")
                .cpf(String.valueOf(random.nextInt(9999999)))
                .telefone("123")
                .build();

        Animal animalBuscado = Animal.builder()
                .animalId(Long.valueOf(1))
                .nome("nome")
                .tipoAnimal(new TipoAnimal())
                .dono(new Dono())
                .build();


        AnimalDTO animalDTORequest = AnimalDTO.builder()
                .id(Long.valueOf(1))
                .nome("nome")
                .idDono(Long.valueOf(1))
                .idTipoAnimal(Long.valueOf(1))
                .build();

        //Mock dos Serviços
        Mockito.when(donoService.buscarDonoPorId(Mockito.anyLong())).thenReturn(donoBuscado);
        Mockito.when(animalService.buscarPorId(Mockito.anyLong())).thenReturn(animalBuscado);
        Mockito.when(animalService.atualizarDono(Mockito.any(Dono.class), Mockito.any(Animal.class))).thenReturn(animalBuscado);

        //Serializar AnimalDTO para Json
        String json = new ObjectMapper().writeValueAsString(animalDTORequest);

        //Motando Requisição para o Controlador
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(API.concat("/atualizar/dono"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        //Ação e Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
