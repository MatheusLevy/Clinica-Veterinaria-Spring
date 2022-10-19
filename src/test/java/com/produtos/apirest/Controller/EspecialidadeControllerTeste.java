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
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static com.produtos.apirest.Controller.AreaControllerTeste.getAreaInstance;
import static com.produtos.apirest.Util.HttpMethods.*;
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

    public static EspecialidadeDTO getEspecialidadeDTOInstance(){
        EspecialidadeDTO especialidadeDTO = EspecialidadeDTO.builder()
                .id(Long.valueOf(1))
                .idArea(Long.valueOf(1))
                .nome("nome")
                .area(new Area())
                .build();;
        return especialidadeDTO;
    }

    public static Especialidade getEspecialidadeInstance(){
        Especialidade especialidade = Especialidade.builder()
                .especialidadeId(Long.valueOf(1))
                .nome("nome")
                .area(new Area())
                .build();
        return especialidade;
    }

    public static List<Especialidade> getEspecialidadeListInstance(){
        return new ArrayList<>(){{
            add(getEspecialidadeInstance());
        }};
    }

    @WithUserDetails("Admin")
    @Test
    public void deveSalvarController() throws Exception{
        Mockito.when(especialidadeService.salvar(Mockito.any(Especialidade.class))).thenReturn(getEspecialidadeInstance());
        Mockito.when(areaService.salvar(Mockito.any(Area.class))).thenReturn(getAreaInstance());
        String json = toJson(getEspecialidadeDTOInstance());
        MockHttpServletRequestBuilder request = request(Post, API.concat("/salvar"), json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveAtualizarController() throws Exception{
        Mockito.when(especialidadeService.atualizar(Mockito.any(Especialidade.class))).thenReturn(getEspecialidadeInstance());
        String json = toJson(getEspecialidadeDTOInstance());
        MockHttpServletRequestBuilder request = request(Put, API.concat("/atualizar"), json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveRemoverController() throws Exception{
        Long id = Long.valueOf(1);
        Mockito.when(especialidadeService.buscarEspecialidadePorId(Mockito.anyLong())).thenReturn(getEspecialidadeInstance());
        Mockito.doNothing().when(especialidadeService).remover(Mockito.any(Especialidade.class));
        MockHttpServletRequestBuilder request = request(Delete, API.concat("/remover/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveRemoverComFeedbackController() throws Exception{
        Long id = Long.valueOf(1);
        Mockito.when(especialidadeService.removerFeedback(Mockito.anyLong())).thenReturn(getEspecialidadeInstance());
        MockHttpServletRequestBuilder request = request(Delete, API.concat("/remover/feedback/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveBuscarPorFiltroController() throws Exception{
        Mockito.when(especialidadeService.buscar(Mockito.any(Especialidade.class))).thenReturn(getEspecialidadeListInstance());
        String json = toJson(getEspecialidadeDTOInstance());
        MockHttpServletRequestBuilder request = request(Get, API.concat("/buscar/filtro"), json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveBuscarPorIdController() throws Exception{
        Long id = Long.valueOf(1);
        Mockito.when(especialidadeService.buscarEspecialidadePorId(Mockito.anyLong())).thenReturn(getEspecialidadeInstance());
        MockHttpServletRequestBuilder request = request(Get, API.concat("/buscar/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveBuscarTodos() throws Exception{
        Long id = Long.valueOf(1);
        Mockito.when(especialidadeService.buscarTodos()).thenReturn(getEspecialidadeListInstance());
        MockHttpServletRequestBuilder request = request(Get, API.concat("/buscarTodos"));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}