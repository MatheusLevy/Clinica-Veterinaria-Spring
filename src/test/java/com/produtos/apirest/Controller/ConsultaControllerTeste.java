package com.produtos.apirest.Controller;

import com.produtos.apirest.models.Consulta;
import com.produtos.apirest.models.DTO.ConsultaDTO;
import com.produtos.apirest.service.AnimalService;
import com.produtos.apirest.service.ConsultaService;
import com.produtos.apirest.service.TipoConsultaService;
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

import static com.produtos.apirest.Controller.AnimalControllerTeste.generateAnimalInstance;
import static com.produtos.apirest.Controller.TipoConsultaControllerTeste.generateTipoConsultaInstance;
import static com.produtos.apirest.Controller.VeterinarioControllerTeste.generateVeterinarioInstance;
import static com.produtos.apirest.Util.Util.request;
import static com.produtos.apirest.Util.Util.toJson;
import static org.mockito.ArgumentMatchers.isA;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
public class ConsultaControllerTeste {

    private final String API = "/api/consulta";

    @MockBean
    private ConsultaService consultaService;

    @MockBean
    private AnimalService animalService;

    @MockBean
    private TipoConsultaService tipoConsultaService;

    @Autowired
    public MockMvc mvc;

    public static Consulta generateConsultaInstance(){
        return Consulta.builder()
                .consultaId(1L)
                .description("descrição")
                .appointmentType(generateTipoConsultaInstance())
                .veterinary(generateVeterinarioInstance())
                .animal(generateAnimalInstance())
                .build();
    }

    public static ConsultaDTO generateConsultaDTOInstance(){
        return ConsultaDTO.builder()
                .id(1L)
                .descricao("descrição")
                .tipoConsultaId(1L)
                .veterinarioId(1L)
                .animalId(1L)
                .build();
    }

    public static List<Consulta> generateConsultaListInstance(){
        return new ArrayList<>(){
            {
                add(generateConsultaInstance());
            }
        };
    }

    @Test
    @WithUserDetails("Admin")
    public void deveSalvar() throws Exception{
        Mockito.when(consultaService.salvar(Mockito.any(Consulta.class))).thenReturn(generateConsultaInstance());
        Mockito.when(tipoConsultaService.buscarPorId(Mockito.anyLong())).thenReturn(generateTipoConsultaInstance());
        Mockito.when(animalService.buscarPorId(Mockito.anyLong())).thenReturn(generateAnimalInstance());
        String json = toJson(generateConsultaDTOInstance());
        MockHttpServletRequestBuilder request = request(HttpMethod.POST, API.concat("/salvar"), json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveAtualizar() throws Exception{
        Mockito.when(consultaService.atualizar(Mockito.any(Consulta.class))).thenReturn(generateConsultaInstance());
        String json = toJson(generateConsultaDTOInstance());
        MockHttpServletRequestBuilder request = request(HttpMethod.PUT, API.concat("/atualizar"), json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveRemoverPorId() throws Exception{
        Long id = 1L;
        Mockito.doNothing().when(consultaService).removerPorId(isA(Long.class));
        MockHttpServletRequestBuilder request = request(HttpMethod.DELETE, API.concat("/remover/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("\"NO_CONTENT\""));
    }

    @WithUserDetails("Admin")
    @Test
    public void deveBuscarTodos() throws Exception{
        Mockito.when(consultaService.buscarTodos()).thenReturn(generateConsultaListInstance());
        MockHttpServletRequestBuilder request = request(HttpMethod.GET, API.concat("/buscarTodos"));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveRemoverComFeedback() throws Exception{

        Long id = 1L;
        Mockito.when(consultaService.removerComFeedback(Mockito.anyLong())).thenReturn(generateConsultaInstance());
        MockHttpServletRequestBuilder request = request(HttpMethod.DELETE, API.concat("/remover/feedback/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}