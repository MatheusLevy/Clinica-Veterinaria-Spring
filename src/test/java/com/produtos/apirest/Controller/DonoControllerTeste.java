package com.produtos.apirest.Controller;

import com.produtos.apirest.models.DTO.DonoDTO;
import com.produtos.apirest.models.Dono;
import com.produtos.apirest.service.DonoService;
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
import static com.produtos.apirest.Controller.AnimalControllerTeste.generateAnimalListInstance;
import static com.produtos.apirest.Util.Util.request;
import static com.produtos.apirest.Util.Util.toJson;
import static org.mockito.ArgumentMatchers.isA;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
public class DonoControllerTeste {

    private final String API = "/api/dono";

    @MockBean
    public DonoService donoService;

    @Autowired
    MockMvc mvc;

    public static Dono generateDonoInstance(){
        return Dono.builder()
                .donoId(1L)
                .nome("nome")
                .cpf("cpf")
                .telefone("telefone")
                .build();
    }

    public static DonoDTO generateDonoDTOInstance(){
        return DonoDTO.builder()
                .id(1L)
                .nome("nome")
                .cpf("cpf")
                .telefone("telefone")
                .build();
    }

    public static List<Dono> generateListDonoInstance(){
        return new ArrayList<>(){{
            add(generateDonoInstance());
        }};
    }

    @WithUserDetails("Admin")
    @Test
    public void deveSalvar() throws Exception{
        Mockito.when(donoService.salvar(Mockito.any(Dono.class))).thenReturn(generateDonoInstance());
        String json = toJson(generateDonoDTOInstance());
        MockHttpServletRequestBuilder request = request(HttpMethod.POST, API.concat("/salvar"), json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveAtualizar() throws Exception{
        Mockito.when(donoService.atualizar(Mockito.any(Dono.class))).thenReturn(generateDonoInstance());
        String json = toJson(generateDonoDTOInstance());
        MockHttpServletRequestBuilder request = request(HttpMethod.PUT, API.concat("/atualizar"), json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveRemoverPorId() throws Exception{
        Long id = 1L;
        Mockito.doNothing().when(donoService).removerPorId(isA(Long.class));
        MockHttpServletRequestBuilder request = request(HttpMethod.DELETE, API.concat("/remover/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("\"NO_CONTENT\""));
    }

    @WithUserDetails("Admin")
    @Test
    public void deveRemoverComFeedback() throws Exception{
        Long id = 1L;
        Mockito.when(donoService.removerComFeedback(Mockito.anyLong())).thenReturn(generateDonoInstance());
        MockHttpServletRequestBuilder request = request(HttpMethod.DELETE, API.concat("/remover/feedback/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveBuscarComFiltro() throws Exception{
        Mockito.when(donoService.buscar(Mockito.any(Dono.class))).thenReturn(generateListDonoInstance());
        String json = toJson(generateDonoDTOInstance());
        MockHttpServletRequestBuilder request = request(HttpMethod.GET, API.concat("/buscar/filtro"), json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveBuscarPorId() throws Exception{
        Long id = 1L;
        Mockito.when(donoService.buscarPorId(Mockito.anyLong())).thenReturn(generateDonoInstance());
        MockHttpServletRequestBuilder request = request(HttpMethod.GET, API.concat("/buscar/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveBuscarTodos() throws Exception{
        Mockito.when(donoService.buscarTodos()).thenReturn(generateListDonoInstance());
        MockHttpServletRequestBuilder request = request(HttpMethod.GET, API.concat("/buscarTodos"));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveBuscarTodosAnimais() throws Exception{
        Long id = 1L;
        Mockito.when(donoService.buscarTodosAnimais(Mockito.anyLong())).thenReturn(generateAnimalListInstance());
        MockHttpServletRequestBuilder request = request(HttpMethod.GET, API.concat("/animais/".concat(String.valueOf(id))));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}