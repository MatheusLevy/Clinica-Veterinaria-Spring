package com.produtos.apirest.Controller;

import com.produtos.apirest.Util.Util;
import com.produtos.apirest.models.DTO.OwnerDTO;
import com.produtos.apirest.models.Owner;
import com.produtos.apirest.service.OwnerService;
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

import java.util.Arrays;
import java.util.List;

import static com.produtos.apirest.Controller.AnimalControllerTest.generateAnimalList;
import static com.produtos.apirest.Util.Util.buildRequest;
import static com.produtos.apirest.Util.Util.toJson;
import static org.mockito.ArgumentMatchers.isA;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
public class OwnerControllerTest {

    private final String API = "/api/owner";

    @MockBean
    public OwnerService ownerService;

    @Autowired
    MockMvc mvc;

    protected static Owner generateOwner(){
        return Owner.builder()
                .ownerId(1L)
                .name("name")
                .cpf("cpf")
                .phone("phone")
                .build();
    }

    public static OwnerDTO generateOwnerDTO(){
        return OwnerDTO.builder()
                .id(1L)
                .name("name")
                .cpf("cpf")
                .phone("phone")
                .build();
    }

    public static List<Owner> generateOwnerList(){
        return Arrays.asList(generateOwner());
    }

    @WithUserDetails("Admin")
    @Test
    public void save() throws Exception{
        Mockito.when(ownerService.save(Mockito.any(Owner.class))).thenReturn(generateOwner());
        String json = toJson(generateOwnerDTO());
        MockHttpServletRequestBuilder request = Util.buildRequest(HttpMethod.POST, API, json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @WithUserDetails("Admin")
    @Test
    public void update() throws Exception{
        Mockito.when(ownerService.update(Mockito.any(Owner.class))).thenReturn(generateOwner());
        String json = toJson(generateOwnerDTO());
        MockHttpServletRequestBuilder request = Util.buildRequest(HttpMethod.PUT, API, json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void removeById() throws Exception{
        Long id = 1L;
        Mockito.doNothing().when(ownerService).removeById(isA(Long.class));
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.DELETE, API.concat("/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("\"NO_CONTENT\""));
    }

    @WithUserDetails("Admin")
    @Test
    public void removeByIdWithFeedback() throws Exception{
        Long id = 1L;
        Mockito.when(ownerService.removeByIdWithFeedback(Mockito.anyLong())).thenReturn(generateOwner());
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.DELETE, API.concat("/feedback/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void find() throws Exception{
        Mockito.when(ownerService.find(Mockito.any(Owner.class))).thenReturn(generateOwnerList());
        String json = toJson(generateOwnerDTO());
        MockHttpServletRequestBuilder request = Util.buildRequest(HttpMethod.GET, API.concat("/filter"), json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void findById() throws Exception{
        Long id = 1L;
        Mockito.when(ownerService.findById(Mockito.anyLong())).thenReturn(generateOwner());
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.GET, API.concat("/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void findAll() throws Exception{
        Mockito.when(ownerService.findAll()).thenReturn(generateOwnerList());
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.GET, API);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void findAllAnimals() throws Exception{
        Long id = 1L;
        Mockito.when(ownerService.findAllAnimalsByOwnerId(Mockito.anyLong())).thenReturn(generateAnimalList());
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.GET, API.concat("/animals/".concat(String.valueOf(id))));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}