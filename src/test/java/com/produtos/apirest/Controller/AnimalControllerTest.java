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

import static com.produtos.apirest.Controller.OwnerControllerTest.generateOwner;
import static com.produtos.apirest.Controller.AnimalTypeControllerTest.generateAnimalType;
import static com.produtos.apirest.Util.Util.buildRequest;
import static com.produtos.apirest.Util.Util.toJson;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
public class AnimalControllerTest {

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
            add(generateAnimal());
        }};
    }

    public static Animal generateAnimal(){
        return Animal.builder()
                .animalId(1L)
                .name("name")
                .owner(generateOwner())
                .animalType(generateAnimalType())
                .build();
    }

    public static AnimalDTO generateAnimalDTO(){
        return AnimalDTO.builder()
                .id(1L)
                .animalTypeId(1L)
                .name("name")
                .ownerId(1L)
                .animalTypeId(1L)
                .owner(generateOwner())
                .type(generateAnimalType())
                .build();
    }
    @Test
    @WithUserDetails("Admin")
    public void save() throws Exception{
        Mockito.when(animalService.save(Mockito.any(Animal.class))).thenReturn(generateAnimal());
        Mockito.when(ownerService.findById(Mockito.anyLong())).thenReturn(generateOwner());
        Mockito.when(animalTypeService.findById(Mockito.anyLong())).thenReturn(generateAnimalType());
        String json = new ObjectMapper().writeValueAsString(generateAnimalDTO());
        MockHttpServletRequestBuilder request = Util.buildRequest(HttpMethod.POST, API, json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @WithUserDetails("Admin")
    public void update() throws Exception{
        Mockito.when(animalService.update(Mockito.any(Animal.class))).thenReturn(generateAnimal());
        String json = toJson(generateAnimalDTO());
        MockHttpServletRequestBuilder request = Util.buildRequest(HttpMethod.PUT, API, json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithUserDetails("Admin")
    public void updateOwner() throws Exception{
        Mockito.when(ownerService.findById(Mockito.anyLong())).thenReturn(generateOwner());
        Mockito.when(animalService.findById(Mockito.anyLong())).thenReturn(generateAnimal());
        Mockito.when(animalService.updateOwner(Mockito.any(Animal.class), Mockito.any(Owner.class))).thenReturn(generateAnimal());
        String json = toJson(generateAnimalDTO());
        MockHttpServletRequestBuilder request = Util.buildRequest(HttpMethod.PUT, API.concat("/owner"), json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithUserDetails("Admin")
    public void removeById() throws Exception{
        Long id = 1L;
        doNothing().when(animalService).removeById(isA(Long.class));
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.DELETE, API.concat("/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("\"NO_CONTENT\""));
    }

    @Test
    @WithUserDetails("Admin")
    public void removeByIdWithFeedback() throws Exception{
        Long id = 1L;
        Mockito.when(animalService.removeByIdWithFeedback(Mockito.anyLong())).thenReturn(generateAnimal());
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.DELETE, API.concat("/feedback/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithUserDetails("Admin")
    public void findById() throws Exception{
        Long id = 1L;
        Mockito.when(animalService.findById(Mockito.anyLong())).thenReturn(generateAnimal());
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.GET, API.concat("/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithUserDetails("Admin")
    public void findOwnerByAnimalId() throws Exception{
        Long id = 1L;
        Mockito.when(animalService.findOwnerByAnimalId(Mockito.anyLong())).thenReturn(generateOwner());
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.GET, API.concat("/owner/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}