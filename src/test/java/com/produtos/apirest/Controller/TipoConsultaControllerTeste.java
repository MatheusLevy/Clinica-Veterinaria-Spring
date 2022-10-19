package com.produtos.apirest.Controller;

import com.produtos.apirest.models.DTO.TipoConsultaDTO;
import com.produtos.apirest.models.TipoConsulta;
import com.produtos.apirest.service.TipoConsultaService;
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

import static com.produtos.apirest.Util.HttpMethods.*;
import static com.produtos.apirest.Util.Util.request;
import static com.produtos.apirest.Util.Util.toJson;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
public class TipoConsultaControllerTeste {
    private final String API = "/api/tipoConsulta";
    @MockBean
    private TipoConsultaService tipoConsultaService;

    @Autowired
    MockMvc mvc;

    public static TipoConsulta getTipoConsultaInstance(){
        return TipoConsulta.builder()
                .tipoConsultaId(Long.valueOf(1))
                .nome("nome")
                .build();
    }

    public static TipoConsultaDTO getTipoConsultaDTOInstance(){
        return TipoConsultaDTO.builder()
                .id(Long.valueOf(1))
                .nome("nome")
                .build();
    }

    @WithUserDetails("Admin")
    @Test
    public void deveSalvarController() throws Exception{
        Mockito.when(tipoConsultaService.salvar(Mockito.any(TipoConsulta.class))).thenReturn(getTipoConsultaInstance());
        String json = toJson(getTipoConsultaDTOInstance());
        MockHttpServletRequestBuilder request = request(Post, API.concat("/salvar"), json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveAtualizarController() throws Exception{
        Mockito.when(tipoConsultaService.atualizar(Mockito.any(TipoConsulta.class))).thenReturn(getTipoConsultaInstance());
        String json = toJson(getTipoConsultaDTOInstance());
        MockHttpServletRequestBuilder request = request(Put, API.concat("/atualizar"), json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveRemoverPorIdController() throws Exception{
        Long id = Long.valueOf(1);
        Mockito.doNothing().when(tipoConsultaService).remover(Mockito.any(TipoConsulta.class));
        Mockito.when(tipoConsultaService.buscarTipoConsultaPorId(Mockito.anyLong())).thenReturn(getTipoConsultaInstance());
        MockHttpServletRequestBuilder request = request(Delete, API.concat("/remover/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveRemoverComFeedbackController() throws Exception{
        Long id = Long.valueOf(1);
        Mockito.when(tipoConsultaService.removerFeedback(Mockito.anyLong())).thenReturn(getTipoConsultaInstance());
        MockHttpServletRequestBuilder request = request(Delete, API.concat("/remover/feedback/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveBuscarPorIdController() throws Exception {
        Long id = Long.valueOf(1);
        Mockito.when(tipoConsultaService.buscarTipoConsultaPorId(Mockito.anyLong())).thenReturn(getTipoConsultaInstance());
        MockHttpServletRequestBuilder request = request(Get, API.concat("/buscar/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}