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
import java.util.ArrayList;
import java.util.List;

import static com.produtos.apirest.Controller.ExpertiseControllerTeste.generateEspecialidadeListInstance;
import static com.produtos.apirest.Util.Util.buildRequest;
import static com.produtos.apirest.Util.Util.toJson;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
public class AreaControllerTeste {

    private final String API = "/api/area";

    @MockBean
    private AreaService areaService;

    @Autowired
    public MockMvc mvc;

    public static Area generateAreaInstance(){
        return Area.builder()
                .areaId(1L)
                .name("name")
                .build();
    }

    public static AreaDTO generateAreaDTOInstance(){
        return AreaDTO.builder()
                .id(1L)
                .name("name")
                .build();
    }

    public static List<Area> generateAreaListInstance(){
        return new ArrayList<>(){{
           add(generateAreaInstance());
        }};
    }

    @Test
    @WithUserDetails("Admin")
    public void deveSalvar() throws Exception{
        Mockito.when(areaService.save(Mockito.any(Area.class))).thenReturn(generateAreaInstance());
        String json = toJson(generateAreaDTOInstance());
        MockHttpServletRequestBuilder request = Util.buildRequest(HttpMethod.POST, API, json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @WithUserDetails("Admin")
    public void deveAtualizar() throws Exception{
        Mockito.when(areaService.update(Mockito.any(Area.class))).thenReturn(generateAreaInstance());
        String json = toJson(generateAreaDTOInstance());
        MockHttpServletRequestBuilder request = Util.buildRequest(HttpMethod.PUT, API, json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithUserDetails("Admin")
    public void deveRemover() throws Exception{
        Long id = 1L;
        doNothing().when(areaService).removeById(isA(Long.class));
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.DELETE, API.concat("/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("\"NO_CONTENT\""));
    }

    @Test
    @WithUserDetails("Admin")
    public void deveRemoverComFeedback() throws Exception{
        Long id = 1L;
        Mockito.when(areaService.removeByIdWithFeedback(Mockito.anyLong())).thenReturn(generateAreaInstance());
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.DELETE, API.concat("/feedback/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithUserDetails("Admin")
    public void deveBuscarPorFiltro() throws Exception{
        Mockito.when(areaService.find(Mockito.any(Area.class))).thenReturn(generateAreaListInstance());
        String json = toJson(generateAreaDTOInstance());
        MockHttpServletRequestBuilder request = Util.buildRequest(HttpMethod.GET, API.concat("/filter"), json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithUserDetails("Admin")
    public void deveBuscarPorId() throws Exception{
        Long id = 1L;
        Mockito.when(areaService.findById(Mockito.anyLong())).thenReturn(generateAreaInstance());
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.GET, API.concat("/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveBuscarEspecialidade() throws Exception{
        Long id = 1L;
        Mockito.when(areaService.findById(Mockito.anyLong())).thenReturn(generateAreaInstance());
        Mockito.when(areaService.findAllExpertiseByAreaId(Mockito.anyLong())).thenReturn(generateEspecialidadeListInstance());
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.GET, API.concat("/expertises/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveBuscarTodos() throws Exception{
        Mockito.when(areaService.findAll()).thenReturn(generateAreaListInstance());
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.GET, API);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}