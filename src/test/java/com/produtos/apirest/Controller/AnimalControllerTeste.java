package com.produtos.apirest.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.produtos.apirest.Util.Util;
import com.produtos.apirest.models.Animal;
import com.produtos.apirest.models.DTO.AnimalDTO;
import com.produtos.apirest.models.Owner;
import com.produtos.apirest.service.AnimalService;
import com.produtos.apirest.service.OwnerService;
import com.produtos.apirest.service.AnimalTypeService;
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

import static com.produtos.apirest.Controller.DonoControllerTeste.generateOwner;
import static com.produtos.apirest.Controller.TipoAnimalControllerTeste.generateTipoAnimalInstance;
import static com.produtos.apirest.Util.Util.buildRequest;
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
    private OwnerService ownerService;

    @MockBean
    private AnimalTypeService animalTypeService;

    public static List<Animal> generateAnimalList(){
        return new ArrayList<>(){{
            add(generateAnimalInstance());
        }};
    }

    public static Animal generateAnimalInstance(){
        return Animal.builder()
                .animalId(1L)
                .name("name")
                .owner(generateOwner())
                .animalType(generateTipoAnimalInstance())
                .build();
    }

    public static AnimalDTO generateAnimalDTOInstance(){
        return AnimalDTO.builder()
                .id(1L)
                .animalTypeId(1L)
                .name("name")
                .ownerId(1L)
                .animalTypeId(1L)
                .owner(generateOwner())
                .type(generateTipoAnimalInstance())
                .build();
    }
    @Test
    @WithUserDetails("Admin")
    public void deveSalvar() throws Exception{
        Mockito.when(animalService.save(Mockito.any(Animal.class))).thenReturn(generateAnimalInstance());
        Mockito.when(ownerService.findById(Mockito.anyLong())).thenReturn(generateOwner());
        Mockito.when(animalTypeService.findById(Mockito.anyLong())).thenReturn(generateTipoAnimalInstance());
        String json = new ObjectMapper().writeValueAsString(generateAnimalDTOInstance());
        MockHttpServletRequestBuilder request = Util.buildRequest(HttpMethod.POST, API, json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @WithUserDetails("Admin")
    public void deveAtualizar() throws Exception{
        Mockito.when(animalService.update(Mockito.any(Animal.class))).thenReturn(generateAnimalInstance());
        String json = toJson(generateAnimalDTOInstance());
        MockHttpServletRequestBuilder request = Util.buildRequest(HttpMethod.PUT, API, json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithUserDetails("Admin")
    public void deveAtualizarDono() throws Exception{
        Mockito.when(ownerService.findById(Mockito.anyLong())).thenReturn(generateOwner());
        Mockito.when(animalService.findById(Mockito.anyLong())).thenReturn(generateAnimalInstance());
        Mockito.when(animalService.updateOwner(Mockito.any(Animal.class), Mockito.any(Owner.class))).thenReturn(generateAnimalInstance());
        String json = toJson(generateAnimalDTOInstance());
        MockHttpServletRequestBuilder request = Util.buildRequest(HttpMethod.PUT, API.concat("/owner"), json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithUserDetails("Admin")
    public void deveRemover() throws Exception{
        Long id = 1L;
        doNothing().when(animalService).removeById(isA(Long.class));
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.DELETE, API.concat("/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("\"NO_CONTENT\""));
    }

    @Test
    @WithUserDetails("Admin")
    public void deveRemoverComFeedback() throws Exception{
        Long id = 1L;
        Mockito.when(animalService.removeByIdWithFeedback(Mockito.anyLong())).thenReturn(generateAnimalInstance());
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.DELETE, API.concat("/feedback/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithUserDetails("Admin")
    public void deveBuscarPorId() throws Exception{
        Long id = 1L;
        Mockito.when(animalService.findById(Mockito.anyLong())).thenReturn(generateAnimalInstance());
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.GET, API.concat("/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithUserDetails("Admin")
    public void deveBuscarDono() throws Exception{
        Long id = 1L;
        Mockito.when(animalService.findOwnerByAnimalId(Mockito.anyLong())).thenReturn(generateOwner());
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.GET, API.concat("/owner/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}