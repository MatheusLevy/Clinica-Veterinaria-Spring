package com.produtos.apirest.Controller;

import com.produtos.apirest.models.Area;
import com.produtos.apirest.models.DTO.AreaDTO;
import com.produtos.apirest.service.AreaService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.ArrayList;
import java.util.List;

import static com.produtos.apirest.Controller.EspecialidadeControllerTeste.getEspecialidadeListInstance;
import static com.produtos.apirest.Util.HttpMethods.*;
import static com.produtos.apirest.Util.Util.request;
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

    public static Area getAreaInstance(){
        return Area.builder()
                .areaId(Long.valueOf(1))
                .nome("nome")
                .build();
    }

    public static AreaDTO getAreaDTOInstance(){
        return AreaDTO.builder()
                .id(Long.valueOf(1))
                .nome("nome")
                .build();
    }

    public static List<Area> getAreaListInstance(){
        return new ArrayList<>(){{
           add(getAreaInstance());
        }};
    }

    @Test
    @WithUserDetails("Admin")
    public void deveSalvarController() throws Exception{
        Mockito.when(areaService.salvar(Mockito.any(Area.class))).thenReturn(getAreaInstance());
        String json = toJson(getAreaDTOInstance());
        MockHttpServletRequestBuilder request = request(Post, API.concat("/salvar"), json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @WithUserDetails("Admin")
    public void deveAtualizarController() throws Exception{
        Mockito.when(areaService.atualizar(Mockito.any(Area.class))).thenReturn(getAreaInstance());
        String json = toJson(getAreaDTOInstance());
        MockHttpServletRequestBuilder request = request(Put, API.concat("/atualizar"), json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithUserDetails("Admin")
    public void deveRemoverController() throws Exception{
        Long id = Long.valueOf(1);
        doNothing().when(areaService).remover(isA(Long.class));
        MockHttpServletRequestBuilder request = request(Delete, API.concat("/remover/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("\"NO_CONTENT\""));
    }

    @Test
    @WithUserDetails("Admin")
    public void deveRemoverComFeedback() throws Exception{
        Long id = Long.valueOf(1);
        Mockito.when(areaService.removerFeedback(Mockito.anyLong())).thenReturn(getAreaInstance());
        MockHttpServletRequestBuilder request = request(Delete, API.concat("/remover/feedback/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithUserDetails("Admin")
    public void deveBuscarPorId() throws Exception{
        Long id = Long.valueOf(1);
        Mockito.when(areaService.buscarAreaPorId(Mockito.anyLong())).thenReturn(getAreaInstance());
        MockHttpServletRequestBuilder request = request(Get, API.concat("/buscarId/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithUserDetails("Admin")
    public void deveBuscarPorFiltro() throws Exception{
        Mockito.when(areaService.buscar(Mockito.any(Area.class))).thenReturn(getAreaListInstance());
        String json = toJson(getAreaDTOInstance());
        MockHttpServletRequestBuilder request = request(Get, API.concat("/buscar/filtro"), json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveBuscarTodosController() throws Exception{
        Mockito.when(areaService.buscarTodos()).thenReturn(getAreaListInstance());
        MockHttpServletRequestBuilder request = request(Get, API.concat("/buscarTodos"));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveBuscarEspecialidade() throws Exception{
        Long id = Long.valueOf(1);
        Mockito.when(areaService.buscarAreaPorId(Mockito.anyLong())).thenReturn(getAreaInstance());
        Mockito.when(areaService.buscarTodasEspecialidades(Mockito.any(Area.class))).thenReturn(getEspecialidadeListInstance());
        MockHttpServletRequestBuilder request = request(Get, API.concat("/buscar/especialidades/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}