package com.produtos.apirest.Controller;

import com.produtos.apirest.Util.Util;
import com.produtos.apirest.models.AnimalType;
import com.produtos.apirest.models.DTO.AnimalTypeDTO;
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

import static com.produtos.apirest.Util.Util.buildRequest;
import static com.produtos.apirest.Util.Util.toJson;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
public class AnimalTypeControllerTest {

    private final String API = "/api/animalType";

    @MockBean
    private AnimalTypeService animalTypeService;

    @Autowired
    MockMvc mvc;

    public static AnimalType generateAnimalType(){
        return AnimalType.builder()
                .animalTypeId(1L)
                .name("name")
                .build();
    }

    public AnimalTypeDTO generateAnimalTypeDTO(){
        return AnimalTypeDTO.builder()
                .id(1L)
                .name("name")
                .build();
    }

    @WithUserDetails("Admin")
    @Test
    public void save() throws Exception{
        Mockito.when(animalTypeService.save(Mockito.any(AnimalType.class))).thenReturn(generateAnimalType());
        String json = toJson(generateAnimalTypeDTO());
        MockHttpServletRequestBuilder request = Util.buildRequest(HttpMethod.POST, API, json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @WithUserDetails("Admin")
    @Test
    public void update() throws Exception{
        Mockito.when(animalTypeService.update(Mockito.any(AnimalType.class))).thenReturn(generateAnimalType());
        String json = toJson(generateAnimalTypeDTO());
        MockHttpServletRequestBuilder request = Util.buildRequest(HttpMethod.PUT, API, json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void removeById() throws Exception{
        Long id = 1L;
        Mockito.doNothing().when(animalTypeService).removeById(Mockito.anyLong());
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.DELETE, API.concat("/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void removeByIdWithFeedback() throws Exception{
        Long id = 1L;
        Mockito.when(animalTypeService.removeByIdWithFeedback(Mockito.anyLong())).thenReturn(generateAnimalType());
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.DELETE, API.concat("/feedback/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void findById() throws Exception{
        Long id = 1L;
        Mockito.when(animalTypeService.findById(Mockito.anyLong())).thenReturn(generateAnimalType());
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.GET, API.concat("/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}