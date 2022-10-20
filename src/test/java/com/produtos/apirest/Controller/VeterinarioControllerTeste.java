package com.produtos.apirest.Controller;

import com.produtos.apirest.models.DTO.VeterinarioDTO;
import com.produtos.apirest.models.Veterinario;
import com.produtos.apirest.service.VeterinarioService;
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

import static com.produtos.apirest.Controller.EspecialidadeControllerTeste.getEspecialidadeInstance;
import static com.produtos.apirest.Util.Util.request;
import static com.produtos.apirest.Util.Util.toJson;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
public class VeterinarioControllerTeste {

    private final String API = "/api/veterinario";

    @MockBean
    private VeterinarioService veterinarioService;

    @Autowired
    MockMvc mvc;

    public static Veterinario getVeterinarioInstance(){
        return Veterinario.builder()
                .veterinarioId(Long.valueOf(1))
                .nome("nome")
                .telefone("telefone")
                .cpf("cpf")
                .especialidade(getEspecialidadeInstance())
                .build();
    }

    public static VeterinarioDTO getVeterinarioDTOInstance(){
        return VeterinarioDTO.builder()
                .id(Long.valueOf(1))
                .nome("nome")
                .telefone("telefone")
                .cpf("cpf")
                .especialidade(getEspecialidadeInstance())
                .build();
    }

    public static List<Veterinario> getVeterinarioListInstance(){
        return new ArrayList<>(){{
           add(getVeterinarioInstance());
        }};
    }

    @WithUserDetails("Admin")
    @Test
    public void deveSalvarController() throws Exception{
        Mockito.when(veterinarioService.salvar(Mockito.any(Veterinario.class))).thenReturn(getVeterinarioInstance());
        String json = toJson(getVeterinarioDTOInstance());
        MockHttpServletRequestBuilder request = request(HttpMethod.POST, API.concat("/salvar"), json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveAtualizarController() throws Exception{
        Mockito.when(veterinarioService.atualizar(Mockito.any(Veterinario.class))).thenReturn(getVeterinarioInstance());
        String json = toJson(getVeterinarioDTOInstance());
        MockHttpServletRequestBuilder request = request(HttpMethod.PUT, API.concat("/atualizar"), json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveRemoverPorId() throws Exception{
        Long id = Long.valueOf(1);
        Mockito.doNothing().when(veterinarioService).remover(Mockito.anyLong());
        MockHttpServletRequestBuilder request = request(HttpMethod.DELETE, API.concat("/remover/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveRemoverComFeedback() throws Exception{
        Long id = Long.valueOf(1);
        Mockito.when(veterinarioService.removerComFeedback(Mockito.anyLong())).thenReturn(getVeterinarioInstance());
        MockHttpServletRequestBuilder request = request(HttpMethod.DELETE, API.concat("/remover/feedback/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveBuscarPorId() throws Exception{
        Long id = Long.valueOf(1);
        Mockito.when(veterinarioService.buscarPorId(Mockito.anyLong())).thenReturn(getVeterinarioInstance());
        MockHttpServletRequestBuilder request = request(HttpMethod.GET, API.concat("/buscar/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveBuscarTodos() throws Exception{
        Mockito.when(veterinarioService.buscarTodos()).thenReturn(getVeterinarioListInstance());
        MockHttpServletRequestBuilder request = request(HttpMethod.GET, API.concat("/buscarTodos/"));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}