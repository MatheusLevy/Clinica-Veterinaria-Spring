package com.produtos.apirest.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.produtos.apirest.models.Area;
import com.produtos.apirest.models.Consulta;
import com.produtos.apirest.models.DTO.EspecialidadeDTO;
import com.produtos.apirest.models.Especialidade;
import com.produtos.apirest.service.AreaService;
import com.produtos.apirest.service.EspecialidadeService;
import lombok.With;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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

import static com.produtos.apirest.Controller.AreaControllerTeste.getAreaInstance;

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
        //Cenário
        EspecialidadeDTO especialidadeDTO = getEspecialidadeDTOInstance();

        //Especialidade Mockada
        Especialidade especialidade = getEspecialidadeInstance();

        //Area Mockada
        Area area = getAreaInstance();

        //Mock dos Serviços
        Mockito.when(especialidadeService.salvar(Mockito.any(Especialidade.class))).thenReturn(especialidade);
        Mockito.when(areaService.salvar(Mockito.any(Area.class))).thenReturn(area);

        // Serializar especialidadeDTO para Json
        String json = new ObjectMapper().writeValueAsString(especialidadeDTO);

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

    @WithUserDetails("Admin")
    @Test
    public void deveAtualizar() throws Exception{
        //Cenário
        EspecialidadeDTO especialidadeDTO = getEspecialidadeDTOInstance();

        //Especialidade Mockado
        Especialidade especialidade = getEspecialidadeInstance();

        //Mock do Serviço
        Mockito.when(especialidadeService.atualizar(Mockito.any(Especialidade.class))).thenReturn(especialidade);

        // Serializar especialidadeDTO para Json
        String json = new ObjectMapper().writeValueAsString(especialidadeDTO);

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

    @WithUserDetails("Admin")
    @Test
    public void deveRemover() throws Exception{
        //Cenário
        Long id = Long.valueOf(1);

        //Especialidade Mockada
        Especialidade especialidade = getEspecialidadeInstance();

        //Mock dos Serviços
        Mockito.when(especialidadeService.buscarEspecialidadePorId(Mockito.anyLong())).thenReturn(especialidade);
        Mockito.doNothing().when(especialidadeService).remover(Mockito.any(Especialidade.class));

        //Montando Requisição
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(API.concat("/remover/").concat(String.valueOf(id)));

        //Ação e Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveRemoverComFeedback() throws Exception{
        //Cenário
        Long id = Long.valueOf(1);

        //Especialidade Mockada
        Especialidade especialidade = getEspecialidadeInstance();

        //Mock dos Serviços
        Mockito.when(especialidadeService.removerFeedback(Mockito.anyLong())).thenReturn(especialidade);

        //Montando Requisição
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(API.concat("/remover/feedback/").concat(String.valueOf(id)));

        //Ação e Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveBuscarPorId() throws Exception{
        //Cenário
        Long id = Long.valueOf(1);

        //Especialidade Mockada
        Especialidade especialidade = getEspecialidadeInstance();

        //Mock do Serviço
        Mockito.when(especialidadeService.buscarEspecialidadePorId(Mockito.anyLong())).thenReturn(especialidade);

        //Montando Requisição
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(API.concat("/buscar/").concat(String.valueOf(id)));

        //Açao e Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveBuscarPorFiltro() throws Exception{
        //Cenário
        EspecialidadeDTO especialidadeDTO = getEspecialidadeDTOInstance();

        //Especialidade Mockada
        List<Especialidade> especialidades = new ArrayList<>();
        especialidades.add(getEspecialidadeInstance());

        //Mock do Serviço
        Mockito.when(especialidadeService.buscar(Mockito.any(Especialidade.class))).thenReturn(especialidades);

        // Serializar especialidadeDTO para Json
        String json = new ObjectMapper().writeValueAsString(especialidadeDTO);

        //Motando Requisição
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(API.concat("/buscar/filtro"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        //Ação e Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveBuscarTodos() throws Exception{
        //Cenário
        Long id = Long.valueOf(1);

        //Lista Mockada
        List<Especialidade> especialidades = new ArrayList<>();
        especialidades.add(getEspecialidadeInstance());

        //Mock do Serviço
        Mockito.when(especialidadeService.buscarTodos()).thenReturn(especialidades);

        //Motando Requisição
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(API.concat("/buscarTodos"));

        //Ação e Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
