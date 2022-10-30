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

    public static TipoAnimal generateTipoAnimalInstance(){
        return TipoAnimal.builder()
                .tipoAnimalId(1L)
                .nome("nome")
                .build();
    }

    public TipoAnimalDTO generateTipoAnimalDTOInstance(){
        return TipoAnimalDTO.builder()
                .id(1L)
                .nome("nome")
                .build();
    }

    @WithUserDetails("Admin")
    @Test
    public void deveSalvar() throws Exception{
        Mockito.when(tipoAnimalService.salvar(Mockito.any(TipoAnimal.class))).thenReturn(generateTipoAnimalInstance());
        String json = toJson(generateTipoAnimalDTOInstance());
        MockHttpServletRequestBuilder request = request(HttpMethod.POST, API.concat("/salvar"), json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveAtualizar() throws Exception{
        Mockito.when(tipoAnimalService.atualizar(Mockito.any(TipoAnimal.class))).thenReturn(generateTipoAnimalInstance());
        String json = toJson(generateTipoAnimalDTOInstance());
        MockHttpServletRequestBuilder request = request(HttpMethod.PUT, API.concat("/atualizar"), json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveRemover() throws Exception{
        Long id = 1L;
        Mockito.doNothing().when(tipoAnimalService).removerPorId(Mockito.anyLong());
        MockHttpServletRequestBuilder request = request(HttpMethod.DELETE, API.concat("/remover/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveRemoverComFeedback() throws Exception{
        Long id = 1L;
        Mockito.when(tipoAnimalService.removerComFeedback(Mockito.anyLong())).thenReturn(generateTipoAnimalInstance());
        MockHttpServletRequestBuilder request = request(HttpMethod.DELETE, API.concat("/remover/feedback/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveBuscarPorId() throws Exception{
        Long id = 1L;
        Mockito.when(tipoAnimalService.buscarPorId(Mockito.anyLong())).thenReturn(generateTipoAnimalInstance());
        MockHttpServletRequestBuilder request = request(HttpMethod.GET, API.concat("/buscarPorId/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}