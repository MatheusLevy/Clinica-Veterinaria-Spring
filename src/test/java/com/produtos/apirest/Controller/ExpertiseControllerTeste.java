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

import java.util.ArrayList;
import java.util.List;

import static com.produtos.apirest.Controller.AreaControllerTeste.generateAreaInstance;
import static com.produtos.apirest.Util.Util.buildRequest;
import static com.produtos.apirest.Util.Util.toJson;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
public class ExpertiseControllerTeste {

    private final String API = "/api/expertise";

    @MockBean
    private ExpertiseService expertiseService;

    @MockBean
    private AreaService areaService;

    @Autowired
    MockMvc mvc;

    public static ExpertiseDTO generateEspecialidadeDTOInstance(){
        return ExpertiseDTO.builder()
                .id(1L)
                .areaId(1L)
                .name("name")
                .area(generateAreaInstance())
                .build();
    }

    public static Expertise generateEspecialidadeInstance(){
        return Expertise.builder()
                .expertiseId(1L)
                .name("name")
                .area(generateAreaInstance())
                .build();
    }

    public static List<Expertise> generateEspecialidadeListInstance(){
        return new ArrayList<>(){{
            add(generateEspecialidadeInstance());
        }};
    }

    @WithUserDetails("Admin")
    @Test
    public void deveSalvar() throws Exception{
        Mockito.when(expertiseService.save(Mockito.any(Expertise.class))).thenReturn(generateEspecialidadeInstance());
        Mockito.when(areaService.save(Mockito.any(Area.class))).thenReturn(generateAreaInstance());
        String json = toJson(generateEspecialidadeDTOInstance());
        MockHttpServletRequestBuilder request = Util.buildRequest(HttpMethod.POST, API, json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveAtualizar() throws Exception{
        Mockito.when(expertiseService.update(Mockito.any(Expertise.class))).thenReturn(generateEspecialidadeInstance());
        String json = toJson(generateEspecialidadeDTOInstance());
        MockHttpServletRequestBuilder request = Util.buildRequest(HttpMethod.PUT, API, json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveRemover() throws Exception{
        Long id = 1L;
        Mockito.when(expertiseService.findById(Mockito.anyLong())).thenReturn(generateEspecialidadeInstance());
        Mockito.doNothing().when(expertiseService).remove(Mockito.any(Expertise.class));
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.DELETE, API.concat("/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveRemoverComFeedback() throws Exception{
        Long id = 1L;
        Mockito.when(expertiseService.removeByIdWithFeedback(Mockito.anyLong())).thenReturn(generateEspecialidadeInstance());
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.DELETE, API.concat("/feedback/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveBuscarPorFiltro() throws Exception{
        Mockito.when(expertiseService.find(Mockito.any(Expertise.class))).thenReturn(generateEspecialidadeListInstance());
        String json = toJson(generateEspecialidadeDTOInstance());
        MockHttpServletRequestBuilder request = Util.buildRequest(HttpMethod.GET, API.concat("/filter"), json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveBuscarPorId() throws Exception{
        Long id = 1L;
        Mockito.when(expertiseService.findById(Mockito.anyLong())).thenReturn(generateEspecialidadeInstance());
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.GET, API.concat("/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveBuscarTodos() throws Exception{
        Mockito.when(expertiseService.findAll()).thenReturn(generateEspecialidadeListInstance());
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.GET, API);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}