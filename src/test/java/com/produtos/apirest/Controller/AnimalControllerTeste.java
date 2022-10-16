package com.produtos.apirest.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.produtos.apirest.autenticacao.SecurityConfig;
import com.produtos.apirest.models.Animal;
import com.produtos.apirest.models.DTO.AnimalDTO;
import com.produtos.apirest.models.Dono;
import com.produtos.apirest.models.TipoAnimal;
import com.produtos.apirest.service.AnimalService;
import com.produtos.apirest.service.DonoService;
import com.produtos.apirest.service.TipoAnimalService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.util.Random;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
public class AnimalControllerTeste {

    private final String API = "/api/animal";

    @MockBean
    private AnimalService animalService;


    @Autowired
    private WebApplicationContext context;

    @Autowired
    public MockMvc mvc;

    @Autowired
    private DonoService donoService;

    @Autowired
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
        Dono donoRetorno = donoService.salvar(dono);

        //Criando Tipo Animal
        TipoAnimal tipo = TipoAnimal.builder()
                .nome("Teste")
                .build();
        TipoAnimal tipoRetorno = tipoAnimalService.salvar(tipo);
        //## Request AnimalDTO


        AnimalDTO animalDTORequest = AnimalDTO.builder()
                .nome("nome")
                .idDono(donoRetorno.getDonoId())
                .idTipoAnimal(tipoRetorno.getTipoAnimalId())
                .build();

        //## Animal mockado
        Animal animal = Animal.builder()
                .animalId(Long.valueOf(1))
                .nome(animalDTORequest.getNome())
                .dono(donoRetorno)
                .tipoAnimal(tipoRetorno)
                .build();

        //## Mock do Serviço de Salvar
        Mockito.when(animalService.salvar(Mockito.any(Animal.class))).thenReturn(animal);

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

        //Rollback
        tipoAnimalService.remover(tipoRetorno);
        donoService.remover(donoRetorno);
    }
}
