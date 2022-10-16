package com.produtos.apirest.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.produtos.apirest.models.Area;
import com.produtos.apirest.models.DTO.AreaDTO;
import com.produtos.apirest.models.Especialidade;
import com.produtos.apirest.service.AreaService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.ArrayList;
import java.util.List;
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


    @Test
    @WithUserDetails("Admin")
    public void deveSalvarController() throws Exception{
        //Cenário
        AreaDTO areaDTORequest = AreaDTO.builder()
                .nome("area")
                .build();

        //Area mockada
        Area area = Area.builder()
                .areaId(Long.valueOf(1))
                .nome("area")
                .build();

        //Mock dos Serviços
        Mockito.when(areaService.salvar(Mockito.any(Area.class))).thenReturn(area);

        // Serializar AnimalDTO para Json
        String json = new ObjectMapper().writeValueAsString(areaDTORequest);

        //Motando Requisição
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API.concat("/salvar"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        //Ação e Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }



    @Test
    @WithUserDetails("Admin")
    public void deveAtualizarController() throws Exception{
        //Cenário
        AreaDTO areaDTORequest = AreaDTO.builder()
                .id(Long.valueOf(1))
                .nome("nome")
                .build();

        //Area mockada
        Area area = Area.builder()
                .areaId(Long.valueOf(1))
                .nome("nome")
                .build();

        //Mock dos Serviços
        Mockito.when(areaService.atualizar(Mockito.any(Area.class))).thenReturn(area);

        // Serializar AnimalDTO para Json
        String json = new ObjectMapper().writeValueAsString(areaDTORequest);

        //Motando Requisição
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(API.concat("/atualizar"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        //Ação e Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithUserDetails("Admin")
    public void deveRemoverController() throws Exception{
        //Cenário
        Long id = Long.valueOf(1);

        //Mock Serviço
        doNothing().when(areaService).remover(isA(Long.class));

        //Montando a Requisiçaõ
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(API.concat("/remover/").concat(String.valueOf(id)));

        //Ação e Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("\"NO_CONTENT\""));
    }

    @Test
    @WithUserDetails("Admin")
    public void deveRemoverComFeedback() throws Exception{
        //Cenário
        Long id = Long.valueOf(1);

        //Area Mockada
        Area area = Area.builder()
                .areaId(1)
                .nome("nome")
                .build();

        //Mockando Serviço
        Mockito.when(areaService.removerFeedback(Mockito.anyLong())).thenReturn(area);

        // Motando Requisição para o Controlador
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(API.concat("/remover/feedback/").concat(String.valueOf(id)));

        //Ação e Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithUserDetails("Admin")
    public void deveBuscarPorId() throws Exception{
        //Cenário
        Long id = Long.valueOf(1);

        //Area Mockada
        Area area = Area.builder()
                .areaId(1)
                .nome("nome")
                .build();

        //Mockando Serviço
        Mockito.when(areaService.buscarAreaPorId(Mockito.anyLong())).thenReturn(area);

        // Motando Requisição para o Controlador
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(API.concat("/buscarId/").concat(String.valueOf(id)));

        //Ação e Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithUserDetails("Admin")
    public void deveBuscarPorFiltro() throws Exception{
        //Cenário
        AreaDTO areaDTORequest = AreaDTO.builder()
                .id(Long.valueOf(1))
                .nome("nome")
                .build();

        //Areas mockada
        List<Area> areas = new ArrayList<>();
        areas.add(
                Area.builder()
                        .areaId(Long.valueOf(1))
                        .nome("nome")
                        .build()
        );

        //Mockando Serviço
        Mockito.when(areaService.buscar(Mockito.any(Area.class))).thenReturn(areas);

        // Serializar AnimalDTO para Json
        String json = new ObjectMapper().writeValueAsString(areaDTORequest);

        //Montando Requisição

        //Motando Requisição para o Controlador
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(API.concat("/buscar/filtro"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        //Ação e Verificaçõa
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveBuscarTodosController() throws Exception{
        //Mock Areas
        List<Area> areas = new ArrayList<>();
        areas.add(
          Area.builder()
                  .areaId(Long.valueOf(1))
                  .nome("nome")
                  .build()
        );

        //Mock dos Serviços
        Mockito.when(areaService.buscarTodos()).thenReturn(areas);

        //Motando a Requisição
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(API.concat("/buscarTodos"));

        //Ação e Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveBuscarEspecialidade() throws Exception{
        //Cenário
        Long id = Long.valueOf(1);

        //Area Mockada
        Area area = Area.builder()
                .areaId(Long.valueOf(1))
                .nome("nome")
                .build();

        //Especialidades Mockada
        List<Especialidade> especialidades = new ArrayList<>();
        especialidades.add(
                Especialidade.builder()
                        .especialidadeId(Long.valueOf(1))
                        .nome("Nome")
                        .area(new Area())
                        .build()
        );

        //Mockando Serviço
        Mockito.when(areaService.buscarAreaPorId(Mockito.anyLong())).thenReturn(area);
        Mockito.when(areaService.buscarTodasEspecialidades(Mockito.any(Area.class))).thenReturn(especialidades);

        //Montando Request
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(API.concat("/buscar/especialidades/").concat(String.valueOf(id)));

        //Ação e Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
