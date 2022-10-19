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
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.ArrayList;
import java.util.List;
import static com.produtos.apirest.Controller.AnimalControllerTeste.getListAnimalInstance;
import static com.produtos.apirest.Util.HttpMethods.*;
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

    public static Dono getDonoInstance(){
        return Dono.builder()
                .donoId(Long.valueOf(1))
                .nome("nome")
                .cpf("cpf")
                .telefone("telefone")
                .build();
    }

    public static DonoDTO getDonoDTOInstance(){
        return DonoDTO.builder()
                .id(Long.valueOf(1))
                .nome("nome")
                .cpf("cpf")
                .telefone("telefone")
                .build();
    }

    public static List<Dono> getListDonoInstance(){
        return new ArrayList<>(){{
            add(getDonoInstance());
        }};
    }

    @WithUserDetails("Admin")
    @Test
    public void deveSalvarController() throws Exception{
        Mockito.when(donoService.salvar(Mockito.any(Dono.class))).thenReturn(getDonoInstance());
        String json = toJson(getDonoDTOInstance());
        MockHttpServletRequestBuilder request = request(Post, API.concat("/salvar"), json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveAtualizarController() throws Exception{
        Mockito.when(donoService.atualizar(Mockito.any(Dono.class))).thenReturn(getDonoInstance());
        String json = toJson(getDonoDTOInstance());
        MockHttpServletRequestBuilder request = request(Put, API.concat("/atualizar"), json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveRemoverPorIdController() throws Exception{
        Long id = Long.valueOf(1);
        Mockito.doNothing().when(donoService).removerPorId(isA(Long.class));
        MockHttpServletRequestBuilder request = request(Delete, API.concat("/remover/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("\"NO_CONTENT\""));
    }

    @WithUserDetails("Admin")
    @Test
    public void deveRemoverComFeedbackController() throws Exception{
        Long id = Long.valueOf(1);
        Mockito.when(donoService.removerFeedback(Mockito.anyLong())).thenReturn(getDonoInstance());
        MockHttpServletRequestBuilder request = request(Delete, API.concat("/remover/feedback/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveBuscarComFiltroController() throws Exception{
        Mockito.when(donoService.buscar(Mockito.any(Dono.class))).thenReturn(getListDonoInstance());
        String json = toJson(getDonoDTOInstance());
        MockHttpServletRequestBuilder request = request(Get, API.concat("/buscar/filtro"), json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveBuscarPorIdController() throws Exception{
        Long id = Long.valueOf(1);
        Mockito.when(donoService.buscarDonoPorId(Mockito.anyLong())).thenReturn(getDonoInstance());
        MockHttpServletRequestBuilder request = request(Get, API.concat("/buscar/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveBuscarTodosController() throws Exception{
        Mockito.when(donoService.buscarTodos()).thenReturn(getListDonoInstance());
        MockHttpServletRequestBuilder request = request(Get, API.concat("/buscarTodos"));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveBuscarTodosAnimaisController() throws Exception{
        Long id = Long.valueOf(1);
        Mockito.when(donoService.buscarTodosAnimais(Mockito.anyLong())).thenReturn(getListAnimalInstance());
        MockHttpServletRequestBuilder request = request(Get, API.concat("/animais/".concat(String.valueOf(id))));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}