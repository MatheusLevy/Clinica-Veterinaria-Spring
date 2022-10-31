package com.produtos.apirest.Controller;

import com.produtos.apirest.models.Area;
import com.produtos.apirest.models.DTO.EspecialidadeDTO;
import com.produtos.apirest.models.Especialidade;
import com.produtos.apirest.service.AreaService;
import com.produtos.apirest.service.EspecialidadeService;
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
import static com.produtos.apirest.Util.Util.request;
import static com.produtos.apirest.Util.Util.toJson;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
public class EspecialidadeControllerTeste {

    private final String API = "/api/especialidade";

    @MockBean
    private EspecialidadeService especialidadeService;

    @MockBean
    private AreaService areaService;

    @Autowired
    MockMvc mvc;

    public static EspecialidadeDTO generateEspecialidadeDTOInstance(){
        return EspecialidadeDTO.builder()
                .id(1L)
                .idArea(1L)
                .nome("nome")
                .area(generateAreaInstance())
                .build();
    }

    public static Especialidade generateEspecialidadeInstance(){
        return Especialidade.builder()
                .especialidadeId(1L)
                .nome("nome")
                .area(generateAreaInstance())
                .build();
    }

    public static List<Especialidade> generateEspecialidadeListInstance(){
        return new ArrayList<>(){{
            add(generateEspecialidadeInstance());
        }};
    }

    @WithUserDetails("Admin")
    @Test
    public void deveSalvar() throws Exception{
        Mockito.when(especialidadeService.salvar(Mockito.any(Especialidade.class))).thenReturn(generateEspecialidadeInstance());
        Mockito.when(areaService.salvar(Mockito.any(Area.class))).thenReturn(generateAreaInstance());
        String json = toJson(generateEspecialidadeDTOInstance());
        MockHttpServletRequestBuilder request = request(HttpMethod.POST, API.concat("/salvar"), json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveAtualizar() throws Exception{
        Mockito.when(especialidadeService.atualizar(Mockito.any(Especialidade.class))).thenReturn(generateEspecialidadeInstance());
        String json = toJson(generateEspecialidadeDTOInstance());
        MockHttpServletRequestBuilder request = request(HttpMethod.PUT, API.concat("/atualizar"), json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveRemover() throws Exception{
        Long id = 1L;
        Mockito.when(especialidadeService.buscarPorId(Mockito.anyLong())).thenReturn(generateEspecialidadeInstance());
        Mockito.doNothing().when(especialidadeService).remover(Mockito.any(Especialidade.class));
        MockHttpServletRequestBuilder request = request(HttpMethod.DELETE, API.concat("/remover/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveRemoverComFeedback() throws Exception{
        Long id = 1L;
        Mockito.when(especialidadeService.removerFeedback(Mockito.anyLong())).thenReturn(generateEspecialidadeInstance());
        MockHttpServletRequestBuilder request = request(HttpMethod.DELETE, API.concat("/remover/feedback/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveBuscarPorFiltro() throws Exception{
        Mockito.when(especialidadeService.buscar(Mockito.any(Especialidade.class))).thenReturn(generateEspecialidadeListInstance());
        String json = toJson(generateEspecialidadeDTOInstance());
        MockHttpServletRequestBuilder request = request(HttpMethod.GET, API.concat("/buscar/filtro"), json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveBuscarPorId() throws Exception{
        Long id = 1L;
        Mockito.when(especialidadeService.buscarPorId(Mockito.anyLong())).thenReturn(generateEspecialidadeInstance());
        MockHttpServletRequestBuilder request = request(HttpMethod.GET, API.concat("/buscar/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveBuscarTodos() throws Exception{
        Mockito.when(especialidadeService.buscarTodos()).thenReturn(generateEspecialidadeListInstance());
        MockHttpServletRequestBuilder request = request(HttpMethod.GET, API.concat("/buscarTodos"));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}