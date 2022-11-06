package com.produtos.apirest.Controller;

import com.produtos.apirest.Util.Util;
import com.produtos.apirest.models.Area;
import com.produtos.apirest.models.DTO.ExpertiseDTO;
import com.produtos.apirest.models.Expertise;
import com.produtos.apirest.service.AreaService;
import com.produtos.apirest.service.ExpertiseService;
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

import static com.produtos.apirest.Controller.AreaControllerTest.generateArea;
import static com.produtos.apirest.Util.Util.buildRequest;
import static com.produtos.apirest.Util.Util.toJson;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
public class ExpertiseControllerTest {

    private final String API = "/api/expertise";

    @MockBean
    private ExpertiseService expertiseService;

    @MockBean
    private AreaService areaService;

    @Autowired
    MockMvc mvc;

    public static ExpertiseDTO generateExpertiseDTO(){
        return ExpertiseDTO.builder()
                .id(1L)
                .areaId(1L)
                .name("name")
                .area(generateArea())
                .build();
    }

    public static Expertise generateExpertise(){
        return Expertise.builder()
                .expertiseId(1L)
                .name("name")
                .area(generateArea())
                .build();
    }

    public static List<Expertise> generateExpertiseList(){
        return Collections.singletonList(generateExpertise());
    }

    @WithUserDetails("Admin")
    @Test
    public void save() throws Exception{
        Mockito.when(expertiseService.save(Mockito.any(Expertise.class))).thenReturn(generateExpertise());
        Mockito.when(areaService.save(Mockito.any(Area.class))).thenReturn(generateArea());
        String json = toJson(generateExpertiseDTO());
        MockHttpServletRequestBuilder request = Util.buildRequest(HttpMethod.POST, API, json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @WithUserDetails("Admin")
    @Test
    public void update() throws Exception{
        Mockito.when(expertiseService.update(Mockito.any(Expertise.class))).thenReturn(generateExpertise());
        String json = toJson(generateExpertiseDTO());
        MockHttpServletRequestBuilder request = Util.buildRequest(HttpMethod.PUT, API, json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void removeById() throws Exception{
        Long id = 1L;
        Mockito.when(expertiseService.findById(Mockito.anyLong())).thenReturn(generateExpertise());
        Mockito.doNothing().when(expertiseService).remove(Mockito.any(Expertise.class));
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.DELETE, API.concat("/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void removeByIdWithFeedback() throws Exception{
        Long id = 1L;
        Mockito.when(expertiseService.removeByIdWithFeedback(Mockito.anyLong())).thenReturn(generateExpertise());
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.DELETE, API.concat("/feedback/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void find() throws Exception{
        Mockito.when(expertiseService.find(Mockito.any(Expertise.class))).thenReturn(generateExpertiseList());
        String json = toJson(generateExpertiseDTO());
        MockHttpServletRequestBuilder request = Util.buildRequest(HttpMethod.GET, API.concat("/filter"), json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void findById() throws Exception{
        Long id = 1L;
        Mockito.when(expertiseService.findById(Mockito.anyLong())).thenReturn(generateExpertise());
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.GET, API.concat("/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void findAll() throws Exception{
        Mockito.when(expertiseService.findAll()).thenReturn(generateExpertiseList());
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.GET, API);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}