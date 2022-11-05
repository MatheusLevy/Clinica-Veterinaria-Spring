package com.produtos.apirest.Controller;

import com.produtos.apirest.Util.Util;
import com.produtos.apirest.models.DTO.OwnerDTO;
import com.produtos.apirest.models.Owner;
import com.produtos.apirest.service.OwnerService;
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

import static com.produtos.apirest.Controller.AnimalControllerTeste.generateAnimalList;
import static com.produtos.apirest.Util.Util.buildRequest;
import static com.produtos.apirest.Util.Util.toJson;
import static org.mockito.ArgumentMatchers.isA;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
public class DonoControllerTeste {

    private final String API = "/api/owner";

    @MockBean
    public OwnerService ownerService;

    @Autowired
    MockMvc mvc;

    protected static Owner generateOwner(){
        return Owner.builder()
                .ownerId(1L)
                .name("name")
                .cpf("cpf")
                .phone("phone")
                .build();
    }

    public static OwnerDTO generateDonoDTOInstance(){
        return OwnerDTO.builder()
                .id(1L)
                .name("name")
                .cpf("cpf")
                .phone("phone")
                .build();
    }

    public static List<Owner> generateListDonoInstance(){
        return new ArrayList<>(){{
            add(generateOwner());
        }};
    }

    @WithUserDetails("Admin")
    @Test
    public void deveSalvar() throws Exception{
        Mockito.when(ownerService.save(Mockito.any(Owner.class))).thenReturn(generateOwner());
        String json = toJson(generateDonoDTOInstance());
        MockHttpServletRequestBuilder request = Util.buildRequest(HttpMethod.POST, API, json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveAtualizar() throws Exception{
        Mockito.when(ownerService.update(Mockito.any(Owner.class))).thenReturn(generateOwner());
        String json = toJson(generateDonoDTOInstance());
        MockHttpServletRequestBuilder request = Util.buildRequest(HttpMethod.PUT, API, json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveRemoverPorId() throws Exception{
        Long id = 1L;
        Mockito.doNothing().when(ownerService).removeById(isA(Long.class));
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.DELETE, API.concat("/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("\"NO_CONTENT\""));
    }

    @WithUserDetails("Admin")
    @Test
    public void deveRemoverComFeedback() throws Exception{
        Long id = 1L;
        Mockito.when(ownerService.removeByIdWithFeedback(Mockito.anyLong())).thenReturn(generateOwner());
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.DELETE, API.concat("/feedback/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveBuscarComFiltro() throws Exception{
        Mockito.when(ownerService.find(Mockito.any(Owner.class))).thenReturn(generateListDonoInstance());
        String json = toJson(generateDonoDTOInstance());
        MockHttpServletRequestBuilder request = Util.buildRequest(HttpMethod.GET, API.concat("/filter"), json);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveBuscarPorId() throws Exception{
        Long id = 1L;
        Mockito.when(ownerService.findById(Mockito.anyLong())).thenReturn(generateOwner());
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.GET, API.concat("/").concat(String.valueOf(id)));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveBuscarTodos() throws Exception{
        Mockito.when(ownerService.findAll()).thenReturn(generateListDonoInstance());
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.GET, API);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithUserDetails("Admin")
    @Test
    public void deveBuscarTodosAnimais() throws Exception{
        Long id = 1L;
        Mockito.when(ownerService.findAllAnimalsByOwnerId(Mockito.anyLong())).thenReturn(generateAnimalList());
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.GET, API.concat("/animals/".concat(String.valueOf(id))));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}