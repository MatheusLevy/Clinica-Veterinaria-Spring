package com.produtos.apirest.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.produtos.apirest.controller.TipoAnimalController;
import com.produtos.apirest.models.DTO.AnimalDTO;
import com.produtos.apirest.models.DTO.TipoAnimalDTO;
import com.produtos.apirest.models.TipoAnimal;
import com.produtos.apirest.service.TipoAnimalService;
import com.produtos.apirest.service.TipoConsultaService;
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

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
public class TipoAnimalControllerTeste {

    private final String API = "/api/tipoAnimal";

    @MockBean
    private TipoAnimalService tipoAnimalService;

    @Autowired
    MockMvc mvc;

    public TipoAnimal getTipoAnimalInstance(Boolean temId){
        TipoAnimal tipoAnimal = TipoAnimal.builder()
                .nome("nome")
                .build();
        if (temId)
            tipoAnimal.setTipoAnimalId(Long.valueOf(1));

        return tipoAnimal;
    }

    public TipoAnimalDTO getTipoAnimalDTOInstance(Boolean temId){
        TipoAnimalDTO tipoAnimalDTO = TipoAnimalDTO.builder()
                .nome("nome")
                .build();
        if (temId)
            tipoAnimalDTO.setId(Long.valueOf(1));

        return tipoAnimalDTO;
    }


    @WithUserDetails("Admin")
    @Test
    public void deveSalvarController() throws Exception{
        //Cenário
        TipoAnimalDTO tipoAnimalDTO = getTipoAnimalDTOInstance(false);

        //TipoAnimal Mockado
        TipoAnimal tipoAnimal = getTipoAnimalInstance(true);

        //Mock Serviço
        Mockito.when(tipoAnimalService.salvar(Mockito.any(TipoAnimal.class))).thenReturn(tipoAnimal);

        // Serializar consultaDTO para Json
        String json = new ObjectMapper().writeValueAsString(tipoAnimalDTO);

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
    public void deveRemoverController() throws Exception{
        //Cenário
        Long id = Long.valueOf(1);

        //Mock do Serviço
        Mockito.doNothing().when(tipoAnimalService).removerPorId(Mockito.anyLong());

        //Motando Requisição
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(API.concat("/remover/").concat(String.valueOf(id)));

        //Ação e Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveRemoverComFeedbackController() throws Exception{
        //Cenário
        Long id = Long.valueOf(1);

        //Mock TipoAnimal
        TipoAnimal tipoAnimal = getTipoAnimalInstance(true);

        //Mock do Serviço
        Mockito.when(tipoAnimalService.removerFeedback(Mockito.anyLong())).thenReturn(tipoAnimal);

        //Motando Requisição
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(API.concat("/remover/feedback/").concat(String.valueOf(id)));

        //Ação e Verificação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveAtualizarController() throws Exception{
        //Cenário
        TipoAnimalDTO tipoAnimalDTO = getTipoAnimalDTOInstance(true);

        //TipoAnimal Mock
        TipoAnimal tipoAnimal = getTipoAnimalInstance(true);

        //Mock do Serviço
        Mockito.when(tipoAnimalService.atualizar(Mockito.any(TipoAnimal.class))).thenReturn(tipoAnimal);

        // Serializar consultaDTO para Json
        String json = new ObjectMapper().writeValueAsString(tipoAnimalDTO);

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
    public void deveBuscarPorIdController() throws Exception{
        //Cenário
        Long id = Long.valueOf(1);

        //TipoAnimal Mockado
        TipoAnimal tipoAnimal = getTipoAnimalInstance(true);

        //Serviço Mock
        Mockito.when(tipoAnimalService.buscarTipoAnimalPorId(Mockito.anyLong())).thenReturn(tipoAnimal);

        //Motando Requisição
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(API.concat("/buscarPorId/").concat(String.valueOf(id)));

        //Ação
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
