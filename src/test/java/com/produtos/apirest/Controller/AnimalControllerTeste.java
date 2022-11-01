package com.produtos.apirest.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.produtos.apirest.models.Animal;
import com.produtos.apirest.models.DTO.AnimalDTO;
import com.produtos.apirest.models.Dono;
import com.produtos.apirest.service.AnimalService;
import com.produtos.apirest.service.DonoService;
import com.produtos.apirest.service.TipoAnimalService;
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

import java.util.ArrayList;
import java.util.List;

import static com.produtos.apirest.Controller.DonoControllerTeste.generateDonoInstance;
import static com.produtos.apirest.Controller.TipoAnimalControllerTeste.generateTipoAnimalInstance;
import static com.produtos.apirest.Util.Util.request;
import static com.produtos.apirest.Util.Util.toJson;
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

    public static List<Animal> generateAnimalListInstance(){
        return new ArrayList<>(){{
            add(generateAnimalInstance());
        }};
    }

    public static Animal generateAnimalInstance(){
        return Animal.builder()
                .animalId(1L)
                .name("name")
                .owner(generateDonoInstance())
                .animalType(generateTipoAnimalInstance())
                .build();
    }

    public static AnimalDTO generateAnimalDTOInstance(){
        return AnimalDTO.builder()
                .id(1L)
                .idTipoAnimal(1L)
                .nome("nome")
                .idDono(1L)
                .idTipoAnimal(1L)
                .dono(generateDonoInstance())
                .tipo(generateTipoAnimalInstance())
                .build();
    }
    @Test
    @WithUserDetails("Admin")
    public void deveSalvar() throws Exception{
        Mockito.when(animalService.salvar(Mockito.any(Animal.class))).thenReturn(generateAnimalInstance());
        Mockito.when(donoService.buscarPorId(Mockito.anyLong())).thenReturn(generateDonoInstance());
        Mockito.when(tipoAnimalService.buscarPorId(Mockito.anyLong())).thenReturn(generateTipoAnimalInstance());
        String json = new ObjectMapper().writeValueAsString(generateAnimalDTOInstance());
        MockHttpServletRequestBuilder request = request(HttpMethod.POST, API.concat("/salvar"), json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @WithUserDetails("Admin")
    public void deveAtualizar() throws Exception{
        Mockito.when(animalService.atualizar(Mockito.any(Animal.class))).thenReturn(generateAnimalInstance());
        String json = toJson(generateAnimalDTOInstance());
        MockHttpServletRequestBuilder request = request(HttpMethod.PUT, API.concat("/atualizar"), json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithUserDetails("Admin")
    public void deveAtualizarDono() throws Exception{
        Mockito.when(donoService.buscarPorId(Mockito.anyLong())).thenReturn(generateDonoInstance());
        Mockito.when(animalService.buscarPorId(Mockito.anyLong())).thenReturn(generateAnimalInstance());
        Mockito.when(animalService.atualizarDono(Mockito.any(Animal.class), Mockito.any(Dono.class))).thenReturn(generateAnimalInstance());
        String json = toJson(generateAnimalDTOInstance());
        MockHttpServletRequestBuilder request = request(HttpMethod.PUT, API.concat("/atualizar/dono"), json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithUserDetails("Admin")
    public void deveRemover() throws Exception{
        Long id = 1L;
        doNothing().when(animalService).removerPorId(isA(Long.class));
        MockHttpServletRequestBuilder request = request(HttpMethod.DELETE, API.concat("/remover/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("\"NO_CONTENT\""));
    }

    @Test
    @WithUserDetails("Admin")
    public void deveRemoverComFeedback() throws Exception{
        Long id = 1L;
        Mockito.when(animalService.removerComFeedback(Mockito.anyLong())).thenReturn(generateAnimalInstance());
        MockHttpServletRequestBuilder request = request(HttpMethod.DELETE, API.concat("/remover/feedback/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithUserDetails("Admin")
    public void deveBuscarPorId() throws Exception{
        Long id = 1L;
        Mockito.when(animalService.buscarPorId(Mockito.anyLong())).thenReturn(generateAnimalInstance());
        MockHttpServletRequestBuilder request = request(HttpMethod.GET, API.concat("/buscarId/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithUserDetails("Admin")
    public void deveBuscarDono() throws Exception{
        Long id = 1L;
        Mockito.when(animalService.buscarDonoPorId(Mockito.anyLong())).thenReturn(generateDonoInstance());
        MockHttpServletRequestBuilder request = request(HttpMethod.GET, API.concat("/buscarDono/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}