package com.produtos.apirest.Controller;

import com.produtos.apirest.Util.Util;
import com.produtos.apirest.models.DTO.VeterinaryDTO;
import com.produtos.apirest.models.Veterinary;
import com.produtos.apirest.service.ExpertiseService;
import com.produtos.apirest.service.VeterinaryService;
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

import java.util.Collections;
import java.util.List;

import static com.produtos.apirest.Controller.ExpertiseControllerTest.generateExpertise;
import static com.produtos.apirest.Util.Util.buildRequest;
import static com.produtos.apirest.Util.Util.toJson;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
public class VeterinaryControllerTest {

    private final String API = "/api/veterinary";

    @MockBean
    private VeterinaryService veterinaryService;

    @MockBean
    private ExpertiseService expertiseService;

    @Autowired
    MockMvc mvc;

    public static Veterinary generateVeterinary(){
        return Veterinary.builder()
                .veterinaryId(1L)
                .name("name")
                .phone("phone")
                .cpf("cpf")
                .expertise(generateExpertise())
                .build();
    }

    public static VeterinaryDTO generateVeterinaryDTO(){
        return VeterinaryDTO.builder()
                .id(1L)
                .name("name")
                .phone("phone")
                .cpf("cpf")
                .expertise(generateExpertise())
                .build();
    }

    public static List<Veterinary> generateVeterinaryList(){
        return Collections.singletonList(generateVeterinary());
    }

    @WithUserDetails("Admin")
    @Test
    public void save() throws Exception{
        Mockito.when(expertiseService.findById(Mockito.anyLong())).thenReturn(generateExpertise());
        Mockito.when(veterinaryService.save(Mockito.any(Veterinary.class))).thenReturn(generateVeterinary());
        String json = toJson(generateVeterinaryDTO());
        MockHttpServletRequestBuilder request = Util.buildRequest(HttpMethod.POST, API, json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @WithUserDetails("Admin")
    @Test
    public void update() throws Exception{
        Mockito.when(veterinaryService.update(Mockito.any(Veterinary.class))).thenReturn(generateVeterinary());
        String json = toJson(generateVeterinaryDTO());
        MockHttpServletRequestBuilder request = Util.buildRequest(HttpMethod.PUT, API, json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void removeById() throws Exception{
        Long id = 1L;
        Mockito.doNothing().when(veterinaryService).removeById(Mockito.anyLong());
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.DELETE, API.concat("/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void removeByIdWithFeedback() throws Exception{
        Long id = 1L;
        Mockito.when(veterinaryService.removeByIdWithFeedback(Mockito.anyLong())).thenReturn(generateVeterinary());
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.DELETE, API.concat("/feedback/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void findById() throws Exception{
        Long id = 1L;
        Mockito.when(veterinaryService.findById(Mockito.anyLong())).thenReturn(generateVeterinary());
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.GET, API.concat("/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void findAll() throws Exception{
        Mockito.when(veterinaryService.findAll()).thenReturn(generateVeterinaryList());
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.GET, API);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}