package com.produtos.apirest.Controller;

import com.produtos.apirest.models.DTO.TipoAnimalDTO;
import com.produtos.apirest.models.TipoAnimal;
import com.produtos.apirest.service.TipoAnimalService;
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

import static com.produtos.apirest.Util.Util.request;
import static com.produtos.apirest.Util.Util.toJson;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
public class TipoAnimalControllerTeste {

    private final String API = "/api/tipoAnimal";

    @MockBean
    private TipoAnimalService tipoAnimalService;

    @Autowired
    MockMvc mvc;

    public static TipoAnimal getTipoAnimalInstance(){
        return TipoAnimal.builder()
                .tipoAnimalId(Long.valueOf(1))
                .nome("nome")
                .build();
    }

    public TipoAnimalDTO getTipoAnimalDTOInstance(){
        return TipoAnimalDTO.builder()
                .id(Long.valueOf(1))
                .nome("nome")
                .build();
    }

    @WithUserDetails("Admin")
    @Test
    public void deveSalvarController() throws Exception{
        Mockito.when(tipoAnimalService.salvar(Mockito.any(TipoAnimal.class))).thenReturn(getTipoAnimalInstance());
        String json = toJson(getTipoAnimalDTOInstance());
        MockHttpServletRequestBuilder request = request(HttpMethod.POST, API.concat("/salvar"), json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveAtualizarController() throws Exception{
        Mockito.when(tipoAnimalService.atualizar(Mockito.any(TipoAnimal.class))).thenReturn(getTipoAnimalInstance());
        String json = toJson(getTipoAnimalDTOInstance());
        MockHttpServletRequestBuilder request = request(HttpMethod.PUT, API.concat("/atualizar"), json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveRemoverController() throws Exception{
        Long id = Long.valueOf(1);
        Mockito.doNothing().when(tipoAnimalService).removerPorId(Mockito.anyLong());
        MockHttpServletRequestBuilder request = request(HttpMethod.DELETE, API.concat("/remover/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveRemoverComFeedbackController() throws Exception{
        Long id = Long.valueOf(1);
        Mockito.when(tipoAnimalService.removerFeedback(Mockito.anyLong())).thenReturn(getTipoAnimalInstance());
        MockHttpServletRequestBuilder request = request(HttpMethod.DELETE, API.concat("/remover/feedback/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveBuscarPorIdController() throws Exception{
        Long id = Long.valueOf(1);
        Mockito.when(tipoAnimalService.buscarTipoAnimalPorId(Mockito.anyLong())).thenReturn(getTipoAnimalInstance());
        MockHttpServletRequestBuilder request = request(HttpMethod.GET, API.concat("/buscarPorId/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}