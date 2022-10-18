package com.produtos.apirest.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.produtos.apirest.models.DTO.TipoConsultaDTO;
import com.produtos.apirest.models.TipoConsulta;
import com.produtos.apirest.service.TipoConsultaService;
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

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
public class TipoConsultaControllerTeste {

    private final String API = "/api/tipoConsulta";
    @MockBean
    private TipoConsultaService tipoConsultaService;

    @Autowired
    MockMvc mvc;

    private static TipoConsulta getTipoConsultaInstance(Boolean temId){
        TipoConsulta tipoConsulta = TipoConsulta.builder()
                .nome("nome")
                .build();
        if (temId)
            tipoConsulta.setTipoConsultaId(Long.valueOf(1));
        return tipoConsulta;
    }

    private static TipoConsultaDTO getTipoConsultaDTOInstance(Boolean temId){
        TipoConsultaDTO tipoConsultaDTO = TipoConsultaDTO.builder()
                .nome("nome")
                .build();
        if (temId)
            tipoConsultaDTO.setId(Long.valueOf(1));
        return tipoConsultaDTO;
    }



    @WithUserDetails("Admin")
    @Test
    public void deveSalvarController() throws Exception{
        //Cenário
        TipoConsultaDTO tipoConsultaDTO = getTipoConsultaDTOInstance(false);

        //Consulta Mockada
        TipoConsulta tipoConsulta = getTipoConsultaInstance(true);

        //Mock do Serviço
        Mockito.when(tipoConsultaService.salvar(Mockito.any(TipoConsulta.class))).thenReturn(tipoConsulta);

        // Serializar consultaDTO para Json
        String json = new ObjectMapper().writeValueAsString(tipoConsultaDTO);

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
    public void deveRemoverPorId() throws Exception{
        //Cenário
        Long id = Long.valueOf(1);

        //TipoConsulta Mockado
        TipoConsulta tipoConsulta = getTipoConsultaInstance(true);

        //Mock do Serviço
        Mockito.doNothing().when(tipoConsultaService).remover(Mockito.any(TipoConsulta.class));
        Mockito.when(tipoConsultaService.buscarTipoConsultaPorId(Mockito.anyLong())).thenReturn(tipoConsulta);

        //Motando Requisição
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

        //TipoConsulta Mockada
        TipoConsulta tipoConsulta = getTipoConsultaInstance(true);

        //Mock do Serviço
        Mockito.when(tipoConsultaService.removerFeedback(Mockito.anyLong())).thenReturn(tipoConsulta);

        //Montando Requisição
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(API.concat("/remover/feedback/").concat(String.valueOf(id)));

        //Ação e Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveBuscarPorId() throws Exception {
        //Cenário
        Long id = Long.valueOf(1);

        //TipoConsulta Mocackada
        TipoConsulta tipoConsulta = getTipoConsultaInstance(true);

        //Mock do Serviço
        Mockito.when(tipoConsultaService.buscarTipoConsultaPorId(Mockito.anyLong())).thenReturn(tipoConsulta);

        //Montando Requisição
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(API.concat("/buscar/").concat(String.valueOf(id)));

        //Ação e Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveAtualizar() throws Exception{
        //Cenário
        TipoConsultaDTO tipoConsultaDTO = getTipoConsultaDTOInstance(true);

        //Consulta Mockada
        TipoConsulta tipoConsulta = getTipoConsultaInstance(true);

        //Mock do serviço
        Mockito.when(tipoConsultaService.atualizar(Mockito.any(TipoConsulta.class))).thenReturn(tipoConsulta);

        // Serializar consultaDTO para Json
        String json = new ObjectMapper().writeValueAsString(tipoConsultaDTO);

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
}
