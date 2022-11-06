package com.produtos.apirest.Controller;

import com.produtos.apirest.Util.Util;
import com.produtos.apirest.models.Area;
import com.produtos.apirest.models.DTO.AreaDTO;
import com.produtos.apirest.service.AreaService;
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

import static com.produtos.apirest.Controller.ExpertiseControllerTest.generateExpertiseList;
import static com.produtos.apirest.Util.Util.buildRequest;
import static com.produtos.apirest.Util.Util.toJson;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
public class AreaControllerTest {

    private final String API = "/api/area";

    @MockBean
    private AreaService areaService;

    @Autowired
    public MockMvc mvc;

    public static Area generateArea(){
        return Area.builder()
                .areaId(1L)
                .name("name")
                .build();
    }

    public static AreaDTO generateAreaDTO(){
        return AreaDTO.builder()
                .id(1L)
                .name("name")
                .build();
    }

    public static List<Area> generateAreaList(){
        return Collections.singletonList(generateArea());
    }

    @Test
    @WithUserDetails("Admin")
    public void save() throws Exception{
        Mockito.when(areaService.save(Mockito.any(Area.class))).thenReturn(generateArea());
        String json = toJson(generateAreaDTO());
        MockHttpServletRequestBuilder request = Util.buildRequest(HttpMethod.POST, API, json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @WithUserDetails("Admin")
    public void update() throws Exception{
        Mockito.when(areaService.update(Mockito.any(Area.class))).thenReturn(generateArea());
        String json = toJson(generateAreaDTO());
        MockHttpServletRequestBuilder request = Util.buildRequest(HttpMethod.PUT, API, json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithUserDetails("Admin")
    public void removeById() throws Exception{
        Long id = 1L;
        doNothing().when(areaService).removeById(isA(Long.class));
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.DELETE, API.concat("/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("\"NO_CONTENT\""));
    }

    @Test
    @WithUserDetails("Admin")
    public void removeByIdWithFeedback() throws Exception{
        Long id = 1L;
        Mockito.when(areaService.removeByIdWithFeedback(Mockito.anyLong())).thenReturn(generateArea());
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.DELETE, API.concat("/feedback/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithUserDetails("Admin")
    public void find() throws Exception{
        Mockito.when(areaService.find(Mockito.any(Area.class))).thenReturn(generateAreaList());
        String json = toJson(generateAreaDTO());
        MockHttpServletRequestBuilder request = Util.buildRequest(HttpMethod.GET, API.concat("/filter"), json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithUserDetails("Admin")
    public void findById() throws Exception{
        Long id = 1L;
        Mockito.when(areaService.findById(Mockito.anyLong())).thenReturn(generateArea());
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.GET, API.concat("/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void findExpertise() throws Exception{
        Long id = 1L;
        Mockito.when(areaService.findById(Mockito.anyLong())).thenReturn(generateArea());
        Mockito.when(areaService.findAllExpertiseByAreaId(Mockito.anyLong())).thenReturn(generateExpertiseList());
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.GET, API.concat("/expertises/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void findAll() throws Exception{
        Mockito.when(areaService.findAll()).thenReturn(generateAreaList());
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.GET, API);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}